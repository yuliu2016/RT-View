@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.31"
}

val kProjectVersion = "0.0.1"

group = "ca.warp7.rt.view"
version = kProjectVersion

repositories {
    mavenCentral()
    jcenter()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    // Kotlin Coroutines
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.2.1")
    // DataFrame Library
    implementation(group = "de.mpicbg.scicomp", name = "krangl", version = "0.11")
    // Table Library
    implementation(group = "tech.tablesaw", name = "tablesaw-core", version = "0.33.2")
    
    implementation(group = "org.kordamp.ikonli", name = "ikonli-javafx", version = "2.4.0")
    implementation(group = "org.kordamp.ikonli", name = "ikonli-fontawesome5-pack", version = "2.4.0")
    implementation(group = "org.controlsfx", name = "controlsfx", version = "8.40.14")

    // Test Libraries
    testImplementation(kotlin("test"))
    testImplementation(group = "junit", name = "junit", version = "4.12")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            artifactId = "rt-view"
        }
    }
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            url = uri("$buildDir/maven")
        }
    }
}