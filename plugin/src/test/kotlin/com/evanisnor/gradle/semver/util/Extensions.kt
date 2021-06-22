package com.evanisnor.gradle.semver

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.net.URL

fun URL.toFile(): File = File(file)
fun File.copyInto(directory: File) = copyTo(File("${directory.absolutePath}/$name"))

fun File.runGradleCommand(command: String): BuildResult = GradleRunner.create()
    .withProjectDir(this)
    .withArguments(command)
    .withPluginClasspath()
    .build()


fun captureStandardOutput(command: () -> Unit): String = ByteArrayOutputStream().use { outputStream ->
    System.setOut(PrintStream(outputStream))
    command()
    outputStream.flush()
    String(outputStream.toByteArray())
}