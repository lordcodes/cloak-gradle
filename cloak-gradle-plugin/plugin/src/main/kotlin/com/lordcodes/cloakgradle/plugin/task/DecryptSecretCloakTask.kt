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
 * Gradle task to decrypt a provided encrypted and encoded secret value, using the encryption key.
 */
abstract class DecryptSecretCloakTask : DefaultTask() {
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
     * The encrypted and encoded secret value, which can be set in code or provided as a Gradle CLI argument.
     */
    @get:Input
    @get:Option(option = "secret", description = "The encrypted secret to encrypt")
    abstract val encryptedSecret: Property<String>

    /**
     * The task action to decode and decrypt the secret and then print the plaintext value as output.
     */
    @TaskAction
    fun decryptSecret() {
        val decryptedValue = encryptionEngine.decrypt(encryptedSecret.get(), keyFile.get().asFile) ?: return
        logger.lifecycle("Decrypted secret:\n$decryptedValue")
    }
}
