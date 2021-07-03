package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.behavior.BehaviorModule
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class PrintConfigurationTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) :
    DefaultTask() {

    @TaskAction
    fun printConfiguration() {
        println(runtimeModule.configuration())
    }
}

open class CurrentVersionTask @Inject constructor(
    private val behaviorModule: BehaviorModule
) : DefaultTask() {

    @TaskAction
    fun currentVersion() {
        behaviorModule.currentVersion().run()
    }
}

open class LatestVersionTask @Inject constructor(
    private val behaviorModule: BehaviorModule
) : DefaultTask() {

    @TaskAction
    fun latestVersion() {
        behaviorModule.latestVersion().run()
    }
}

open class ListVersionsTask @Inject constructor(
    private val behaviorModule: BehaviorModule
) : DefaultTask() {

    @TaskAction
    fun versions() {
        behaviorModule.listVersions().run()
    }
}

open class ReleaseNextMajorTask @Inject constructor(
    private val behaviorModule: BehaviorModule
) : DefaultTask() {

    @TaskAction
    fun releaseMajor() {
        behaviorModule.releaseNextMajor().run()
    }
}

open class ReleaseNextMinorTask @Inject constructor(
    private val behaviorModule: BehaviorModule
) : DefaultTask() {

    @TaskAction
    fun releaseMinor() {
        behaviorModule.releaseNextMinor().run()
    }
}

open class ReleaseNextPatchTask @Inject constructor(
    private val behaviorModule: BehaviorModule
) : DefaultTask() {

    @TaskAction
    fun releasePatch() {
        behaviorModule.releaseNextPatch().run()
    }
}

open class ReleaseNextPreReleaseTask @Inject constructor(
    private val behaviorModule: BehaviorModule,
    private val preReleaseIdentifier: String
) : DefaultTask() {

    @TaskAction
    fun releasePatch() {
        behaviorModule.releaseNextPreRelease(preReleaseIdentifier).run()
    }
}