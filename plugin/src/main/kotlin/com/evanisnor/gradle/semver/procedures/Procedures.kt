package com.evanisnor.gradle.semver.procedures

import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.support.isDryRun
import com.evanisnor.gradle.semver.system.Git
import org.gradle.api.Project

class Procedures(
    private val git: Git,
    private val project: Project,
    private val parser: SemanticVersionParser,
    private val configuration: SemanticVersionConfiguration
) {
    fun isCommitTagged() = git.isCommitTagged()

    fun sortedVersions() = git.listAllTags()
        .map { parser.parseSemVer(it) }
        .sortedByDescending { it }

    fun tagAndPush(version: SemanticVersion) {
        if (project.isDryRun()) {
            println("**DRY RUN**")
            println(Git.Commands.createTag(version))
            println(Git.Commands.pushTag(configuration.remote, version))
        } else {
            git.createTag(version)
            git.push(configuration.remote, version)
            println("$version released")
        }
    }

    fun currentCommitSha(): String = git.currentCommitSha()

    fun currentShortCommitSha(): String = git.currentShortCommitSha()
}