import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration.*

plugins {
    id("com.evanisnor.semver")
}

semanticVersion {
    remote = "origin"
    prefix = "v"
    preReleaseIdentifiers = listOf(
        "alpha",
        "beta",
        "gamma"
    )
    preReleaseMetadata = ReleaseMetadata.ShortSha
    initialPreReleaseVersion = 1
    releaseMetadata = ReleaseMetadata.Timestamp
    untaggedIncrementRule = UntaggedIncrementRule.IncrementMajor
}