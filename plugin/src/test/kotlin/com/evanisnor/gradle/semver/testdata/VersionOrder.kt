package com.evanisnor.gradle.semver.testdata

class VersionOrder {

    companion object {

        @JvmStatic
        fun sortedVersions() = listOf(
            "1.0.1",
            "1.0.1-rc.1",
            "1.0.1-alpha",
            "1.0.0",
            "1.0.0-RC.2",
            "1.0.0-RC.1",
            "1.0.0-beta",
            "0.1.2",
            "0.1.1",
            "0.1.0",
            "0.1.0-RC.3",
            "0.1.0-RC.2",
            "0.1.0-RC.1",
            "0.1.0-alpha"
        )

        @JvmStatic
        fun semverOrgPreReleaseOrdering() = listOf(
            "1.0.0",
            "1.0.0-rc.1",
            "1.0.0-beta.11",
            "1.0.0-beta.2",
            "1.0.0-beta",
            "1.0.0-alpha.beta",
            "1.0.0-alpha.1",
            "1.0.0-alpha"
        )
    }
}