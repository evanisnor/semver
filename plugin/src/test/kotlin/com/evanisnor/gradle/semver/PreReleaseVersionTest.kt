package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class PreReleaseVersionTest {

    @Test
    fun `Increments PreReleaseVersion by one`() {
        val semver = SemanticVersion(0, 1, 0)
        assertThat(semver.nextPreReleaseVersion(identifier = "RC"))
            .isEqualTo(
                SemanticVersion(
                    0,
                    1,
                    0,
                    preReleaseVersion = PreReleaseVersion(identifier = "RC", version = 1)
                )
            )
    }

    @Test
    fun `Allows unversioned pre-release identifiers`() {
        val preReleaseVersion = PreReleaseVersion("SNAPSHOT")
        assertThat(preReleaseVersion.toString()).isEqualTo("SNAPSHOT")
    }
}
