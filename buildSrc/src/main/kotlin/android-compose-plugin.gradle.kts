@file:Suppress("UnstableApiUsage")

import util.libs

plugins {
    id("com.android.library")
    kotlin("android")
}

android {

    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":android:core:resources"))

    implementation(libs.androidx.core)
    api(libs.androidx.palette)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons)
    api(libs.androidx.compose.ui.runtime)
    implementation(libs.androidx.compose.foundation)
    api(libs.coroutines.jvm)
    api(libs.androidx.compose.ui.tooling)
    implementation(libs.kenburns)
    implementation(libs.coil)
    implementation(libs.androidx.lifecycle.runtime)
}
