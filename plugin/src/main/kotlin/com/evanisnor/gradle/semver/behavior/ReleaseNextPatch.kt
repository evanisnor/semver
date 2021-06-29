package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.procedures.MetadataBuilder
import com.evanisnor.gradle.semver.model.NoVersionsFoundError
import com.evanisnor.gradle.semver.procedures.Procedures

class ReleaseNextPatch(
    private val procedures: Procedures,
    private val metadataBuilder: MetadataBuilder
) : Runnable {

    override fun run() {
        val versions = procedures.sortedVersions()
        if (versions.isEmpty()) {
            throw NoVersionsFoundError()
        } else {
            val next = versions.first()
                .nextPatch()
                .withBuildMetadata(metadataBuilder.buildMetadata())

            procedures.tagAndPush(next)
            println("Created tag '$next' and pushed to origin")
        }
    }
}