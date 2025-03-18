plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "tishukov.app.android.planner"
    compileSdk = findProperty("android.compileSdk").toString().toInt()

    defaultConfig {
        minSdk = findProperty("android.minSdk").toString().toInt()
        targetSdk = findProperty("android.targetSdk").toString().toInt()
        applicationId = "tishukov.app.planner"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(project(":shared"))
}
