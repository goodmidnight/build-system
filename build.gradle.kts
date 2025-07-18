plugins {
    `kotlin-dsl`
}

group = "io.goodmidnight.buildsystem"
version = "0.0.1"

dependencies {
    compileOnly(buildLib.kotlin.gradle)
    compileOnly(buildLib.android.gradle)
}

gradlePlugin {
    plugins {
        register("LogPlugin") {
            id = "id.goodmidnight.buildsystem.log"
            implementationClass = "io.goodmidnight.buildsystem.plugin.LogPlugin"
        }
    }
}