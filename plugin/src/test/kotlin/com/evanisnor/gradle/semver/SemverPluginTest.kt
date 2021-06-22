package com.evanisnor.gradle.semver

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
        javaClass.classLoader.getResource("settings.gradle.kts")!!.toFile().copyInto(testProject)
        javaClass.classLoader.getResource("build.gradle.kts")!!.toFile().copyInto(testProject)
    }

    @AfterEach
    fun teardown() {
        testProject.delete()
    }

    @Test
    fun runNextMinor() {
        val result = testProject.runGradleCommand("nextMinor")
        assertThat(result.task(":nextMinor")!!.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }


}