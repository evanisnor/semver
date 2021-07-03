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
        val sortedVersions = procedures.sortedVersions()
        val currentVersion = determineCurrentVersion(sortedVersions)
        println("$currentVersion")
    }

    fun determineCurrentVersion(sortedVersions: List<SemanticVersion>) = when {
        // There are no tags. Use the base/initial semantic version as untagged.
        sortedVersions.isEmpty() -> untaggedVersion(SemanticVersion())
        // The commit is tagged. Grab the top result as the current version.
        procedures.isCommitTagged() -> sortedVersions.first()
        // The commit is not tagged. Build the next untagged version based on configuration rules.
        else -> untaggedVersion(sortedVersions.first().increment(configuration.incrementRule))
    }


    private fun untaggedVersion(version: SemanticVersion): SemanticVersion =
        version.asPreReleaseVersion(PreReleaseVersion(configuration.untaggedIdentifier))

}
