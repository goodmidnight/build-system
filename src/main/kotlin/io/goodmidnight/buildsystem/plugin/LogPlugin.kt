package io.goodmidnight.buildsystem.plugin

import io.goodmidnight.buildsystem.task.CheckLogTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class LogPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("checkLog", CheckLogTask::class.java) {
            group = GROUP_NAME
            description = "Detects all Log calls in source files and generates a report."

            // Collects source files from specified source sets
            sourceFiles.from(
                project.fileTree("src/main") {
                    include("**/*.kt", "**/*.java")
                })

            reportFile.set(project.layout.buildDirectory.file("reports/log-check/report.txt"))
        }
    }

    companion object {
        private const val GROUP_NAME = "Logging"
    }
}