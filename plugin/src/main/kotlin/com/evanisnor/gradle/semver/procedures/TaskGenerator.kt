package com.evanisnor.gradle.semver.procedures

import com.evanisnor.gradle.semver.RuntimeModule
import com.evanisnor.gradle.semver.behavior.BehaviorModule
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

class TaskGenerator(
    private val runtimeModule: RuntimeModule,
    private val behaviorModule: BehaviorModule,
    private val project: Project = runtimeModule.project()
) {

    fun registerTasks() {
        project.tasks.register("versionConfiguration", PrintConfigurationTask::class.java, runtimeModule)
        project.tasks.register("versions", ListVersionsTask::class.java, behaviorModule)
        project.tasks.register("latestVersion", LatestVersionTask::class.java, behaviorModule)
        project.tasks.register("currentVersion", CurrentVersionTask::class.java, behaviorModule)
        project.tasks.register("nextVersion", NextVersionTask::class.java, behaviorModule)
        project.tasks.register("releaseMajor", ReleaseNextMajorTask::class.java, behaviorModule)
        project.tasks.register("releaseMinor", ReleaseNextMinorTask::class.java, behaviorModule)
        project.tasks.register("releasePatch", ReleaseNextPatchTask::class.java, behaviorModule)

        runtimeModule.configuration().preReleaseIdentifiers.forEach { preReleaseIdentifier ->
            // Register a new task to print the next pre-release version
            project.tasks.register(
                "next${preReleaseIdentifier.capitalize()}",
                NextPreReleaseTask::class.java,
                runtimeModule,
                behaviorModule,
                preReleaseIdentifier
            )

            // Register a new release task to release the next pre-release version
            project.tasks.register(
                "release${preReleaseIdentifier.capitalize()}",
                ReleaseNextPreReleaseTask::class.java,
                runtimeModule,
                behaviorModule,
                preReleaseIdentifier
            )
        }
    }
}

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

open class NextVersionTask @Inject constructor(
    private val behaviorModule: BehaviorModule
) : DefaultTask() {

    @TaskAction
    fun nextVersion() {
        behaviorModule.nextVersion().run()
    }
}

open class NextPreReleaseTask @Inject constructor(
    private val behaviorModule: BehaviorModule,
    private val preReleaseIdentifier: String
) : DefaultTask() {

    @TaskAction
    fun printNextVersion() {
        behaviorModule.nextPreRelease(preReleaseIdentifier).run()
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