package com.evanisnor.gradle.semver

class SemanticVersionParser(
    private val prefix: String = defaultPrefix,
    private val candidateIdentifier: String = SemanticVersion.defaultCandidateIdentifier,
    private val untaggedIdentifier: String = SemanticVersion.defaultUntaggedIdentifier,
    private val buildIdentifier: String = SemanticVersion.defaultBuildIdentifier,
    private val versionPattern: String = "^$prefix([0-9]+)\\.([0-9]{1,2})\\.([0-9]{1,2})"
            + "(?:(?:" +
            "-(?:$candidateIdentifier\\.)?([0-9]+){1,2})|" +
            "-($untaggedIdentifier))?" +
            "(?:\\+(?:$buildIdentifier\\.)?([a-zA-Z0-9]{1,15})" +
            ")?$"
) {

    companion object {
        const val defaultPrefix = ""
    }

    fun parseSemVer(versionString: String): SemanticVersion {
        versionPattern.toRegex().find(versionString)?.groupValues?.let { groups ->
            return SemanticVersion(
                major = groups[1].toInt(),
                minor = groups[2].toInt(),
                patch = groups[3].toInt(),
                candidateIdentifier = candidateIdentifier,
                candidate = if (groups.size > 4 && groups[4].isBlank()) 0 else groups[4].toInt(),
                isTagged = !(groups.size > 5 && groups[5] == untaggedIdentifier),
                untaggedIdentifier = untaggedIdentifier,
                buildIdentifier = buildIdentifier,
                build = if (groups.size <= 6) "" else groups[6]
            )
        }

        return SemanticVersion()
    }

}