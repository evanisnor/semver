package com.evanisnor.gradle.semver.support

import java.io.BufferedReader

/**
 * Run a shell command and return the output as a list of strings
 */
fun ProcessBuilder.run(command: String) =
    command(command.split(" "))
        .start()
        .inputStream
        .bufferedReader()
        .use(BufferedReader::readLines)

/**
 * Run a shell command and return the exit code
 */
fun ProcessBuilder.runForExitCode(command: String) =
    command(command.split(" "))
        .start()
        .waitFor()