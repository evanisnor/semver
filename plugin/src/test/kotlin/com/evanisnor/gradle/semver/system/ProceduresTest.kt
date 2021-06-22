package com.evanisnor.gradle.semver.system

import com.evanisnor.gradle.semver.SemanticVersion
import com.evanisnor.gradle.semver.captureStandardOutput
import com.evanisnor.gradle.semver.testdata.VersionOrder
import com.evanisnor.gradle.semver.toSemanticVersion
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ProceduresTest {

    private val git: Git = mockk(relaxed = true)
    private val procedures = Procedures(git)

    @Test
    fun `Returns true when git commit is tagged`() {
        every { git.isCommitTagged() } returns true
        assertThat(procedures.isCommitTagged()).isTrue()
    }

    @Test
    fun `Returns false when git commit is not tagged`() {
        every { git.isCommitTagged() } returns false
        assertThat(procedures.isCommitTagged()).isFalse()
    }

    @Test
    fun `Sorts versions according to semver rules`() {
        val sortedSemanticVersions = VersionOrder.sortedVersions().map {
            it.toSemanticVersion()
        }
        every { git.listAllTags() } returns sortedSemanticVersions.map { it.toString() }
        assertThat(procedures.sortedVersions()).isEqualTo(sortedSemanticVersions)
    }

    @Test
    fun `tagAndPush tags and pushes`() {
        val printed = captureStandardOutput {
            procedures.tagAndPush(
                SemanticVersion(0, 1, 0, isTagged = false)
            )
        }

        verify { git.createTag("0.1.0") }
        verify { git.push("origin", "0.1.0") }
        assertThat(printed).isEqualTo("0.1.0 released\n")
    }

    @Test
    fun `tagAndPush dry run prints git commands`() {

        val printed = captureStandardOutput {
            procedures.tagAndPush(
                SemanticVersion(0, 1, 0, isTagged = false),
                isDryRun = true
            )
        }

        assertThat(printed).isEqualTo(
            """
                **DRY RUN**
                git tag 0.1.0
                git push origin 0.1.0
                
            """.trimIndent()
        )
    }
}