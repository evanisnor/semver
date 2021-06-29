package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.model.NoVersionsFoundError
import com.evanisnor.gradle.semver.procedures.Procedures

class LatestVersion(
    private val procedures: Procedures
) : Runnable {

    override fun run() {
        val versions = procedures.sortedVersions()
        if (versions.isEmpty()) {
            println(NoVersionsFoundError().message)
        } else {
            println(versions.first())
        }
    }
}