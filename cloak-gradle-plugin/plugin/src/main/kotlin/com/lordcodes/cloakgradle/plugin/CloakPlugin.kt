package com.lordcodes.cloakgradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class CloakPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, CloakExtension::class.java, project)

        project.tasks.register("cloakKey", CreateCloakKeyTask::class.java) {
            it.keyFile.set(extension.encryptionKeyFile)
        }
    }

    companion object {
        private const val EXTENSION_NAME = "cloak"
    }
}
