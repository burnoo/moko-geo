/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */
buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlinGradlePlugin)
        classpath(libs.androidGradlePlugin)
        classpath(libs.googleServicesGradlePlugin)
        classpath(libs.mokoGradlePlugin)
        classpath(libs.mobileMultiplatformGradlePlugin)
        classpath(libs.kotlinSerializationGradlePlugin)
        classpath(libs.composeJetBrainsGradlePlugin)
        classpath(libs.detektGradlePlugin)
    }
}

apply(plugin = "dev.icerock.moko.gradle.publication.nexus")
val mokoVersion = libs.versions.mokoGeoVersion.get()
allprojects {
    group = "dev.icerock.moko"
    version = mokoVersion
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}

subprojects {
    afterEvaluate {
        this.extensions.findByType<PublishingExtension>()?.apply {
            repositories {
                maven {
                    name = "burnoo"
                    url = uri("https://pkgs.dev.azure.com/burnoo/maven/_packaging/public/maven/v1")
                    credentials {
                        username = rootProject.findProperty("azureUsername")?.toString()
                        password = rootProject.findProperty("azureKey")?.toString()
                    }
                }
            }
        }
    }
}
