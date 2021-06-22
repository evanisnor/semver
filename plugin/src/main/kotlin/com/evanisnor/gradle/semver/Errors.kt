package com.evanisnor.gradle.semver

class NoVersionsFoundError : Throwable() {
    override val message: String = "Sorry, you haven't declared any versions yet."
}