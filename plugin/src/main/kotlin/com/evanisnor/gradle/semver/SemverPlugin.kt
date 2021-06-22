package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.support.RuntimeModule
import com.evanisnor.gradle.semver.taskrunnables.*
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

/**
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
        val runtimeModule = RuntimeModule(project)
        project.tasks.register("versions", ListVersionsTask::class.java, runtimeModule)
        project.tasks.register("latestVersion", LatestVersionTask::class.java, runtimeModule)
        project.tasks.register("currentVersion", CurrentVersionTask::class.java, runtimeModule)
        project.tasks.register("nextPatch", NextPatchTask::class.java, runtimeModule)
        project.tasks.register("nextMinor", NextMinorTask::class.java, runtimeModule)
        project.tasks.register("nextMajor", NextMajorTask::class.java, runtimeModule)
        project.tasks.register("nextCandidate", NextCandidateTask::class.java, runtimeModule)
        project.tasks.register("releaseNextPatch", ReleaseNextPatchTask::class.java, runtimeModule)
        project.tasks.register("releaseNextPatchCandidate", ReleaseNextPatchCandidateTask::class.java, runtimeModule)
        project.tasks.register("releaseNextMinor", ReleaseNextMinorTask::class.java, runtimeModule)
        project.tasks.register("releaseNextMinorCandidate", ReleaseNextMinorCandidateTask::class.java, runtimeModule)
        project.tasks.register("releaseNextMajor", ReleaseNextMajorTask::class.java, runtimeModule)
        project.tasks.register("releaseNextMajorCandidate", ReleaseNextMajorCandidateTask::class.java, runtimeModule)
        project.tasks.register("releaseNextCandidate", ReleaseNextCandidateTask::class.java, runtimeModule)
    }
}

/**
 * Prints the current semantic version for the active commit
 */
open class CurrentVersionTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun currentVersion() {
        project.extensions.findByType(ConfigExtension::class.java)
        CurrentVersion(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class LatestVersionTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun latestVersion() {
        LatestVersion(
            procedures = runtimeModule.procedures()
        ).run()
    }
}


open class ListVersionsTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun versions() {
        ListVersions(
            procedures = runtimeModule.procedures()
        ).run()
    }

}

open class NextCandidateTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun nextCandidate() {
        NextCandidate(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class NextMajorTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun nextMajor() {
        NextMajor(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class NextMinorTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun nextMinor() {
        NextMinor(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class NextPatchTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun nextPatch() {
        NextPatch(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class ReleaseNextCandidateTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun releaseCandidate() {
        ReleaseNextCandidate(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class ReleaseNextMajorCandidateTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun releaseMajor() {
        ReleaseNextMajorCandidate(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class ReleaseNextMajorTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun releaseMajor() {
        ReleaseNextMajor(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class ReleaseNextMinorCandidateTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun releaseMinor() {
        ReleaseNextMinorCandidate(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class ReleaseNextMinorTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun releaseMinor() {
        ReleaseNextMinor(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class ReleaseNextPatchCandidateTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun releasePatch() {
        ReleaseNextPatchCandidate(
            procedures = runtimeModule.procedures()
        ).run()
    }
}

open class ReleaseNextPatchTask @Inject constructor(
    private val runtimeModule: RuntimeModule
) : DefaultTask() {

    @TaskAction
    fun releasePatch() {
        ReleaseNextPatch(
            procedures = runtimeModule.procedures()
        ).run()
    }
}