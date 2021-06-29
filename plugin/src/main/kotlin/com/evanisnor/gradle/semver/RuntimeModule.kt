package com.evanisnor.gradle.semver

import com.evanisnor.gradle.semver.model.SemanticVersionConfiguration
import com.evanisnor.gradle.semver.procedures.MetadataBuilder
import com.evanisnor.gradle.semver.procedures.SemanticVersionParser
import com.evanisnor.gradle.semver.system.Git
import com.evanisnor.gradle.semver.procedures.Procedures
import org.gradle.api.Project

class RuntimeModule(
    private val project: Project
) {

    fun processBuilder() = singleton(ProcessBuilder())

    fun git(): Git = singleton(Git(processBuilder()))

    fun configuration(): SemanticVersionConfiguration =
        singleton(project.extensions.getByType(SemanticVersionConfiguration::class.java))

    fun project() = singleton(project)

    fun parser() = singleton(SemanticVersionParser(configuration()))

    fun procedures(): Procedures = singleton(Procedures(git(), project(), parser(), configuration()))

    fun metadataBuilder() = singleton(MetadataBuilder(procedures(), configuration()))


    // region Singleton Control

    private val instances: MutableMap<Any, Any> = mutableMapOf()

    fun <T : Any> singleton(item: T): T {
        if (instances.containsKey(item::class)) {
            return instances[item::class] as T
        }

        instances[item::class] = item
        return item
    }

    // endregion
}
