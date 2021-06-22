package com.evanisnor.gradle.semver.android

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * TODO
 *
 * USAGE
 * gradle versions
 * gradle latestVersion
 * gradle currentVersion
 * gradle nextPatch
 * gradle nextMinor
 * gradle nextMajor
 * gradle nextCandidate
 * gradle releaseNextPatch
 * gradle releaseNextPatchCandidate
 * gradle releaseNextMinor
 * gradle releaseNextMinorCandidate
 * gradle releaseNextMajor
 * gradle releaseNextMajorCandidate
 * gradle releaseNextCandidate
 *
 * PARAMS
 * -Pdry-run
 */
class SemverPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val currentVersion = ""

//        project.plugins.apply()

        project.version = currentVersion.toString()
        project.extensions.findByType(ApplicationExtension::class.java)?.let {
            it.defaultConfig.versionName = currentVersion.toString()
            it.defaultConfig.versionCode = currentVersion.toInt()
        }

    }
}