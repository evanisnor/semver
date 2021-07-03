package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.RuntimeModule
import com.evanisnor.gradle.semver.support.DependencyModule

/**
 * Dependency module for task behaviors
 */
class BehaviorModule(
    private val runtimeModule: RuntimeModule
) : DependencyModule {

    fun currentVersion() = CurrentVersion(runtimeModule.procedures(), runtimeModule.configuration())

    fun latestVersion() = LatestVersion(runtimeModule.procedures())

    fun listVersions() = ListVersions(runtimeModule.procedures())

    fun releaseNextMajor() = ReleaseMajor(runtimeModule.procedures(), runtimeModule.metadataBuilder())

    fun releaseNextMinor() = ReleaseMinor(runtimeModule.procedures(), runtimeModule.metadataBuilder())

    fun releaseNextPatch() = ReleasePatch(runtimeModule.procedures(), runtimeModule.metadataBuilder())

    fun releaseNextPreRelease(preReleaseIdentifier: String) = ReleasePreRelease(
        runtimeModule.procedures(),
        runtimeModule.configuration(),
        runtimeModule.metadataBuilder(),
        preReleaseIdentifier
    )

}