plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
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
//    implementation(project(":data"))



}