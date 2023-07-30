plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.sunrisekcdeveloper.medialion.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.sunrisekcdeveloper.medialion.android"
        minSdk = 28
        targetSdk = 33
        versionCode = 10
        versionName = "0.2.0"
    }

    signingConfigs {
        signingConfigs.create("release")
        getByName("release") {
            storeFile = file("/Users/nadinecilliers/kaisigningkey")
            storePassword = "password"
            keyAlias = "key0"
            keyPassword = "password"
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = " (Dev)"
            manifestPlaceholders["appName"] = "Dev-MediaLion"
        }
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["appName"] = "MediaLion"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.9"
    }
}

kotlin.sourceSets.all {
    languageSettings.enableLanguageFeature("DataObjects")
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.5.7")
    implementation("androidx.activity:activity-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("io.coil-kt:coil-compose:2.3.0")

    implementation("com.github.Zhuinden:simple-stack-extensions:2.3.3")
    implementation("com.github.KaiCilliers.simple-stack-compose-integration:core:0.2.0")

    implementation("io.github.aakira:napier:2.6.1")

    implementation("com.github.Zhuinden:flow-combinetuple-kt:1.1.1")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("io.ktor:ktor-client-android:2.2.4")

    implementation("io.insert-koin:koin-android:3.4.0")

    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.18")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
}