package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.testdata.SemanticVersionData
import com.evanisnor.gradle.semver.testdata.VersionOrder
import com.evanisnor.gradle.semver.util.toSemanticVersion
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SemanticVersionTest {

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun validSemanticVersionStrings() = SemanticVersionData.validSemanticVersionStrings()
    }

    @ParameterizedTest
    @MethodSource("validSemanticVersionStrings")
    fun `Produces expected semver from SemanticVersion data class`(
        semverString: String,
        semver: SemanticVersion
    ) {
        assertThat(semver.toString()).isEqualTo(semverString)
    }

    @Test
    fun `Comparable according to semver rules`() {
        val sortedSemanticVersions =
            VersionOrder.sortedVersions().map { it.toSemanticVersion() }.reversed()
        assertThat(sortedSemanticVersions.sortedBy { it }).isEqualTo(sortedSemanticVersions)
    }

    @Test
    fun `Increments do not modify original data object`() {
        val semver = SemanticVersion(0, 1, 0)
        semver.nextMajor()
        semver.nextMinor()
        semver.nextPatch()
        semver.increment(SemanticVersionConfiguration.UntaggedIncrementRule.IncrementPatch)
        semver.nextPreReleaseVersion(identifier = "RC", initialVersion = 5)
        semver.withBuildMetadata("abcd")
        assertThat(semver).isEqualTo(SemanticVersion(0, 1, 0))
    }

    @Test
    fun `Increments Major by one`() {
        val semver = SemanticVersion(0, 1, 0).nextMajor()

        assertThat(semver).isEqualTo(SemanticVersion(1, 0, 0))
    }

    @Test
    fun `Increments Major by one from increment rule`() {
        val semver = SemanticVersion(0, 1, 0)
            .increment(SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMajor)

        assertThat(semver).isEqualTo(
            SemanticVersion(1, 0, 0)
        )
    }

    @Test
    fun `Increments Minor by one`() {
        val semver = SemanticVersion(0, 1, 0).nextMinor()

        assertThat(semver).isEqualTo(SemanticVersion(0, 2, 0))
    }

    @Test
    fun `Increments Minor by one from increment rule`() {
        val semver = SemanticVersion(0, 1, 0)
            .increment(SemanticVersionConfiguration.UntaggedIncrementRule.IncrementMinor)

        assertThat(semver).isEqualTo(SemanticVersion(0, 2, 0))
    }

    @Test
    fun `Increments Patch by one`() {
        val semver = SemanticVersion(0, 1, 0).nextPatch()

        assertThat(semver).isEqualTo(SemanticVersion(0, 1, 1))
    }

    @Test
    fun `Increments Patch by one from increment rule`() {
        val semver = SemanticVersion(0, 1, 0)
            .increment(SemanticVersionConfiguration.UntaggedIncrementRule.IncrementPatch)

        assertThat(semver).isEqualTo(SemanticVersion(0, 1, 1))
    }

    @Test
    fun `Include build value`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.withBuildMetadata("abcd"))
            .isEqualTo(SemanticVersion(0, 1, 0, buildMetadata = "abcd"))
    }
}
