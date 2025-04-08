plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Habilita el procesador de anotaciones en Kotlin
    id("com.google.devtools.ksp")
    id ("kotlin-android")
}

android {
    namespace = "com.example.practicarominaluppi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practicarominaluppi"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    buildFeatures {
        compose = true

    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.foundation)


    implementation (libs.kotlin.stdlib)

    implementation(project(":data"))
    implementation(project(":core"))
    implementation(project(":domain"))

    // Retrofit
    implementation(libs.retrofit)
    // Retrofit with Scalar Converter
    implementation(libs.converter.scalars)
    implementation(libs.converter.gson)

    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit.v290)
    implementation(libs.kotlinx.serialization.json)  // Agrega la dependencia de Kotlinx Serialization
    implementation(libs.retrofit2.kotlinx.serialization.converter.v080)  // Convertidor de Kotlinx para Retrofit
    implementation(libs.okhttp)

    //Room
    implementation(libs.androidx.room.runtime)
    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Procesador de anotaciones para Room
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}