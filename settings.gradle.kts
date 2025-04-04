enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // Nếu là repo JitPack

    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // Nếu là repo JitPack

    }
}

rootProject.name = "VietMapKMMSDKTest"
include(":androidApp")
include(":shared")