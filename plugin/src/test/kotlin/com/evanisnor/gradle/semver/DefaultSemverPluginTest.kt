package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.util.copyInto
import com.evanisnor.gradle.semver.util.rename
import com.evanisnor.gradle.semver.util.runGradleCommand
import com.google.common.truth.Truth.assertThat
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File


class DefaultSemverPluginTest {

    @TempDir
    lateinit var testProject: File

    @BeforeEach
    fun setup() {
        javaClass.classLoader.getResource("settings.gradle.kts")!!
            .copyInto(testProject)
        javaClass.classLoader.getResource("default.build.gradle.kts")!!
            .copyInto(testProject)
            .rename("build.gradle.kts")
    }

    @AfterEach
    fun teardown() {
        testProject.delete()
    }

    @Test
    fun printDefaultConfiguration() {
        val result = testProject.runGradleCommand("versionConfiguration")
        assertThat(result.task(":versionConfiguration")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
        assertThat(result.output).contains(
            """
                 > Task :versionConfiguration
                                   remote: origin
                                   prefix: 
                    preReleaseIdentifiers: [alpha, beta]
                 initialPreReleaseVersion: 0
                       preReleaseMetadata: None
                          releaseMetadata: None
                    untaggedIncrementRule: IncrementPatch
                       untaggedIdentifier: SNAPSHOT
        """.trimIndent()
        )
    }

}