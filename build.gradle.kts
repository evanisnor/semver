plugins {
    kotlin("jvm") version "1.5.20"
}

repositories {
    mavenCentral()
}

allprojects {
    group = "com.evanisnor.semver"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }
}