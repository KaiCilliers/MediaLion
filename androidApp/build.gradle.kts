plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.medialion.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.medialion.android"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
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
    implementation("androidx.fragment:fragment-ktx:1.5.6")
    implementation("androidx.activity:activity-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    implementation("com.github.Zhuinden:simple-stack:2.7.0")
    implementation("com.github.Zhuinden.simple-stack-extensions:core-ktx:2.3.0")
    implementation("com.github.Zhuinden.simple-stack-extensions:fragments:2.3.0")
    implementation("com.github.Zhuinden.simple-stack-extensions:fragments-ktx:2.3.0")
    implementation("com.github.Zhuinden.simple-stack-extensions:navigator-ktx:2.3.0")
    implementation("com.github.Zhuinden.simple-stack-extensions:services:2.3.0")
    implementation("com.github.Zhuinden.simple-stack-extensions:services-ktx:2.3.0")
}