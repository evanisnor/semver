plugins {
    kotlin("jvm")
}

repositories {
    google()
}

dependencies {
    implementation(projects.plugin)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    implementation(gradleApi())


    val android = "7.0.0-beta03"
    val androidGradlePlugin = "com.android.tools.build:gradle:$android"
    val androidGradlePluginApi = "com.android.tools.build:gradle-api:$android"

    implementation(androidGradlePlugin)
    implementation(androidGradlePluginApi)
}
