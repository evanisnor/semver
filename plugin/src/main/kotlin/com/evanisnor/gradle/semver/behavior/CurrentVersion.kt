package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.Procedures

class CurrentVersion(
    private val procedures: Procedures,
    private val configuration: SemanticVersionConfiguration
) : Runnable {

    override fun run() {
        val currentVersion = determineCurrentVersion()
        println("$currentVersion")
    }

    fun determineCurrentVersion(): SemanticVersion {
        val sortedVersions = procedures.sortedVersions()
        return when {
            // There are no commits. Use the base/initial semantic version.
            sortedVersions.isEmpty() -> SemanticVersion()
            // The commit is tagged. Grab the top result as the current version.
            procedures.isCommitTagged() -> sortedVersions.first()
            // The commit is not tagged. Build the latest untagged version based on configuration rules.
            else -> untaggedVersion(sortedVersions.first())
        }
    }

    private fun untaggedVersion(firstVersion: SemanticVersion): SemanticVersion =
        when (configuration.untaggedIncrementRule) {
            SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMajor -> firstVersion.nextMajor()
            SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMinor -> firstVersion.nextMinor()
            SemanticVersionConfiguration.UntaggedIncrementRule.IncrementPatch -> firstVersion.nextPatch()
        }.asPreReleaseVersion(PreReleaseVersion(configuration.untaggedIdentifier))
}
