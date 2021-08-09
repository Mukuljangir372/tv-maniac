@file:Suppress("UnstableApiUsage")

import util.libs

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {

    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        applicationId = "com.thomaskioko.tvmaniac"
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.get()
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    dependencies {

        implementation(libs.hilt.android)
        implementation(libs.hilt.navigation)
        kapt(libs.hilt.compiler)

        implementation(libs.napier)
        implementation(libs.ktor.android)

        debugImplementation(libs.squareup.leakcanary)


    }
}