rootProject.name = "uirouter-for-kcm"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":uirouter-for-kcm")
include(":sample")

// Map the module to the library directory
project(":uirouter-for-kcm").projectDir = file("library")
