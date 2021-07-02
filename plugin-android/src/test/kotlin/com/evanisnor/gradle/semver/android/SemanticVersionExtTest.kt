package com.evanisnor.gradle.semver.android

import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource


class SemanticVersionExtTest {

    companion object {
        val preReleaseIdentifiers = listOf("alpha", "beta", "gamma", "delta", "epsilon", "zeta", "eta")

        @Suppress("unused")
        @JvmStatic
        fun versionCodes(): List<Arguments> = listOf(
            arguments(SemanticVersion(0, 0, 0), 0),
            arguments(SemanticVersion(0, 1, 0), 100000),
            arguments(SemanticVersion(1, 0, 0), 10000000),
            arguments(SemanticVersion(100, 0, 0), 1000000000),
            arguments(SemanticVersion(214, 74, 83), 2147483000),
            arguments(SemanticVersion(213, 99, 99), 2139999000),
            arguments(
                SemanticVersion(
                    214, 74, 83,
                    preReleaseVersion = PreReleaseVersion("eta", version = 47)
                ), 2147483647
            ),
        )
    }

    @ParameterizedTest
    @MethodSource("versionCodes")
    fun `Calculates correct version code from SemanticVersion`(semanticVersion: SemanticVersion, versionCode: Int) {
        assertThat(semanticVersion.toVersionCode(preReleaseIdentifiers)).isEqualTo(versionCode)
    }
}