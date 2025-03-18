plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
}

kotlin {
    jvm()
    androidTarget()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material)
            }
        }

        jvmMain {
            dependencies {
                //Необходимо чтобы Compose сам подтянул нужные библиотеи для разных платформ
                api(compose.desktop.currentOs)
            }
        }

    }
}

android {
    namespace = "tishukov.app.shared.android.planner"
    compileSdk = findProperty("android.compileSdk").toString().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
