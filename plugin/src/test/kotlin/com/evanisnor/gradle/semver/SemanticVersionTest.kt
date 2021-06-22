package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.testdata.SemanticVersionData
import com.evanisnor.gradle.semver.testdata.VersionOrder
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
    fun `Produces expected semver from SemanticVersion data class`(semverString: String, semver: SemanticVersion) {
        assertThat(semver.toString()).isEqualTo(semverString)
    }

    @Test
    fun `Comparable according to semver rules`() {
        val sortedSemanticVersions = VersionOrder.sortedVersions().map {
            it.toSemanticVersion()
        }.reversed()
        assertThat(sortedSemanticVersions.sortedBy { it }).isEqualTo(sortedSemanticVersions)
    }

    @Test
    fun `Increments do not modify original data object`() {
        val semver = SemanticVersion(0, 1, 0)
        semver.nextMajor()
        semver.nextMinor()
        semver.nextPatch()
        semver.nextCandidate()
        semver.asBuild("abcd")
        semver.asTagged()
        assertThat(semver).isEqualTo(SemanticVersion(0, 1, 0))
    }

    @Test
    fun `Increments Major by one`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.nextMajor()).isEqualTo(SemanticVersion(1, 0, 0, isTagged = false))
    }

    @Test
    fun `Increments Minor by one`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.nextMinor()).isEqualTo(SemanticVersion(0, 2, 0, isTagged = false))
    }

    @Test
    fun `Increments Patch by one`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.nextPatch()).isEqualTo(SemanticVersion(0, 1, 1, isTagged = false))
    }

    @Test
    fun `Increments Candidate by one`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.nextCandidate()).isEqualTo(SemanticVersion(0, 1, 0, candidate = 1, isTagged = false))
    }

    @Test
    fun `Include build value`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.asBuild("abcd")).isEqualTo(SemanticVersion(0, 1, 0, isTagged = false, build = "abcd"))
    }

    @Test
    fun `asTagged updates tagged flag`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.isTagged).isFalse()
        assertThat(semver.asTagged().isTagged).isTrue()
    }
}