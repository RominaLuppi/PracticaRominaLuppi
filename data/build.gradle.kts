plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Habilita el procesador de anotaciones en Kotlin
    id("com.google.devtools.ksp")
    id ("kotlin-android")
    id ("kotlin-kapt") // Necesario para la anotación @Inject
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    // Procesador de anotaciones para Room
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

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
    //Retromock
    implementation (libs.retromock)


    //hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.android.compiler)
    implementation (libs.hilt.navigation.compose)

    implementation(project(":domain"))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}