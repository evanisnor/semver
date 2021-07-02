package com.evanisnor.gradle.semver.util

import com.evanisnor.gradle.semver.procedures.SemanticVersionParser
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.net.URL

fun String.toSemanticVersion() = SemanticVersionParser().parseSemVer(this)

fun URL.toFile(): File = File(file)
fun URL.copyInto(directory: File) = toFile().copyInto(directory)
fun File.copyInto(directory: File) = copyTo(File("${directory.absolutePath}/$name"))
fun File.rename(name: String) = renameTo(File("$parent/$name"))

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