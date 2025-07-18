dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("buildLib") {
            from(files("./gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-system"

