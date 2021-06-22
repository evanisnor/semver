package com.evanisnor.gradle.semver.taskrunnables

import com.evanisnor.gradle.semver.NoVersionsFoundError
import com.evanisnor.gradle.semver.system.Procedures

class NextMinor(
    private val procedures: Procedures
) : Runnable {

    override fun run() {
        val versions = procedures.sortedVersions()
        if (versions.isEmpty()) {
            println(NoVersionsFoundError().message)
        } else {
            val next = procedures.sortedVersions().first().nextMinor().asTagged()
            println("$next")
        }
    }
}