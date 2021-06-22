plugins {
    kotlin("jvm") version "1.5.10"
}

group = "com.evanisnor.semver"
version = "0.1.0"

repositories {
    mavenCentral()
}

allprojects {
    group = "com.evanisnor.semver"
    version = "0.1.0"

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showStandardStreams = true
    }

    repositories {
        mavenCentral()
    }
}