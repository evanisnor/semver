package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.NoVersionsFoundError
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.Procedures

class NextPreRelease(
    private val procedures: Procedures,
    private val configuration: SemanticVersionConfiguration,
    private val preReleaseIdentifier: String
) : Runnable {

    override fun run() {
        val versions = procedures.sortedVersions()
        if (versions.isEmpty()) {
            println(NoVersionsFoundError().message)
        } else {
            versions.first()
                .nextPreReleaseVersion(
                    identifier = preReleaseIdentifier,
                    initialVersion = configuration.initialPreReleaseVersion
                )
                .let { println(it) }
        }
    }
}
