package com.evanisnor.gradle.semver.support

import org.gradle.api.Project


fun Project.isDryRun(): Boolean = properties.contains("dry-run")
