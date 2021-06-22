package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.system.Procedures
import com.evanisnor.gradle.semver.taskrunnables.*
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class TaskRunnableTests {

    companion object {

        fun infoMessage(version: String) = "$version\n"
        fun releaseMessage(version: String) = "Created tag '$version' and pushed to origin\n"

        @JvmStatic
        fun testVersions() = listOf(
            SemanticVersion(1, 1, 0, isTagged = true),
            SemanticVersion(1, 0, 0, isTagged = true),
            SemanticVersion(0, 2, 1, isTagged = true),
            SemanticVersion(0, 2, 0, isTagged = true),
            SemanticVersion(0, 1, 0, isTagged = true),
        )

        @JvmStatic
        fun taskRunnablesAndExpectedMessageWhenTagged() = listOf(
            arguments(CurrentVersion::class, infoMessage("1.1.0")),
            arguments(LatestVersion::class, infoMessage("1.1.0")),
            arguments(ListVersions::class, infoMessage("1.1.0\n1.0.0\n0.2.1\n0.2.0\n0.1.0")),
            arguments(NextMajor::class, infoMessage("2.0.0")),
            arguments(NextMinor::class, infoMessage("1.2.0")),
            arguments(NextPatch::class, infoMessage("1.1.1")),
            arguments(NextCandidate::class, infoMessage("1.1.0-RC.1")),
        )

        @JvmStatic
        fun taskRunnablesExpectedReleaseMessage() = listOf(
            arguments(ReleaseNextMajor::class, releaseMessage("2.0.0")),
            arguments(ReleaseNextMinor::class, releaseMessage("1.2.0")),
            arguments(ReleaseNextPatch::class, releaseMessage("1.1.1")),
            arguments(ReleaseNextCandidate::class, releaseMessage("1.1.0-RC.1")),
            arguments(ReleaseNextMajorCandidate::class, releaseMessage("2.0.0-RC.1")),
            arguments(ReleaseNextMinorCandidate::class, releaseMessage("1.2.0-RC.1")),
            arguments(ReleaseNextPatchCandidate::class, releaseMessage("1.1.1-RC.1")),
        )

        @JvmStatic
        fun taskRunnablesExpectedReleaseVersion() = listOf(
            arguments(ReleaseNextMajor::class, SemanticVersion(2, 0, 0, isTagged = true)),
            arguments(ReleaseNextMinor::class, SemanticVersion(1, 2, 0, isTagged = true)),
            arguments(ReleaseNextPatch::class, SemanticVersion(1, 1, 1, isTagged = true)),
            arguments(ReleaseNextCandidate::class, SemanticVersion(1, 1, 0, candidate = 1, isTagged = true)),
            arguments(ReleaseNextMajorCandidate::class, SemanticVersion(2, 0, 0, candidate = 1, isTagged = true)),
            arguments(ReleaseNextMinorCandidate::class, SemanticVersion(1, 2, 0, candidate = 1, isTagged = true)),
            arguments(ReleaseNextPatchCandidate::class, SemanticVersion(1, 1, 1, candidate = 1, isTagged = true)),
        )

        @JvmStatic
        fun taskRunnablesAndExpectedMessageWhenNotTagged() = listOf(
            arguments(CurrentVersion::class, infoMessage("1.1.1-SNAPSHOT")),
            arguments(LatestVersion::class, infoMessage("1.1.0")),
            arguments(ListVersions::class, infoMessage("1.1.0\n1.0.0\n0.2.1\n0.2.0\n0.1.0")),
            arguments(NextMajor::class, infoMessage("2.0.0")),
            arguments(NextMinor::class, infoMessage("1.2.0")),
            arguments(NextPatch::class, infoMessage("1.1.1")),
            arguments(NextCandidate::class, infoMessage("1.1.0-RC.1")),
        )
    }

    private val procedures: Procedures = mockk(relaxed = true)

    @ParameterizedTest
    @MethodSource("taskRunnablesAndExpectedMessageWhenTagged")
    fun taskRunnablesPrintExpectedWhenTagged(taskRunnableClass: KClass<Runnable>, expectedMessage: String) {
        every { procedures.isCommitTagged() } returns true
        every { procedures.sortedVersions() } returns testVersions()

        val printed = captureStandardOutput {
            taskRunnableClass.primaryConstructor!!.call(procedures).run()
        }

        assertThat(printed).isEqualTo(expectedMessage)
    }

    @ParameterizedTest
    @MethodSource("taskRunnablesAndExpectedMessageWhenNotTagged")
    fun taskRunnablesPrintExpectedNotTagged(taskRunnableClass: KClass<Runnable>, expectedMessage: String) {
        every { procedures.isCommitTagged() } returns false
        every { procedures.sortedVersions() } returns testVersions()

        val printed = captureStandardOutput {
            taskRunnableClass.primaryConstructor!!.call(procedures).run()
        }

        assertThat(printed).isEqualTo(expectedMessage)
    }

    @ParameterizedTest
    @MethodSource("taskRunnablesExpectedReleaseMessage")
    fun taskRunnablesPrintExpectedReleaseMessage(taskRunnableClass: KClass<Runnable>, expectedMessage: String) {
        every { procedures.isCommitTagged() } returns true
        every { procedures.sortedVersions() } returns testVersions()

        val printed = captureStandardOutput {
            taskRunnableClass.primaryConstructor!!.call(procedures).run()
        }

        assertThat(printed).isEqualTo(expectedMessage)
    }

    @ParameterizedTest
    @MethodSource("taskRunnablesExpectedReleaseVersion")
    fun taskRunnablesReleaseExpectedVersion(taskRunnableClass: KClass<Runnable>, expectedVersion: SemanticVersion) {
        every { procedures.isCommitTagged() } returns true
        every { procedures.sortedVersions() } returns testVersions()

        taskRunnableClass.primaryConstructor!!.call(procedures).run()

        verify { procedures.tagAndPush(expectedVersion) }
    }
}