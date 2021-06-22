package com.evanisnor.gradle.semver.support

import java.io.BufferedReader


fun ProcessBuilder.run(command: String) =
    command(command.split(" "))
        .start()
        .inputStream
        .bufferedReader()
        .use(BufferedReader::readLines)

fun ProcessBuilder.runForExitCode(command: String) =
    command(command.split(" "))
        .start()
        .waitFor()