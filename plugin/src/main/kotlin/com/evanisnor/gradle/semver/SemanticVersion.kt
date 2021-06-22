package com.evanisnor.gradle.semver


fun String.toSemanticVersion() = SemanticVersionParser().parseSemVer(this)

data class SemanticVersion(
    val major: Int = 0,
    val minor: Int = 1,
    val patch: Int = 0,
    val candidateIdentifier: String = defaultCandidateIdentifier,
    val candidate: Int = 0,
    val isTagged: Boolean = false,
    val untaggedIdentifier: String = defaultUntaggedIdentifier,
    val buildIdentifier: String = defaultBuildIdentifier,
    val build: String = ""
) : Comparable<SemanticVersion> {

    companion object {
        const val defaultCandidateIdentifier = "RC"
        const val defaultUntaggedIdentifier = "SNAPSHOT"
        const val defaultBuildIdentifier = ""
    }

    fun nextMajor() = SemanticVersion(
        major = major + 1,
        minor = 0,
        patch = 0,
        candidateIdentifier = candidateIdentifier,
        candidate = 0,
        isTagged = false,
        untaggedIdentifier = untaggedIdentifier,
        buildIdentifier = buildIdentifier
    )

    fun nextMinor() = SemanticVersion(
        major = major,
        minor = minor + 1,
        patch = 0,
        candidateIdentifier = candidateIdentifier,
        candidate = 0,
        isTagged = false,
        untaggedIdentifier = untaggedIdentifier,
        buildIdentifier = buildIdentifier
    )

    fun nextPatch() = SemanticVersion(
        major = major,
        minor = minor,
        patch = patch + 1,
        candidateIdentifier = candidateIdentifier,
        candidate = 0,
        isTagged = false,
        untaggedIdentifier = untaggedIdentifier,
        buildIdentifier = buildIdentifier
    )

    fun nextCandidate() = SemanticVersion(
        major = major,
        minor = minor,
        patch = patch,
        candidateIdentifier = candidateIdentifier,
        candidate = candidate + 1,
        isTagged = false,
        untaggedIdentifier = untaggedIdentifier,
        buildIdentifier = buildIdentifier
    )

    fun asBuild(build: String) = SemanticVersion(
        major = major,
        minor = minor,
        patch = patch,
        candidateIdentifier = candidateIdentifier,
        candidate = candidate,
        isTagged = false,
        untaggedIdentifier = untaggedIdentifier,
        buildIdentifier = buildIdentifier,
        build = build
    )

    fun asTagged() = SemanticVersion(
        major = major,
        minor = minor,
        patch = patch,
        candidateIdentifier = candidateIdentifier,
        candidate = candidate,
        isTagged = true,
        untaggedIdentifier = untaggedIdentifier,
        buildIdentifier = buildIdentifier,
        build = build
    )

    fun toInt(): Int =
        Integer.parseInt(
            "$major" +
                    minor.toString().padStart(2, '0') +
                    patch.toString().padStart(2, '0') +
                    candidate.toString().padStart(2, '0')
        )

    override fun toString(): String = "$major.$minor.$patch" + when {
        candidate > 0 -> "-$candidateIdentifier.$candidate"
        !isTagged -> "-$untaggedIdentifier"
        else -> ""
    } + if (build.isNotBlank()) "+$build" else ""


    override fun compareTo(other: SemanticVersion): Int = when {
        major != other.major -> major - other.major
        minor != other.minor -> minor - other.minor
        patch != other.patch -> patch - other.patch
        isTagged && !other.isTagged -> 1
        candidate != other.candidate -> when {
            candidate == 0 && other.candidate > 0 -> 1
            candidateIdentifier != other.candidateIdentifier ->
                candidateIdentifier.compareTo(other.candidateIdentifier)
            candidate != other.candidate -> candidate - other.candidate
            else -> 0
        }
        untaggedIdentifier != other.untaggedIdentifier ->
            untaggedIdentifier.compareTo(untaggedIdentifier)
        else -> 0
    }

}