package com.evanisnor.gradle.semver.model

import com.evanisnor.gradle.semver.support.compareToAsIntsIfPossiblePlease
import com.evanisnor.gradle.semver.support.findVersion
import com.evanisnor.gradle.semver.support.splitIdentifier

/**
 * A pre-release version MAY be denoted by appending a hyphen and a series of dot separated
 * identifiers immediately following the patch version. Identifiers MUST comprise only ASCII
 * alphanumerics and hyphens [0-9A-Za-z-]. Identifiers MUST NOT be empty. Numeric identifiers MUST
 * NOT include leading zeroes. Pre-release versions have a lower precedence than the associated
 * normal version.
 *
 * Examples: 1.0.0-alpha, 1.0.0-alpha.1, 1.0.0-0.3.7, 1.0.0-x.7.z.92, 1.0.0-x-y-z.–.
 *
 * https://semver.org/#spec-item-9
 */
data class PreReleaseVersion(
    /* Includes hyphens an dots */
    val identifier: String,
    /* Includes hyphens */
    val fields: List<String> = identifier.splitIdentifier(),
    val version: Int? = identifier.findVersion()
) : Comparable<PreReleaseVersion> {

    fun next() =
        PreReleaseVersion(identifier = identifier, fields = fields, version = (version ?: 0) + 1)

    override fun toString(): String =
        fields.joinToString(".") +
                if (version != null) {
                    ".$version"
                } else {
                    ""
                }

    /**
     * Precedence for two pre-release versions with the same major, minor, and patch version MUST be
     * determined by comparing each dot separated identifier from left to right until a difference
     * is found as follows:
     *
     * 1. Identifiers consisting of only digits are compared numerically.
     * 2. Identifiers with letters or hyphens are compared lexically in ASCII sort order.
     * 3. Numeric identifiers always have lower precedence than non-numeric identifiers.
     * 4. A larger set of pre-release fields has a higher precedence than a smaller set, if all of
     * the preceding identifiers are equal.
     *
     * https://semver.org/#spec-item-11
     */
    override fun compareTo(other: PreReleaseVersion): Int {
        fields.forEachIndexed { index, leftField ->
            if (index >= fields.size) {
                return -1
            } else if (index >= other.fields.size) {
                return 1
            }
            val c = leftField.compareToAsIntsIfPossiblePlease(other.fields[index])
            if (c != 0) {
                return c
            }
        }
        return (version ?: 0) - (other.version ?: 0)
    }
}

/**
 * Pre-release versions have a lower precedence than the associated normal version.
 * https://semver.org/#spec-item-9
 */
fun PreReleaseVersion?.compareTo(other: PreReleaseVersion?): Int =
    when {
        this == null -> 1
        other == null -> -1
        else -> this.compareTo(other)
    }
