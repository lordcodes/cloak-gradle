@file:Suppress("UnstableApiUsage")

import com.lordcodes.cloakgradle.buildlogic.convention.applyLinting
import com.lordcodes.cloakgradle.buildlogic.convention.configureKotlinModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class RootConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyConventions()
    }
}

private fun Project.applyConventions() {
    applyLinting()
}
