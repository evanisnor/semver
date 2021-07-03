package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.MetadataBuilder
import com.evanisnor.gradle.semver.procedures.Procedures
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ReleasePreReleaseTest {

    private val procedures: Procedures = mockk()
    private val configuration: SemanticVersionConfiguration = mockk {
        every { incrementRule } returns SemanticVersionConfiguration.IncrementRule.IncrementMinor
        every { initialPreReleaseVersion } returns 1
    }
    private val metadataBuilder: MetadataBuilder = mockk()
    private val preReleaseIdentifier = "beta"

    private val releaseNextPreRelease = ReleasePreRelease(
        procedures = procedures,
        configuration = configuration,
        metadataBuilder = metadataBuilder,
        preReleaseIdentifier = preReleaseIdentifier
    )

    @Test
    fun `Pre-release base version when there are no previous versions`() {
        val preRelease = releaseNextPreRelease.determineNextPreRelease(emptyList())

        assertThat(preRelease).isEqualTo(
            SemanticVersion(
                0, 1, 0, preReleaseVersion = PreReleaseVersion(
                    identifier = preReleaseIdentifier,
                    version = 1
                )
            )
        )
    }

    @Test
    fun `Increments version for fresh pre-release`() {
        val sortedVersions = listOf(
            SemanticVersion(0, 1, 0)
        )

        val preRelease = releaseNextPreRelease.determineNextPreRelease(sortedVersions)

        assertThat(preRelease).isEqualTo(
            SemanticVersion(
                0, 2, 0,
                preReleaseVersion = PreReleaseVersion(
                    identifier = preReleaseIdentifier,
                    version = 1
                )
            )
        )
    }

    @Test
    fun `Increments previous pre-release for next pre-release`() {
        val sortedVersions = listOf(
            SemanticVersion(
                0, 2, 0, preReleaseVersion = PreReleaseVersion(
                    identifier = preReleaseIdentifier,
                    version = 1
                )
            )
        )

        val preRelease = releaseNextPreRelease.determineNextPreRelease(sortedVersions)

        assertThat(preRelease).isEqualTo(
            SemanticVersion(
                0, 2, 0, preReleaseVersion = PreReleaseVersion(
                    identifier = preReleaseIdentifier,
                    version = 2
                )
            )
        )
    }
}