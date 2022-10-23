package com.lordcodes.cloakgradle.plugin

import com.lordcodes.cloakgradle.plugin.task.CreateKeyCloakTask
import com.lordcodes.cloakgradle.plugin.task.DecryptSecretCloakTask
import com.lordcodes.cloakgradle.plugin.task.EncryptSecretCloakTask
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File

class CloakPluginTest {
    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.lordcodes.cloakgradle")

        assert(project.tasks.getByName("cloakKey") is CreateKeyCloakTask)
        assert(project.tasks.getByName("cloakEncrypt") is EncryptSecretCloakTask)
        assert(project.tasks.getByName("cloakDecrypt") is DecryptSecretCloakTask)
    }

    @Test
    fun `extension cloak is created correctly`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.lordcodes.cloakgradle")

        assertNotNull(project.extensions.getByName("cloak"))
    }

    @Test
    fun `parameters are passed correctly from extension to task`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.lordcodes.cloakgradle")
        val aFile = File(project.projectDir, ".tmp")
        (project.extensions.getByName("cloak") as CloakExtension).apply {
            encryptionKeyFile.set(aFile)
        }

        val createKeyTask = project.tasks.getByName("cloakKey") as CreateKeyCloakTask
        assertEquals(aFile, createKeyTask.keyFile.get().asFile)

        val encryptTask = project.tasks.getByName("cloakEncrypt") as EncryptSecretCloakTask
        assertEquals(aFile, encryptTask.keyFile.get().asFile)

        val decryptTask = project.tasks.getByName("cloakDecrypt") as DecryptSecretCloakTask
        assertEquals(aFile, decryptTask.keyFile.get().asFile)
    }
}
