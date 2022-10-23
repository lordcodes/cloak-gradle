package com.lordcodes.cloakgradle.plugin.task

import com.lordcodes.cloakgradle.plugin.encryption.EncryptionEngine
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class CreateKeyCloakTask : DefaultTask() {
    init {
        description = "Create an encryption key to use within Cloak and your application"
        group = "secrets"
    }

    private val encryptionEngine: EncryptionEngine by lazy {
        EncryptionEngine(logger)
    }

    @get:OutputFile
    abstract val keyFile: RegularFileProperty

    @TaskAction
    fun createEncryptionKey() {
        encryptionEngine.createEncryptionKey(keyFile.get().asFile)
    }
}
