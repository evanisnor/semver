package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.MetadataBuilder
import com.evanisnor.gradle.semver.procedures.Procedures

class ReleasePreRelease(
    private val procedures: Procedures,
    private val configuration: SemanticVersionConfiguration,
    private val metadataBuilder: MetadataBuilder,
    private val preReleaseIdentifier: String
) : Runnable {

    override fun run() {
        val sortedVersions = procedures.sortedVersions()

        determineNextPreRelease(sortedVersions)
            .apply {
                withBuildMetadata(metadataBuilder.buildMetadata())
            }
            .let {
                procedures.tagAndPush(it)
                println("Created tag '$it' and pushed to origin")
            }
    }

    fun determineNextPreRelease(sortedVersions: List<SemanticVersion>) = when {
        // Current version is already a pre-release
        sortedVersions.isNotEmpty() && sortedVersions.first().isPreRelease() -> sortedVersions.first()
        // Current version is not a pre-release and needs to be incremented
        sortedVersions.isNotEmpty() -> sortedVersions.first().increment(configuration.incrementRule)
        // There are no versions, so use the base version
        else -> SemanticVersion()
    }.nextPreReleaseVersion(
        identifier = preReleaseIdentifier,
        initialVersion = configuration.initialPreReleaseVersion
    )

}