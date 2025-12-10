plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "UIRouterKCM"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.9.3")
                implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.kcm.uirouter.library"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("UIRouter for KCM")
                description.set("A type-safe routing library for Kotlin Compose Multiplatform")
                url.set("https://github.com/yourusername/uirouter-for-kcm")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("yourusername")
                        name.set("Your Name")
                        email.set("your.email@example.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/yourusername/uirouter-for-kcm.git")
                    developerConnection.set("scm:git:ssh://github.com/yourusername/uirouter-for-kcm.git")
                    url.set("https://github.com/yourusername/uirouter-for-kcm")
                }
            }
        }
    }
}
