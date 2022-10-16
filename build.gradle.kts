plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    id("convention.root")
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.register("format") {
    description = "Format all Kotlin code."
    group = "cloak"

    dependsOn("formatKotlin")
    dependsOn(gradle.includedBuild("cloak-gradle-plugin").task(":plugin:formatKotlin"))
}

tasks.register("preMerge") {
    description = "Runs all the tests/verification tasks on both top level and included build."
    group = "cloak"

    dependsOn(":detekt")
    dependsOn(gradle.includedBuild("cloak-gradle-plugin").task(":plugin:check"))
    dependsOn(gradle.includedBuild("cloak-gradle-plugin").task(":plugin:validatePlugins"))
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
