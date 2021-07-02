package com.evanisnor.gradle.semver.procedures

import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.support.findVersion
import com.evanisnor.gradle.semver.support.omitVersion
import com.evanisnor.gradle.semver.support.splitIdentifier

/**
 * Semver Parser
 *
 * <valid semver> ::= <version core>
 *                  | <version core> "-" <pre-release>
 *                  | <version core> "+" <build>
 *                  | <version core> "-" <pre-release> "+" <build>
 *
 * https://semver.org/#backusnaur-form-grammar-for-valid-semver-versions
 */
class SemanticVersionParser(
    private val configuration: SemanticVersionConfiguration = SemanticVersionConfiguration.Default(),
    private val prefix: String = configuration.prefix,
    private val versionPattern: String = "$prefix^([0-9]+)\\.([0-9]+)\\.([0-9]+)(?:\\-([a-zA-Z0-9\\-\\.]*))*(?:\\+([a-zA-Z0-9\\.]*))?\$"
) {

    fun parseSemVer(versionString: String): SemanticVersion {
        versionPattern.toRegex().find(versionString)?.groupValues?.let { groups ->
            return SemanticVersion(
                major = groups[1].toInt(),
                minor = groups[2].toInt(),
                patch = groups[3].toInt(),
                preReleaseVersion = if (groups[4].isBlank()) null else PreReleaseVersion(
                    identifier = groups[4].omitVersion(),
                    fields = groups[4].splitIdentifier(),
                    version = groups[4].findVersion()
                ),
                buildMetadata = groups[5].ifBlank { null }
            )
        }

        return SemanticVersion()
    }

}