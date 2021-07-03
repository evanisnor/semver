package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.behavior.BehaviorModule
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.TaskGenerator
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
 *  - nextVersion
 *  - next<Pre-release Identifier>
 *  - release<Pre-Release Identifier>
 *
 * Parameters:
 *  - dry-run
 */
class SemverPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("semanticVersion", SemanticVersionConfiguration::class.java)

        val runtimeModule = RuntimeModule(project)
        val behaviorModule = BehaviorModule(runtimeModule)

        TaskGenerator(runtimeModule, behaviorModule).registerTasks()

        val sortedVersions = runtimeModule.procedures().sortedVersions()
        project.version = behaviorModule.currentVersion().determineCurrentVersion(sortedVersions)
    }
}
