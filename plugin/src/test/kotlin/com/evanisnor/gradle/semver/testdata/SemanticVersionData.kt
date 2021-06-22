package com.evanisnor.gradle.semver.testdata

import com.evanisnor.gradle.semver.SemanticVersion
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments

class SemanticVersionData {
    companion object {

        @JvmStatic
        fun validSemanticVersionStrings(): List<Arguments> = listOf(
            arguments("0.0.0", SemanticVersion(0, 0, 0, isTagged = true)),
            arguments("0.1.0", SemanticVersion(0, 1, 0, isTagged = true)),
            arguments("1.0.0", SemanticVersion(1, 0, 0, isTagged = true)),
            arguments("1.1.1", SemanticVersion(1, 1, 1, isTagged = true)),
            arguments("999.99.99", SemanticVersion(999, 99, 99, isTagged = true)),
        ) + validPreReleaseUntaggedSemanticVersionStrings() +
                validPreReleaseCandidateSemanticVersionStrings() +
                validBuildSemanticVersionStrings() +
                validPreReleaseBuildSemanticVersionStrings()

        @JvmStatic
        fun validPreReleaseUntaggedSemanticVersionStrings(): List<Arguments> = listOf(
            arguments("0.1.0-SNAPSHOT", SemanticVersion()),
            arguments("0.0.0-SNAPSHOT", SemanticVersion(0, 0, 0, isTagged = false)),
            arguments("0.1.0-SNAPSHOT", SemanticVersion(0, 1, 0, isTagged = false)),
            arguments("1.0.0-SNAPSHOT", SemanticVersion(1, 0, 0, isTagged = false)),
            arguments("1.1.1-SNAPSHOT", SemanticVersion(1, 1, 1, isTagged = false)),
            arguments("999.99.99-SNAPSHOT", SemanticVersion(999, 99, 99, isTagged = false)),
        )

        @JvmStatic
        fun validPreReleaseCandidateSemanticVersionStrings(): List<Arguments> = listOf(
            arguments("1.1.1-RC.1", SemanticVersion(1, 1, 1, candidate = 1, isTagged = true)),
            arguments("999.99.99-RC.99", SemanticVersion(999, 99, 99, candidate = 99, isTagged = true)),
        )

        @JvmStatic
        fun validBuildSemanticVersionStrings(): List<Arguments> = listOf(
            arguments(
                "0.0.0+abcdefgh0000000",
                SemanticVersion(0, 0, 0, build = "abcdefgh0000000", isTagged = true)
            ),
            arguments(
                "0.1.0+abcdefgh0000000",
                SemanticVersion(0, 1, 0, build = "abcdefgh0000000", isTagged = true)
            ),
            arguments(
                "1.0.0+abcdefgh0000000",
                SemanticVersion(1, 0, 0, build = "abcdefgh0000000", isTagged = true)
            ),
            arguments(
                "1.1.1+abcdefgh0000000",
                SemanticVersion(1, 1, 1, build = "abcdefgh0000000", isTagged = true)
            ),
            arguments(
                "999.99.99+abcdefgh0000000",
                SemanticVersion(999, 99, 99, build = "abcdefgh0000000", isTagged = true)
            ),
        )

        @JvmStatic
        fun validPreReleaseBuildSemanticVersionStrings(): List<Arguments> = listOf(
            arguments(
                "0.1.0-RC.1+abcdefgh0000000",
                SemanticVersion(0, 1, 0, candidate = 1, build = "abcdefgh0000000", isTagged = true)
            ),
            arguments(
                "0.0.0-RC.99+abcdefgh0000000",
                SemanticVersion(0, 0, 0, candidate = 99, build = "abcdefgh0000000", isTagged = true)
            ),
            arguments(
                "11.10.0-SNAPSHOT+abcdefgh",
                SemanticVersion(11, 10, 0, build = "abcdefgh", isTagged = false)
            ),
        )
    }

}