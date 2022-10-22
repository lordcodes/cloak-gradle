plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.register("clFormat") {
    description = "Format all Kotlin code."
    group = "cloak"

    dependsOn(gradle.includedBuild("cloak-gradle-plugin").task(":plugin:formatKotlin"))
}

tasks.register("clChecks") {
    description = "Runs all the tests/verification tasks on both top level and included build."
    group = "cloak"

    dependsOn(gradle.includedBuild("cloak-gradle-plugin").task(":plugin:check"))
    dependsOn(gradle.includedBuild("cloak-gradle-plugin").task(":plugin:validatePlugins"))
}
