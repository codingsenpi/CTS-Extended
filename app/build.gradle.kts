/*
 *
 *  * Copyright (C) 2025 AKS-Labs (original author)
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.akslabs.circletosearch"
    compileSdk {
        version = release(36)
    }

    signingConfigs {
        create("release") {
            val ksFile = System.getenv("KEYSTORE_FILE")
            if (!ksFile.isNullOrBlank() && file(ksFile).exists()) {
                storeFile = file(ksFile)
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }

    defaultConfig {
        applicationId = "com.codingsenpi.ctsextended"
        minSdk = 29
        targetSdk = 36
        versionCode = 8
        versionName = "0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        androidResources {
            localeFilters += "en"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs
                .getByName("release")
                .takeIf { it.storeFile?.exists() == true }
        }
        
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    // Fixed dependenciesInfo block
    dependenciesInfo {
        @Suppress("UnstableApiUsage")
        includeInApk = false
        @Suppress("UnstableApiUsage")
        includeInBundle = false
    }
    buildFeatures {
        compose = true
    }
    packaging {
        jniLibs {
            // Keep native libs compressed in the APK (smaller download);
            // matches android:extractNativeLibs="true" in the manifest.
            useLegacyPackaging = true
        }
        resources {
            // Debug-agent probe bytes bundled inside kotlinx-coroutines-core;
            // never loaded in production (only by kotlinx-coroutines-debug).
            excludes += "DebugProbesKt.bin"
        }
    }
}


dependencies {
    implementation(project(":OCR-translation"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.webkit:webkit:1.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.zxing:core:3.5.4")
}
