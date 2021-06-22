package com.evanisnor.gradle.semver.support

import com.evanisnor.gradle.semver.ConfigExtension
import com.evanisnor.gradle.semver.system.Git
import com.evanisnor.gradle.semver.system.Procedures
import org.gradle.api.Project

class RuntimeModule(
    private val project: Project
) {

    fun processBuilder() = ProcessBuilder()

    fun git(): Git = Git(processBuilder())

    fun procedures(): Procedures = Procedures(git())

    fun configuration() = project.extensions.getByType(ConfigExtension::class.java)

}
