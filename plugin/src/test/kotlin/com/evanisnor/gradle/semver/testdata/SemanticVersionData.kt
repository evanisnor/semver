package com.evanisnor.gradle.semver.testdata

import com.evanisnor.gradle.semver.model.PreReleaseVersion
import com.evanisnor.gradle.semver.model.SemanticVersion
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments

class SemanticVersionData {
  companion object {

    @JvmStatic
    fun validSemanticVersionStrings(): List<Arguments> =
        listOf(
            arguments("0.0.0", SemanticVersion(0, 0, 0)),
            arguments("0.1.0", SemanticVersion(0, 1, 0)),
            arguments("1.0.0", SemanticVersion(1, 0, 0)),
            arguments("1.1.1", SemanticVersion(1, 1, 1)),
            arguments("999.99.99", SemanticVersion(999, 99, 99)),
        ) +
            validPreReleaseSemanticVersionStrings() +
            validBuildSemanticVersionStrings() +
            validPreReleaseBuildSemanticVersionStrings()

    @JvmStatic
    fun validPreReleaseSemanticVersionStrings(): List<Arguments> =
        listOf(
            arguments(
                "0.1.0-SNAPSHOT",
                SemanticVersion(preReleaseVersion = PreReleaseVersion("SNAPSHOT"))
            ),
            arguments(
                "0.0.0-SNAPSHOT",
                SemanticVersion(0, 0, 0, preReleaseVersion = PreReleaseVersion("SNAPSHOT"))
            ),
            arguments(
                "0.1.0-SNAPSHOT",
                SemanticVersion(0, 1, 0, preReleaseVersion = PreReleaseVersion("SNAPSHOT"))
            ),
            arguments(
                "1.0.0-SNAPSHOT",
                SemanticVersion(1, 0, 0, preReleaseVersion = PreReleaseVersion("SNAPSHOT"))
            ),
            arguments(
                "1.1.1-SNAPSHOT",
                SemanticVersion(1, 1, 1, preReleaseVersion = PreReleaseVersion("SNAPSHOT"))
            ),
            arguments(
                "999.99.99-SNAPSHOT",
                SemanticVersion(999, 99, 99, preReleaseVersion = PreReleaseVersion("SNAPSHOT"))
            ),
            arguments(
                "1.1.1-RC.1",
                SemanticVersion(
                    1, 1, 1, preReleaseVersion = PreReleaseVersion(identifier = "RC.1"))
            ),
            arguments(
                "999.99.99-RC.99",
                SemanticVersion(
                    999, 99, 99, preReleaseVersion = PreReleaseVersion(identifier = "RC.99"))
            ),
        )

    @JvmStatic
    fun validBuildSemanticVersionStrings(): List<Arguments> =
        listOf(
            arguments(
                "0.0.0+abcdefgh0000000",
                SemanticVersion(0, 0, 0, buildMetadata = "abcdefgh0000000")
            ),
            arguments(
                "0.1.0+abcdefgh0000000",
                SemanticVersion(0, 1, 0, buildMetadata = "abcdefgh0000000")
            ),
            arguments(
                "1.0.0+abcdefgh0000000",
                SemanticVersion(1, 0, 0, buildMetadata = "abcdefgh0000000")
            ),
            arguments(
                "1.1.1+abcdefgh0000000",
                SemanticVersion(1, 1, 1, buildMetadata = "abcdefgh0000000")
            ),
            arguments(
                "999.99.99+abcdefgh0000000",
                SemanticVersion(999, 99, 99, buildMetadata = "abcdefgh0000000")
            ),
        )

    @JvmStatic
    fun validPreReleaseBuildSemanticVersionStrings(): List<Arguments> =
        listOf(
            arguments(
                "0.1.0-RC.1+abcdefgh0000000",
                SemanticVersion(0, 1, 0)
                    .asPreReleaseVersion(PreReleaseVersion(identifier = "RC.1"))
                    .withBuildMetadata("abcdefgh0000000")),
            arguments(
                "0.0.0-RC.99+abcdefgh0000000",
                SemanticVersion(0, 0, 0)
                    .asPreReleaseVersion(PreReleaseVersion(identifier = "RC.99"))
                    .withBuildMetadata("abcdefgh0000000")),
            arguments(
                "11.10.0-SNAPSHOT+abcdefgh",
                SemanticVersion(11, 10, 0)
                    .asPreReleaseVersion(PreReleaseVersion(identifier = "SNAPSHOT"))
                    .withBuildMetadata("abcdefgh")),
        )
  }
}
