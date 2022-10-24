plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "com.lordcodes.cloakgradle.buildlogic"

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlinter)
    implementation(libs.detekt.gradlePlugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        register("gradlePlugin") {
            id = "convention.gradle.plugin"
            implementationClass = "GradlePluginConventionPlugin"
        }
    }
}
