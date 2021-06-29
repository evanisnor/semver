package com.evanisnor.gradle.semver.system

import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.support.run
import com.evanisnor.gradle.semver.support.runForExitCode

class Git(private val processBuilder: ProcessBuilder) {

    object Commands {
        const val listAllTags = "git tag"
        const val listCommitTags = "git describe --tags --exact-match"

        fun createTag(version: SemanticVersion) = "git tag $version"
        fun pushTag(remote: String, version: SemanticVersion) = "git push $remote $version"
        fun getCurrentSha(reference: String = "HEAD") = "git rev-parse $reference"
        fun getCurrentShortSha(reference: String = "HEAD") = "git rev-parse --short $reference"
    }

    fun isCommitTagged() = processBuilder.runForExitCode(
        Commands.listCommitTags
    ) == 0

    fun listAllTags() = processBuilder.run(
        Commands.listAllTags
    )

    fun createTag(version: SemanticVersion) = processBuilder.run(
        Commands.createTag(version)
    )

    fun push(remote: String, version: SemanticVersion) = processBuilder.run(
        Commands.pushTag(remote, version)
    )

    fun currentCommitSha(): String = processBuilder.run(Commands.getCurrentSha()).first()

    fun currentShortCommitSha(): String = processBuilder.run(Commands.getCurrentShortSha()).first()
}