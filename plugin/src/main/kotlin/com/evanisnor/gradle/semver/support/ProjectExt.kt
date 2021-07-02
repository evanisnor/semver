package com.evanisnor.gradle.semver.support

import org.gradle.api.Project

/**
 * Project extension for accessing the 'dry-run' property
 */
fun Project.isDryRun(): Boolean = properties.contains("dry-run")
