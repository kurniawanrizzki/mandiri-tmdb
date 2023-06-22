plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.application)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.kotlin)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.diffplug.spotless)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.google.hilt)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.androidx.navigation.args)
    kotlin("kapt")
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
    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)

    implementation(libs.google.material)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.facebook.shimmer)
    implementation(libs.coil.kt)
    implementation(libs.squareapp.retrofit)
    implementation(libs.squareapp.retrofit.converter.gson)
    implementation(platform(libs.squareapp.okhttp.bom))
    implementation(libs.squareapp.okhttp.client)
    implementation(libs.squareapp.okhttp.logging.interceptor)

    implementation(libs.androidx.paging)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

apply(from = rootProject.file("spotless.gradle"))