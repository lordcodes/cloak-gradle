package com.lordcodes.cloakgradle.plugin

import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.JsonKeysetWriter
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class CreateCloakKeyTask : DefaultTask() {
    init {
        description = "Create an encryption key to use within Cloak and your application"
        group = "secrets"
    }

    @get:OutputFile
    abstract val keyFile: RegularFileProperty

    @TaskAction
    fun createEncryptionKey() {
        AeadConfig.register()

        val keySetHandle = KeysetHandle.generateNew(KeyTemplates.get("AES256_GCM"))
        CleartextKeysetHandle.write(keySetHandle, JsonKeysetWriter.withFile(keyFile.get().asFile))
    }
}
