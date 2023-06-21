plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.application)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.kotlin)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.diffplug.spotless)
}

android {
    namespace = "com.mandiri.tmdb"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = "com.mandiri.tmdb"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = 100000
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraint.layout)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

apply(from = rootProject.file("spotless.gradle"))