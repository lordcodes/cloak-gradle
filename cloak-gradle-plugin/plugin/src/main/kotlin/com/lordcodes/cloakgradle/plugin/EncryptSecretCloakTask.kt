package com.lordcodes.cloakgradle.plugin

import com.google.crypto.tink.Aead
import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.JsonKeysetReader
import com.google.crypto.tink.aead.AeadConfig
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.IOException
import java.security.GeneralSecurityException
import java.util.*

abstract class EncryptSecretCloakTask : DefaultTask() {
    init {
        description = "Encrypt a secret using the stored encryption key."
        group = "secrets"
    }

    @get:InputFile
    abstract val keyFile: RegularFileProperty

    @get:Input
    @get:Option(option = "secret", description = "The plaintext secret to encrypt")
    abstract val plaintextSecret: Property<String>

    @TaskAction
    fun encryptSecret() {
        logger.lifecycle("\uD83D\uDD20 Encrypting ${plaintextSecret.get()}")

        val keySetFile = keyFile.get().asFile
        if (!keySetFile.exists()) {
            logger.error(
                "Key set file not found at ${keySetFile.absolutePath}. " +
                    "You need to add it or generate a new one with the task `cloakKey`"
            )
            return
        }

        logger.debug("Setting up Tink")
        AeadConfig.register()

        val keySetHandle = try {
            CleartextKeysetHandle.read(JsonKeysetReader.withFile(keyFile.get().asFile))
        } catch (error: GeneralSecurityException) {
            logger.error("Failed to read keyset", error)
            return
        } catch (error: IOException) {
            logger.error("Failed to read keyset", error)
            return
        }
        val aead = try {
            keySetHandle.getPrimitive(Aead::class.java)
        } catch (error: GeneralSecurityException) {
            logger.error("Failed to setup Tink primitive ready for encryption", error)
            return
        }
        val encryptedBytes = try {
            aead.encrypt(plaintextSecret.get().encodeToByteArray(), null)
        } catch (error: GeneralSecurityException) {
            logger.error("Failed to encrypt plaintext secret ", error)
            return
        }
        val encoded = Base64.getEncoder().encodeToString(encryptedBytes)
        logger.lifecycle("Encrypted Base64 encoded secret:\n$encoded")
    }
}
