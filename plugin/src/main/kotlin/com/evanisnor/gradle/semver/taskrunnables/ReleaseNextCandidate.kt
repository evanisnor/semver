package com.evanisnor.gradle.semver.taskrunnables

import com.evanisnor.gradle.semver.NoVersionsFoundError
import com.evanisnor.gradle.semver.system.Procedures

class ReleaseNextCandidate(
    private val procedures: Procedures
) : Runnable {

    override fun run() {
        val versions = procedures.sortedVersions()
        if (versions.isEmpty()) {
            throw NoVersionsFoundError()
        } else {
            val next = procedures.sortedVersions().first().nextCandidate().asTagged()
            procedures.tagAndPush(next)
            println("Created tag '$next' and pushed to origin")
        }
    }
}