plugins {
    `kotlin-dsl`
}

group = "io.goodmidnight.buildsystem"
version = "0.0.1"

dependencies {
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.android.gradle)
}