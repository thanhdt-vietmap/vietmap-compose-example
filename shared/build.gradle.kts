import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id(libs.plugins.kotlinCocoapods.get().pluginId)
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.compose.jetbrains.get().pluginId) version "1.7.3"
    id(libs.plugins.kotlin.serialization.get().pluginId) version "2.1.20"
    id(libs.plugins.compose.compiler.get().pluginId)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        pod("VietMap", "2.0.0")
    }
    
    sourceSets {
        commonMain.dependencies {

            implementation("org.jetbrains.compose.components:components-resources:1.7.3")
            implementation(libs.vietmap.compose)
            implementation(libs.vietmap.compose.material3)
            implementation(libs.vietmap.compose.expressions)
            implementation(libs.androidx.navigation.compose)

            implementation("org.jetbrains.compose.runtime:runtime:1.5.0")
            implementation("org.jetbrains.compose.ui:ui:1.5.0") // Compose UI
            implementation("org.jetbrains.compose.material3:material3:1.5.0") // Compose Material 3 (nếu cần)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "vn.vietmap.vietmapkmmsdktest"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}