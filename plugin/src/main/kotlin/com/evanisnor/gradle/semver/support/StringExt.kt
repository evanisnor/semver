package com.evanisnor.gradle.semver.support

/**
 * Numeric identifiers always have lower precedence than non-numeric identifiers.
 * https://semver.org/#spec-item-11
 */
fun String.compareToAsIntsIfPossiblePlease(other: String): Int =
    when {
        this.toIntOrNull() == null && other.toIntOrNull() != null -> 1
        this.toIntOrNull() != null && other.toIntOrNull() == null -> -1
        this.toIntOrNull() != null && other.toIntOrNull() != null -> this.toInt() - other.toInt()
        else -> compareTo(other, ignoreCase = true)
    }

/**
 * Follow semver conventions to remove any last integer field from a pre-release identifier
 */
fun String.omitVersion(): String = this.split(".").dropLastWhile { it.toIntOrNull() != null }.joinToString(".")

/**
 * Follow semver conventions to split a pre-release identifier into a list of fields
 */
fun String.splitIdentifier(): List<String> = this.split(".").dropLastWhile { it.toIntOrNull() != null }

/**
 * Follow semver conventions to extract the last field in a pre-release identifier if it is an integer
 */
fun String.findVersion(): Int? = this.split(".").lastOrNull { it.toIntOrNull() != null }?.toInt()