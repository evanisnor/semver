package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.procedures.SemanticVersionParser
import com.evanisnor.gradle.semver.testdata.SemanticVersionData
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class SemanticVersionParserTest {

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun validSemanticVersionStrings() = SemanticVersionData.validSemanticVersionStrings()

        @Suppress("unused")
        @JvmStatic
        fun validPreReleaseUntaggedSemanticVersionStrings() =
            SemanticVersionData.validPreReleaseSemanticVersionStrings()
    }

    @ParameterizedTest
    @MethodSource("validSemanticVersionStrings")
    fun `Parses valid semver successfully`(semverString: String, semver: SemanticVersion) {
        val semanticVersionParser = SemanticVersionParser()
        val parsed = semanticVersionParser.parseSemVer(semverString)

        assertThat(parsed).isEqualTo(semver)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "0.0.-1",
            "0.-1.0",
            "-1.0.0",
            "-1.-1.-1",
            "-1.-1.-1-RC.-99",
            "-1.-1.-1-SNAPSHOT",
            "",
            "a",
            "abcd",
            "0000000",
            "9999999",
            "a.b.c",
        ]
    )
    fun `Invalid Semver is always Base Version`(semverString: String) {
        val semanticVersionParser = SemanticVersionParser()
        val parsed = semanticVersionParser.parseSemVer(semverString)

        assertThat(parsed).isEqualTo(SemanticVersion())
    }
}
