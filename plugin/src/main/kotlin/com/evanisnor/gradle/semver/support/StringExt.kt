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
