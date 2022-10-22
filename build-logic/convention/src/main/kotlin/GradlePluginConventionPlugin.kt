@file:Suppress("UnstableApiUsage")

import com.lordcodes.cloakgradle.buildlogic.convention.applyLinting
import com.lordcodes.cloakgradle.buildlogic.convention.configureKotlinModule
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class GradlePluginConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyConventions()
    }
}

private fun Project.applyConventions() {
    pluginManager.apply("org.jetbrains.kotlin.jvm")

    configureKotlinModule()
    applyLinting()
}
