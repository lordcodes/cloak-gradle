package com.lordcodes.cloakgradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import java.io.File
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
abstract class CloakExtension @Inject constructor(project: Project) {
    private val objects = project.objects

    val encryptionKeyFile: RegularFileProperty = objects.fileProperty().fileValue(
        File(project.rootProject.rootDir, "config/cloak/.encryption-key.json")
    )
}
