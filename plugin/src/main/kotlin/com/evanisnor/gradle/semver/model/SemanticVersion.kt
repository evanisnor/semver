package com.evanisnor.gradle.semver.model

/**
 * Version data following rules defined here -> https://semver.org/
 */
data class SemanticVersion(
    val major: Int = 0,
    val minor: Int = 1,
    val patch: Int = 0,
    val preReleaseVersion: PreReleaseVersion? = null,
    val buildMetadata: String? = null
) : Comparable<SemanticVersion> {

    /** Increment major by one. Resets all other fields. */
    fun nextMajor() =
        SemanticVersion(
            major = major + 1,
            minor = 0,
            patch = 0,
        )

    /** Increment minor by one. Resets all other fields except [major]. */
    fun nextMinor() =
        SemanticVersion(
            major = major,
            minor = minor + 1,
            patch = 0,
        )

    /** Increment patch by one. Resets all other fields except [major] and [minor]. */
    fun nextPatch() =
        SemanticVersion(
            major = major,
            minor = minor,
            patch = patch + 1,
        )

    /** Increment the version according to the provided [SemanticVersionConfiguration.UntaggedIncrementRule] **/
    fun increment(incrementRule: SemanticVersionConfiguration.UntaggedIncrementRule) = when (incrementRule) {
        SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMajor -> nextMajor()
        SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMinor -> nextMinor()
        SemanticVersionConfiguration.UntaggedIncrementRule.IncrementPatch -> nextPatch()
    }

    /**
     * Increment [preReleaseVersion] by one if defined, otherwise set the next pre-release version
     * according to [PreReleaseVersion.next] and set its version as the initial version. Resets
     * [buildMetadata] to null.
     *
     * Example: existing pre-release version - 0.1.0-beta.5 (0.1.9-beta.6) Example: null existing
     * pre-release version, identifier = RC, initialVersion = 1 (<version>-RC.1)
     */
    fun nextPreReleaseVersion(identifier: String, initialVersion: Int = 1) =
        SemanticVersion(
            major = major,
            minor = minor,
            patch = patch,
            preReleaseVersion = preReleaseVersion?.next()
                ?: PreReleaseVersion(identifier = identifier, version = initialVersion)
        )

    /** Change [preReleaseVersion] to the provided value. Resets [buildMetadata] to null */
    fun asPreReleaseVersion(preReleaseVersion: PreReleaseVersion) =
        SemanticVersion(
            major = major,
            minor = minor,
            patch = patch,
            preReleaseVersion = preReleaseVersion
        )

    /** Change [buildMetadata] to the provided value. Preserves all other fields. */
    fun withBuildMetadata(buildMetadata: String) =
        SemanticVersion(
            major = major,
            minor = minor,
            patch = patch,
            preReleaseVersion = preReleaseVersion,
            buildMetadata = buildMetadata
        )

    /** True if this version is a pre-release version **/
    fun isPreRelease(): Boolean = preReleaseVersion != null

    /**
     * <valid semver> ::= <version core>
     * ```
     *                  | <version core> "-" <pre-release>
     *                  | <version core> "+" <build>
     *                  | <version core> "-" <pre-release> "+" <build>
     * ```
     * https://semver.org/#backusnaur-form-grammar-for-valid-semver-versions
     */
    override fun toString(): String =
        "$major.$minor.$patch" +
                (if (preReleaseVersion == null) "" else "-$preReleaseVersion") +
                (if (buildMetadata.isNullOrBlank()) "" else "+$buildMetadata")

    /**
     * Precedence refers to how versions are compared to each other when ordered.
     *
     * 1. Precedence MUST be calculated by separating the version into major, minor, patch and
     * pre-release identifiers in that order (Build metadata does not figure into precedence).
     *
     * 2. Precedence is determined by the first difference when comparing each of these identifiers
     * from left to right as follows: Major, minor, and patch versions are always compared
     * numerically.
     * ```
     *      Example: 1.0.0 < 2.0.0 < 2.1.0 < 2.1.1.
     * ```
     * 3. When major, minor, and patch are equal, a pre-release version has lower precedence than a
     * normal version:
     * ```
     *      Example: 1.0.0-alpha < 1.0.0.
     * ```
     * 4. [PreReleaseVersion.compareTo]
     *
     * https://semver.org/#spec-item-11
     */
    override fun compareTo(other: SemanticVersion): Int =
        when {
            major != other.major -> major - other.major
            minor != other.minor -> minor - other.minor
            patch != other.patch -> patch - other.patch
            preReleaseVersion != other.preReleaseVersion -> preReleaseVersion.compareTo(other.preReleaseVersion)
            else -> 0
        }
}
