package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.NoVersionsFoundError
import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.Procedures

class NextVersion(
    private val procedures: Procedures,
    private val configuration: SemanticVersionConfiguration
) : Runnable {

    override fun run() {
        val versions = procedures.sortedVersions()
        if (versions.isEmpty()) {
            println(NoVersionsFoundError().message)
        } else {
            val next = nextVersion(versions.first())
            println("$next")
        }
    }

    private fun nextVersion(currentVersion: SemanticVersion): SemanticVersion =
        when (configuration.untaggedIncrementRule) {
            SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMajor -> currentVersion.nextMajor()
            SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMinor -> currentVersion.nextMinor()
            SemanticVersionConfiguration.UntaggedIncrementRule.IncrementPatch -> currentVersion.nextPatch()
        }.asPreReleaseVersion(PreReleaseVersion(configuration.untaggedIdentifier))
}