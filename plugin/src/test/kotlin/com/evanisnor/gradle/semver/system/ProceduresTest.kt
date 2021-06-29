package com.evanisnor.gradle.semver.system

import com.evanisnor.gradle.semver.procedures.SemanticVersionParser
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.Procedures
import com.evanisnor.gradle.semver.testdata.VersionOrder
import com.evanisnor.gradle.semver.util.captureStandardOutput
import com.evanisnor.gradle.semver.util.toSemanticVersion
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.gradle.api.Project
import org.junit.jupiter.api.Test

class ProceduresTest {

    private val git: Git = mockk(relaxed = true)
    private val project: Project = mockk(relaxed = true)
    private val parser: SemanticVersionParser = mockk(relaxed = true) {
        every { parseSemVer(any()) } answers {
            if (invocation.args.first() is String) {
                (invocation.args.first() as String).toSemanticVersion()
            } else {
                SemanticVersion()
            }
        }
    }
    private val configuration: SemanticVersionConfiguration = mockk {
        every { remote } returns "testorigin"
    }
    private val procedures = Procedures(git, project, parser, configuration)

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
    fun `Sorts more versions according to semver rules`() {
        val sortedSemanticVersions = VersionOrder.semverOrgPreReleaseOrdering().map {
            it.toSemanticVersion()
        }
        every { git.listAllTags() } returns sortedSemanticVersions.map { it.toString() }
        assertThat(procedures.sortedVersions()).isEqualTo(sortedSemanticVersions)
    }

    @Test
    fun `tagAndPush tags and pushes`() {
        val version = SemanticVersion(0, 1, 0)

        val printed = captureStandardOutput {
            procedures.tagAndPush(version)
        }

        verify { git.createTag(version) }
        verify { git.push("testorigin", version) }
        assertThat(printed).isEqualTo("0.1.0 released\n")
    }

    @Test
    fun `tagAndPush dry run prints git commands`() {
        every { project.properties } returns mapOf("dry-run" to "")
        val printed = captureStandardOutput {
            procedures.tagAndPush(SemanticVersion(0, 1, 0))
        }

        assertThat(printed).isEqualTo(
            """
                **DRY RUN**
                git tag 0.1.0
                git push testorigin 0.1.0
                
            """.trimIndent()
        )
    }
}