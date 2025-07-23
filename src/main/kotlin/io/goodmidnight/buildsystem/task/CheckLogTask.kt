package io.goodmidnight.buildsystem.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CheckLogTask : DefaultTask() {
    private val logRegex = Regex("""(Log|Timber)\s*\.\s*\w+\s*\(.*?\);?""")

    @get:InputFiles
    abstract val sourceFiles: ConfigurableFileCollection

    @get:OutputFile
    abstract val reportFile: RegularFileProperty

    @TaskAction
    fun execute() {
        println("Starting Log call detection process")

        if (sourceFiles.files.isEmpty())
            println("No source files found for analysis. Please check your plugin configuration.")

        // Detects and collects lines of Log or Timber calls within the source files.
        val logCallFindings: MutableList<String> = sourceFiles.files
            .filter { it.isFile && (it.extension.lowercase() == "java" || it.extension.lowercase() == "kt") }
            .flatMap { file ->
                file.readLines().mapIndexedNotNull { index, line ->
                    if (logRegex.containsMatchIn(line))
                        "Found log call in ${file.absolutePath} at line ${index + 1}: $line"
                    else null
                }
            }
            .toMutableList()

        val reportFile: File = reportFile.asFile.get()

        // Create directory
        reportFile.parentFile.mkdirs()

        // Write text in the report file
        reportFile.writeText(
            text = if (logCallFindings.isNotEmpty())
                "--- Log Call Report ---\nDetected ${logCallFindings.size} Log/Timber calls across your project.\n${
                    logCallFindings.joinToString(
                        "\n"
                    )
                }"
            else "--- Log Call Report ---\nNo Log/Timber calls detected in the scanned source files."
        )


        println("Log detection complete. Report generated at: ${reportFile.absolutePath}")
    }
}