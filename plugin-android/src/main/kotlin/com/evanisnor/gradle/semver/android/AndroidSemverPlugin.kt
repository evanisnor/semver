package com.evanisnor.gradle.semver.android

import com.android.build.api.dsl.ApplicationExtension
import com.evanisnor.gradle.semver.SemverPlugin
import com.evanisnor.gradle.semver.model.SemanticVersion
import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Semantic Versioning plugin for Android Projects
 *
 * Automatically sets your Android Application's version name and version code based on the tags present in
 * your git repository. Default: 0.1.0-SNAPSHOT
 *
 * See [SemverPlugin] for configuration and tasks.
 */
class AndroidSemverPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply(SemverPlugin::class.java)

        if (project.version is SemanticVersion) {
            val currentVersion = project.version as SemanticVersion

            val configuration = project.extensions.findByType(SemanticVersionConfiguration::class.java)
                ?: object : SemanticVersionConfiguration() {}

            project.extensions.findByType(ApplicationExtension::class.java)?.let {
                it.defaultConfig.versionName = currentVersion.toString()
                it.defaultConfig.versionCode = currentVersion.toVersionCode(configuration.preReleaseIdentifiers)
            }
        }
    }

}