plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.pluginPublish) apply false
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
