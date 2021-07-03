package com.evanisnor.gradle.semver.behavior

import com.evanisnor.gradle.semver.RuntimeModule
import com.evanisnor.gradle.semver.support.DependencyModule

/**
 * Dependency module for task behaviors
 */
class BehaviorModule(
    private val runtimeModule: RuntimeModule
) : DependencyModule {

    fun currentVersion() = singleton(CurrentVersion(runtimeModule.procedures(), runtimeModule.configuration()))

    fun latestVersion() = LatestVersion(runtimeModule.procedures())

    fun listVersions() = ListVersions(runtimeModule.procedures())

    fun releaseNextMajor() = ReleaseNextMajor(runtimeModule.procedures(), runtimeModule.metadataBuilder())

    fun releaseNextMinor() = ReleaseNextMinor(runtimeModule.procedures(), runtimeModule.metadataBuilder())

    fun releaseNextPatch() = ReleaseNextPatch(runtimeModule.procedures(), runtimeModule.metadataBuilder())

    fun releaseNextPreRelease(preReleaseIdentifier: String) = ReleaseNextPreRelease(
        runtimeModule.procedures(),
        runtimeModule.configuration(),
        runtimeModule.metadataBuilder(),
        preReleaseIdentifier
    )

}