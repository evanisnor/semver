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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    implementation(gradleApi())

    testImplementation(gradleTestKit())
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.10")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("io.mockk:mockk:1.11.0")
}