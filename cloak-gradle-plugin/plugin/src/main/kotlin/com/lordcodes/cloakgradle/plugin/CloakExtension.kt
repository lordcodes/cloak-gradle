package com.lordcodes.cloakgradle.plugin

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import java.io.File
import javax.inject.Inject

/**
 * Gradle extension to configure Cloak.
 */
@Suppress("UnnecessaryAbstractClass")
abstract class CloakExtension @Inject constructor(project: Project) {
    private val objects = project.objects

    /**
     * The location of the encryption key file.
     */
    val encryptionKeyFile: RegularFileProperty = objects.fileProperty().fileValue(
        File(project.rootProject.rootDir, "config/cloak/.encryption-key.json")
    )
}
