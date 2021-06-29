package com.evanisnor.gradle.semver.model

class NoVersionsFoundError : Throwable() {
    override val message: String = "Sorry, you haven't declared any versions yet."
}