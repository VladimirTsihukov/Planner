plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    alias(libs.plugins.moko.res)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm("desktop")
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = false
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                //Compose
                api(compose.foundation)
                api(compose.runtime)
                api(compose.ui)
                api(compose.material)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(libs.lifecycle.viewmodel.compose)

                //Resources
                api(libs.resources.compose)
                api(libs.resources.core)

                //Settings
                api(libs.settings)

                //DI
                api(libs.koin.core)
                api(libs.koin.compose)
                api(libs.koin.compose.viewmodel)

                //DateTime
                implementation(libs.date.time)
            }
        }

        val desktopMain by getting {
            dependencies {
                //Необходимо чтобы Compose сам подтянул нужные библиотеи для разных платформ
                api(compose.desktop.currentOs)
                //необходим чтобы viewModelScope внутри ViewModel корректно работал в Compose Desktop (поддержка Dispatchers.Main).
                api(libs.kotlinx.coroutines.swing)
            }
        }
    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure {
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }
}

multiplatformResources {
    resourcesPackage.set("com.tishukov.planner")
    resourcesClassName.set("MR")
    iosBaseLocalizationRegion.set("en")
    iosMinimalDeploymentTarget.set("11.0")
}

android {
    namespace = "tishukov.app.shared.android.planner"
    compileSdk = findProperty("android.compileSdk").toString().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
