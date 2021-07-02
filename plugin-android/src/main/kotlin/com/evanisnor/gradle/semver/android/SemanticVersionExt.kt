package com.evanisnor.gradle.semver.android

import com.evanisnor.gradle.semver.model.SemanticVersion

fun SemanticVersion.toVersionCode(preReleaseIdentifiers: List<String>): Int {
    val preReleaseIdentifierIndex = preReleaseVersion?.let { preReleaseVersion ->
        preReleaseIdentifiers.indexOf(preReleaseVersion.identifier)
    } ?: 0
    return Integer.parseInt(
        "$major" +
                "$minor".padStart(2, '0') +
                "$patch".padStart(2, '0') +
                "$preReleaseIdentifierIndex" +
                "${preReleaseVersion?.version ?: 0}".padStart(2, '0')
    )
}