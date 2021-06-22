package com.evanisnor.gradle.semver.taskrunnables

import com.evanisnor.gradle.semver.SemanticVersion
import com.evanisnor.gradle.semver.system.Procedures

class CurrentVersion(
    private val procedures: Procedures
) : Runnable {

    override fun run() {
        val sortedVersions = procedures.sortedVersions()
        val currentVersion = when {
            // The commit is tagged. Grab the top result as the current version.
            procedures.isCommitTagged() -> sortedVersions.first()
            // There are no commits. Print the base/initial semantic version.
            sortedVersions.isEmpty() -> SemanticVersion()
            // There are commits, but this commit isn't tagged. Assume an untagged next patch version.
            else -> procedures.sortedVersions().first().nextPatch()
        }
        println("$currentVersion")
    }
}