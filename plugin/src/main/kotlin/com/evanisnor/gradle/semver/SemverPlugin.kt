package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.behavior.BehaviorModule
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.TaskGenerator
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * USAGE gradle versions gradle latestVersion gradle currentVersion gradle nextPatch gradle
 * nextMinor gradle nextMajor gradle nextCandidate gradle releaseNextPatch gradle
 * releaseNextPatchCandidate gradle releaseNextMinor gradle releaseNextMinorCandidate gradle
 * releaseNextMajor gradle releaseNextMajorCandidate gradle releaseNextCandidate
 *
 * PARAMS -Pdry-run
 */
class SemverPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("semanticVersion", SemanticVersionConfiguration::class.java)

        val runtimeModule = RuntimeModule(project)
        val taskModule = BehaviorModule(runtimeModule)

        TaskGenerator(runtimeModule, taskModule).registerTasks()

        project.version = taskModule.currentVersion().determineCurrentVersion()
    }
}
