package com.lordcodes.cloakgradle.plugin.task

import com.lordcodes.cloakgradle.plugin.encryption.EncryptionEngine
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class DecryptSecretCloakTask : DefaultTask() {
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
    @get:Option(option = "secret", description = "The encrypted secret to encrypt")
    abstract val encryptedSecret: Property<String>

    @TaskAction
    fun decryptSecret() {
        val decryptedValue = encryptionEngine.decrypt(encryptedSecret.get(), keyFile.get().asFile) ?: return
        logger.lifecycle("Decrypted secret:\n$decryptedValue")
    }
}
