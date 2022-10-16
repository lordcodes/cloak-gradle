package com.lordcodes.cloakgradle.buildlogic.convention

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jmailen.gradle.kotlinter.KotlinterExtension

fun Project.applyLinting() {
    detekt()
    ktLint()
}

private fun Project.detekt() {
    pluginManager.apply("io.gitlab.arturbosch.detekt")

    extensions.configure<DetektExtension> {
        parallel = true
        config = files("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
    }
}

private fun Project.ktLint() {
    pluginManager.apply("org.jmailen.kotlinter")

    extensions.configure<KotlinterExtension> {
        disabledRules = arrayOf("filename", "import-ordering")
        reporters = arrayOf("checkstyle", "html")
    }
}
