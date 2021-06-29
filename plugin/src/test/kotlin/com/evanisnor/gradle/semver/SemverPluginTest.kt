package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.util.copyInto
import com.evanisnor.gradle.semver.util.runGradleCommand
import com.google.common.truth.Truth.assertThat
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File


class SemverPluginTest {

    @TempDir
    lateinit var testProject: File

    @BeforeEach
    fun setup() {
        javaClass.classLoader.getResource("settings.gradle.kts")!!.copyInto(testProject)
    }

    @AfterEach
    fun teardown() {
        testProject.delete()
    }

    @Test
    fun printConfiguration() {
        javaClass.classLoader.getResource("build.gradle.kts")!!.copyInto(testProject)

        val result = testProject.runGradleCommand("versionConfiguration")
        assertThat(result.task(":versionConfiguration")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
        assertThat(result.output).contains(
            """
                 > Task :versionConfiguration
                                   remote: origin
                                   prefix: v
                    preReleaseIdentifiers: [alpha, beta, gamma]
                 initialPreReleaseVersion: 1
                       preReleaseMetadata: ShortSha
                          releaseMetadata: Timestamp
                    untaggedIncrementRule: IncrementMajor
                       untaggedIdentifier: SNAPSHOT
        """.trimIndent()
        )
    }


}