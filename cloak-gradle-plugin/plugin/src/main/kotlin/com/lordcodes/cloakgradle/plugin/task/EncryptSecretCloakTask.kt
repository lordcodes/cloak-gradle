package com.lordcodes.cloakgradle.plugin.task

import com.lordcodes.cloakgradle.plugin.encryption.EncryptionEngine
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class EncryptSecretCloakTask : DefaultTask() {
    init {
        description = "Encrypt a secret using the stored encryption key."
        group = "secrets"
    }

    private val encryptionEngine: EncryptionEngine by lazy {
        EncryptionEngine(logger)
    }

    @get:InputFile
    abstract val keyFile: RegularFileProperty

    @get:Input
    @get:Option(option = "secret", description = "The plaintext secret to encrypt")
    abstract val plaintextSecret: Property<String>

    @TaskAction
    fun encryptSecret() {
        val encoded = encryptionEngine.encrypt(plaintextSecret.get(), keyFile.get().asFile) ?: return
        logger.lifecycle("Encrypted Base64 encoded secret:\n$encoded")
    }
}
