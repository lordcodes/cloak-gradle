package com.lordcodes.cloakgradle.plugin.encryption

import com.google.crypto.tink.Aead
import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.JsonKeysetReader
import com.google.crypto.tink.JsonKeysetWriter
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import org.gradle.api.logging.Logger
import java.io.File
import java.io.IOException
import java.security.GeneralSecurityException
import java.util.Base64

class EncryptionEngine(
    private val logger: Logger
) {
    fun createEncryptionKey(file: File) {
        AeadConfig.register()

        val keySetHandle = KeysetHandle.generateNew(KeyTemplates.get("AES256_GCM"))
        CleartextKeysetHandle.write(keySetHandle, JsonKeysetWriter.withFile(file))
    }

    fun encrypt(plaintextSecret: String, keyFile: File): String? {
        logger.lifecycle("\uD83D\uDD20 Encrypting $plaintextSecret")

        logger.debug("Setting up Tink")
        AeadConfig.register()

        val aead = readPrimitiveFromFile(keyFile) ?: return null
        val encryptedBytes = try {
            logger.debug("Encrypting plaintext secret")
            aead.encrypt(plaintextSecret.encodeToByteArray(), null)
        } catch (error: GeneralSecurityException) {
            logger.error("Failed to encrypt plaintext secret ", error)
            return null
        }
        logger.debug("Encoding encrypted bytes into Base64 string")
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encryptedSecret: String, keyFile: File): String? {
        logger.lifecycle("\uD83D\uDD20 Decrypting $encryptedSecret")

        logger.debug("Setting up Tink")
        AeadConfig.register()

        val decodedBytes = try {
            logger.debug("Decoding Base64 string into bytes")
            Base64.getDecoder().decode(encryptedSecret)
        } catch (error: IllegalArgumentException) {
            logger.error("Secret is not encoded as Base64", error)
            return null
        }
        val aead = readPrimitiveFromFile(keyFile) ?: return null
        val decryptedBytes = try {
            logger.debug("Decrypting secret")
            aead.decrypt(decodedBytes, null)
        } catch (error: GeneralSecurityException) {
            logger.error("Failed to decrypt secret ", error)
            return null
        }
        return String(decryptedBytes)
    }

    private fun readPrimitiveFromFile(file: File): Aead? {
        val keysetHandle = readKeysetFromFile(file) ?: return null
        return try {
            logger.debug("Preparing the Aead primitive from key set handle for Tink")
            keysetHandle.getPrimitive(Aead::class.java)
        } catch (error: GeneralSecurityException) {
            logger.error("Failed to setup Tink primitive ready for encryption", error)
            null
        }
    }

    private fun readKeysetFromFile(file: File): KeysetHandle? = try {
        logger.debug("Reading in the key set handle from file")
        CleartextKeysetHandle.read(JsonKeysetReader.withFile(file))
    } catch (error: GeneralSecurityException) {
        logger.error("Failed to read keyset", error)
        null
    } catch (error: IOException) {
        logger.error("Failed to read keyset", error)
        null
    }
}
