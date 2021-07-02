import com.evanisnor.semver.build.Dependencies

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("semver") {
            id = "com.evanisnor.semver"
            implementationClass = "com.evanisnor.gradle.semver.SemverPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
    testImplementation(gradleTestKit())

    with(Dependencies.Jetbrains) {
        implementation(kotlinStdLib)
        implementation(kotlinReflect)
    }

    with(Dependencies.Junit) {
        testImplementation(jupiterApi)
        testImplementation(jupiterParams)
        testRuntimeOnly(jupiterEngine)
    }

    testImplementation(Dependencies.Google.truth)
    testImplementation(Dependencies.Mockk.mockk)
}