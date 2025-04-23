plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.core"
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
    buildFeatures {
        compose = true

    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.androidx.material)
    implementation(project(":domain"))
    implementation (libs.ui) // Composable UI
    implementation (libs.material3) // Material3

    //implementation (libs.androidx.material3.datepicker)
    implementation (libs.androidx.material3.v121)
    implementation (libs.ui.tooling.preview)
    implementation (libs.androidx.foundation)
   // implementation (libs.androidx.lifecycle.livedata.compose)
    implementation(libs.androidx.lifecycle.runtime.compose.v262)

    implementation (libs.androidx.runtime.livedata)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
   // implementation(libs.androidx.lifecycle.livedata.compose.v100alpha01)
    implementation(libs.accompanist.pager)
    implementation(libs.androidx.material3.v110)
    // Hilt
//    implementation (libs.hilt.android)
//    implementation (libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}