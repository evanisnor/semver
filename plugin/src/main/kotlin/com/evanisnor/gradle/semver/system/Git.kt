package com.evanisnor.gradle.semver.system

import com.evanisnor.gradle.semver.support.run
import com.evanisnor.gradle.semver.support.runForExitCode

class Git(private val processBuilder: ProcessBuilder) {

    object Commands {
        const val listAllTags = "git tag"
        const val listCommitTags = "git describe --tags --exact-match"

        fun createTag(version: String) = "git tag $version"
        fun pushTag(remote: String, version: String) = "git push $remote $version"
    }

    fun isCommitTagged() = processBuilder.runForExitCode(
        Commands.listCommitTags
    ) == 0

    fun listAllTags() = processBuilder.run(
        Commands.listAllTags
    )

    fun createTag(version: String) = processBuilder.run(
        Commands.createTag(version)
    )

    fun push(remote: String, version: String) = processBuilder.run(
        Commands.pushTag(remote, version)
    )
}