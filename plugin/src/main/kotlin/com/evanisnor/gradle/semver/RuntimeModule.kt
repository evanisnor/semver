package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.MetadataBuilder
import com.evanisnor.gradle.semver.procedures.Procedures
import com.evanisnor.gradle.semver.procedures.SemanticVersionParser
import com.evanisnor.gradle.semver.support.DependencyModule
import com.evanisnor.gradle.semver.system.Git
import org.gradle.api.Project

/**
 * Dependency injection module for runtime components.
 */
class RuntimeModule(
    private val project: Project
) : DependencyModule {

    fun processBuilder() = singleton(ProcessBuilder())

    fun git(): Git = singleton(Git(processBuilder()))

    fun configuration(): SemanticVersionConfiguration =
        singleton(project.extensions.getByType(SemanticVersionConfiguration::class.java))

    fun project() = singleton(project)

    fun parser() = singleton(SemanticVersionParser(configuration()))

    fun procedures(): Procedures = singleton(Procedures(git(), project(), parser(), configuration()))

    fun metadataBuilder() = singleton(MetadataBuilder(procedures(), configuration()))
}
