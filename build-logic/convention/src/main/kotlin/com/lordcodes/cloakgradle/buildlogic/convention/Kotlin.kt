package com.lordcodes.cloakgradle.buildlogic.convention

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configureKotlinModule() {
    applyKotlinOptions()

    java {
        sourceCompatibility = javaVersion()
        targetCompatibility = javaVersion()
    }
}

fun Project.applyKotlinOptions() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            allWarningsAsErrors = properties["warningsAsErrors"] as? Boolean ?: false
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xjvm-default=all",
            )
            jvmTarget = javaVersion().toString()
            moduleName = project.path
        }
    }
}

private fun Project.java(configure: JavaPluginExtension.() -> Unit) {
    (this as ExtensionAware).extensions.configure("java", configure)
}

private fun Project.sourceSets(configure: SourceSetContainer.() -> Unit) {
    (this as ExtensionAware).extensions.configure("sourceSets", configure)
}
