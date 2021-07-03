import com.evanisnor.semver.build.Dependencies

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    id("com.evanisnor.semver.build")
}

repositories {
    google()
}

gradlePlugin {
    plugins {
        create("semver") {
            id = "com.evanisnor.semver.android"
            implementationClass = "com.evanisnor.gradle.semver.android.AndroidSemverPlugin"
        }
    }
}

dependencies {
    implementation(projects.plugin)
    implementation(gradleApi())
    testImplementation(gradleTestKit())

    with(Dependencies.Jetbrains) {
        implementation(kotlinStdLib)
        implementation(kotlinReflect)
    }

    with(Dependencies.Android) {
        implementation(androidGradlePlugin)
        implementation(androidGradlePluginApi)
    }

    with(Dependencies.Junit) {
        testImplementation(jupiterApi)
        testImplementation(jupiterParams)
        testRuntimeOnly(jupiterEngine)
    }

    testImplementation(Dependencies.Google.truth)
    testImplementation(Dependencies.Mockk.mockk)
}
