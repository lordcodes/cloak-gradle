@file:Suppress("UnstableApiUsage")

import com.getbusy.android.buildlogic.convention.applyLinting
import com.getbusy.android.buildlogic.convention.configureKotlinModule
import com.getbusy.android.buildlogic.convention.moduleReplacements
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class GradlePluginConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyConventions()
    }
}

private fun Project.applyConventions() {
    pluginManager.apply("kotlin.jvm")
    
    configureKotlinModule()
    applyLinting()
}