import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "io.github.kurenairyu"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

object Versions {
    const val jackson = "2.13.1"
    const val log4j = "2.17.0"
    const val slf4jApi = "1.7.32"
}

dependencies {
    api("org.jetbrains.kotlin", "kotlin-reflect")
    api("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")

    api("com.fasterxml.jackson.core:jackson-core:${Versions.jackson}")
    api("com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")

    implementation("org.apache.logging.log4j:log4j-core:${Versions.log4j}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${Versions.log4j}")
    implementation("org.apache.logging.log4j:log4j-api:${Versions.log4j}")
    implementation("org.slf4j:slf4j-api:${Versions.slf4jApi}")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
}