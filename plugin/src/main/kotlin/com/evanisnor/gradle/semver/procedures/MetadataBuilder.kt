package com.evanisnor.gradle.semver.procedures

import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import java.time.Clock

class MetadataBuilder(
    private val procedures: Procedures,
    private val configuration: SemanticVersionConfiguration
) {

    fun buildMetadata(): String = when (configuration.preReleaseMetadata) {
        SemanticVersionConfiguration.ReleaseMetadata.LongSha -> procedures.currentCommitSha()
        SemanticVersionConfiguration.ReleaseMetadata.ShortSha -> procedures.currentShortCommitSha()
        SemanticVersionConfiguration.ReleaseMetadata.Timestamp -> Clock.systemUTC().instant().epochSecond.toString()
        else -> ""
    }

}