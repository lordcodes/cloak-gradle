package com.lordcodes.cloakgradle.plugin

import com.lordcodes.cloakgradle.plugin.task.CreateKeyCloakTask
import com.lordcodes.cloakgradle.plugin.task.DecryptSecretCloakTask
import com.lordcodes.cloakgradle.plugin.task.EncryptSecretCloakTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class CloakPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, CloakExtension::class.java, project)

        project.tasks.register("cloakKey", CreateKeyCloakTask::class.java) {
            it.keyFile.set(extension.encryptionKeyFile)
        }
        project.tasks.register("cloakEncrypt", EncryptSecretCloakTask::class.java) {
            it.keyFile.set(extension.encryptionKeyFile)
        }
        project.tasks.register("cloakDecrypt", DecryptSecretCloakTask::class.java) {
            it.keyFile.set(extension.encryptionKeyFile)
        }
    }

    companion object {
        private const val EXTENSION_NAME = "cloak"
    }
}
