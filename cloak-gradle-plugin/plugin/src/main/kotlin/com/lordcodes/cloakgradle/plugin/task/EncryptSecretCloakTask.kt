package com.lordcodes.cloakgradle.plugin.task

import com.lordcodes.cloakgradle.plugin.encryption.EncryptionEngine
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * Gradle task to encrypt a provided secret using the encryption key.
 */
abstract class EncryptSecretCloakTask : DefaultTask() {
    init {
        description = "Encrypt a secret using the stored encryption key."
        group = "secrets"
    }

    private val encryptionEngine: EncryptionEngine by lazy {
        EncryptionEngine(logger)
    }

    /**
     * The file containing the encryption key.
     */
    @get:InputFile
    abstract val keyFile: RegularFileProperty

    /**
     * The plaintext secret value ready to be encrypted, which can be set in code or provided as a Gradle CLI argument.
     */
    @get:Input
    @get:Option(option = "secret", description = "The plaintext secret to encrypt")
    abstract val plaintextSecret: Property<String>

    /**
     * The task action to encrypt a secret, encode it into Base64 and then print it as output.
     */
    @TaskAction
    fun encryptSecret() {
        val encoded = encryptionEngine.encrypt(plaintextSecret.get(), keyFile.get().asFile) ?: return
        logger.lifecycle("Encrypted Base64 encoded secret:\n$encoded")
    }
}
