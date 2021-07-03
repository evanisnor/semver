package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.procedures.MetadataBuilder
import com.evanisnor.gradle.semver.procedures.Procedures

class ReleasePatch(
    private val procedures: Procedures,
    private val metadataBuilder: MetadataBuilder,
) : Runnable {

    override fun run() {
        val versions = procedures.sortedVersions()

        val next = if (versions.isEmpty()) {
            SemanticVersion()
        } else {
            versions.first()
        }.nextPatch().withBuildMetadata(metadataBuilder.buildMetadata())

        procedures.tagAndPush(next)
        println("Created tag $next and pushed to origin")
    }
}