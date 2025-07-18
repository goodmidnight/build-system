package io.goodmidnight.buildsystem.plugin

import io.goodmidnight.buildsystem.task.CheckLogTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.the

/**
 * [LogPlugin]
 */
class LogPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("checkLog", CheckLogTask::class.java) {
            group = GROUP_NAME
            description = "Detects all Log calls in source files and generates a report."

            // Collects source files from specified source sets (main, test)
            listOf(
                SourceSet.MAIN_SOURCE_SET_NAME, // src/main/java, src/main/kotlin
                SourceSet.TEST_SOURCE_SET_NAME  // src/test/java, src/test/kotlin
            ).forEach { sourceSet ->
                project.the<SourceSetContainer>()
                    .getByName(sourceSet).allSource.srcDirs.forEach { dir ->
                        sourceFiles.from(
                            project.fileTree(
                                mapOf(
                                    "dir" to dir,
                                    "include" to listOf("**/*.kt", "**/*.java")
                                )
                            )
                        )
                    }
            }

            reportFile.set(project.layout.buildDirectory.file("reports/log-check/report.txt"))
        }
    }

    companion object {
        private const val GROUP_NAME = "Logging"
    }
}