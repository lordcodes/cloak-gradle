package com.lordcodes.cloakgradle.plugin.task

import com.lordcodes.cloakgradle.plugin.encryption.EncryptionEngine
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * Gradle task to create an encryption key for use with Cloak.
 */
abstract class CreateKeyCloakTask : DefaultTask() {
    init {
        description = "Create an encryption key to use with Cloak and your application"
        group = "secrets"
    }

    private val encryptionEngine: EncryptionEngine by lazy {
        EncryptionEngine(logger)
    }

    /**
     * The file that will contain the encryption key, created for use with Google Tink.
     */
    @get:OutputFile
    abstract val keyFile: RegularFileProperty

    /**
     * The task action to create an encryption key.
     */
    @TaskAction
    fun createEncryptionKey() {
        encryptionEngine.createEncryptionKey(keyFile.get().asFile)
    }
}
