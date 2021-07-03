package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.behavior.BehaviorModule
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.*
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Semantic Versioning plugin for Gradle projects
 *
 * See [SemanticVersionConfiguration] for "semanticVersion" plugin configuration
 *
 * Tasks:
 *  - currentVersion
 *  - latestVersion
 *  - listVersions
 *  - release<Pre-Release Identifier>
 *  - versionConfiguration
 *
 * Parameters:
 *  - dry-run
 */
class SemverPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        // Register configuration extension
        project.extensions.create("semanticVersion", SemanticVersionConfiguration::class.java)

        // Configure runtime dependencies
        val runtimeModule = RuntimeModule(project)
        val behaviorModule = BehaviorModule(runtimeModule)

        // Register tasks
        project.tasks.register("versionConfiguration", PrintConfigurationTask::class.java, runtimeModule)
        project.tasks.register("versions", ListVersionsTask::class.java, behaviorModule)
        project.tasks.register("latestVersion", LatestVersionTask::class.java, behaviorModule)
        project.tasks.register("currentVersion", CurrentVersionTask::class.java, behaviorModule)
        project.tasks.register("releaseMajor", ReleaseNextMajorTask::class.java, behaviorModule)
        project.tasks.register("releaseMinor", ReleaseNextMinorTask::class.java, behaviorModule)
        project.tasks.register("releasePatch", ReleaseNextPatchTask::class.java, behaviorModule)

        // Register pre release tasks based on configuration
        runtimeModule.configuration().preReleaseIdentifiers.forEach { preReleaseIdentifier ->
            // Register a new release task to release the next pre-release version
            project.tasks.register(
                "release${preReleaseIdentifier.capitalize()}",
                ReleaseNextPreReleaseTask::class.java,
                behaviorModule,
                preReleaseIdentifier
            )
        }

        // Set the project's version
        applyProjectVersion(project, runtimeModule, behaviorModule)
    }

    private fun applyProjectVersion(project: Project, runtimeModule: RuntimeModule, behaviorModule: BehaviorModule) {
        val sortedVersions = runtimeModule.procedures().sortedVersions()
        project.version = behaviorModule.currentVersion().determineCurrentVersion(sortedVersions)
    }
}
