plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("semver-module") {
            id = "com.evanisnor.semver.build"
            implementationClass = "com.evanisnor.semver.build.ModulePlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.20")
}