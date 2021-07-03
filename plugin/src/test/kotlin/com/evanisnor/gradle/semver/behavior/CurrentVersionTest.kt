package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.Procedures
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class CurrentVersionTest {

    private val procedures: Procedures = mockk()
    private val configuration: SemanticVersionConfiguration = mockk()

    private val currentVersion = CurrentVersion(
        procedures = procedures,
        configuration = configuration
    )

    @Test
    fun `Returns default untagged when there are no versions`() {
        every { configuration.untaggedIdentifier } returns "SNAPSHOT"

        val currentVersion = currentVersion.determineCurrentVersion(emptyList())

        assertThat(currentVersion).isEqualTo(
            SemanticVersion(
                0, 1, 0,
                preReleaseVersion = PreReleaseVersion("SNAPSHOT")
            )
        )
    }

    @Test
    fun `Returns version from tag when commit is tagged`() {
        val sortedVersions = listOf(
            SemanticVersion(1, 0, 0)
        )
        every { procedures.isCommitTagged() } returns true

        assertThat(currentVersion.determineCurrentVersion(sortedVersions)).isEqualTo(
            SemanticVersion(1, 0, 0)
        )
    }

    @Test
    fun `Returns next untagged version when commit is not tagged`() {
        val sortedVersions = listOf(
            SemanticVersion(1, 0, 0)
        )
        every { procedures.isCommitTagged() } returns false
        with(configuration) {
            every { untaggedIdentifier } returns "SNAPSHOT"
            every { incrementRule } returns SemanticVersionConfiguration.IncrementRule.IncrementPatch
        }

        val currentVersion = currentVersion.determineCurrentVersion(sortedVersions)

        assertThat(currentVersion).isEqualTo(
            SemanticVersion(
                1, 0, 1,
                preReleaseVersion = PreReleaseVersion("SNAPSHOT")
            )
        )
    }
}