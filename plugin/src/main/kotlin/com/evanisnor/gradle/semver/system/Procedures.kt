package com.evanisnor.gradle.semver.system

import com.evanisnor.gradle.semver.SemanticVersion
import com.evanisnor.gradle.semver.toSemanticVersion

class Procedures(
    private val git: Git
) {

    fun isCommitTagged() = git.isCommitTagged()

    fun sortedVersions() = git.listAllTags()
        .map { it.toSemanticVersion() }
        .sortedByDescending { it }

    fun tagAndPush(next: SemanticVersion, isDryRun: Boolean = false) {
        val version = next.asTagged().toString()

        if (isDryRun) {
            println("**DRY RUN**")
            println(Git.Commands.createTag(version))
            println(Git.Commands.pushTag("origin", version))
        } else {
            git.createTag(version)
            git.push("origin", version)
            println("$version released")
        }
    }
}