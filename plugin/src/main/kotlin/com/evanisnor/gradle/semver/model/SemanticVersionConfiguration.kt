package com.evanisnor.gradle.semver.model

/**
 * Configuration model for the "semanticVersion" block in your project.
 */
abstract class SemanticVersionConfiguration {

    class Default : SemanticVersionConfiguration()

    enum class ReleaseMetadata {
        None,
        ShortSha,
        LongSha,
        Timestamp
    }

    enum class UntaggedIncrementRule {
        IncrementPatch,
        IncrementMinor,
        IncrementMajor
    }

    var remote: String = "origin"
    var prefix: String = ""
    var preReleaseIdentifiers: List<String> = listOf("alpha", "beta")
    var preReleaseMetadata: ReleaseMetadata = ReleaseMetadata.None
    var initialPreReleaseVersion: Int = 0
    var releaseMetadata: ReleaseMetadata = ReleaseMetadata.None
    var untaggedIdentifier = "SNAPSHOT"
    var untaggedIncrementRule = UntaggedIncrementRule.IncrementPatch

    override fun toString(): String =
        """
                            remote: $remote
                            prefix: $prefix
             preReleaseIdentifiers: $preReleaseIdentifiers
          initialPreReleaseVersion: $initialPreReleaseVersion
                preReleaseMetadata: $preReleaseMetadata
                   releaseMetadata: $releaseMetadata
             untaggedIncrementRule: $untaggedIncrementRule
                untaggedIdentifier: $untaggedIdentifier
        """.trimIndent()

}