import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.noarg.gradle.NoArgExtension

plugins {
    id("org.jetbrains.kotlin.plugin.noarg") version "1.6.21"
    kotlin("jvm") version "1.6.21"
    `maven-publish`
}

group = "moe.kurenai.tdlight"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

object Versions {
    const val jackson = "2.13.1"
    const val log4j = "2.17.1"
    const val disruptor = "3.4.4"
    const val ktor = "2.1.3"
}

dependencies {
    api("org.jetbrains.kotlin", "kotlin-reflect")
    api("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
    api("org.jetbrains.kotlinx", "atomicfu", "0.18.3")

    api("com.fasterxml.jackson.core:jackson-core:${Versions.jackson}")
    api("com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}")
    api("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jackson}")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}")

    api("io.ktor:ktor-client-core:${Versions.ktor}")
    api("io.ktor:ktor-client-okhttp:${Versions.ktor}")

    //logging
    val log4j = "2.20.0"
    testImplementation("org.apache.logging.log4j:log4j-core:$log4j")
    testImplementation("org.apache.logging.log4j:log4j-api:$log4j")
    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4j")
    testImplementation("com.lmax:disruptor:3.4.4")

    testImplementation(kotlin("test"))
}

configure<NoArgExtension> {
    annotation("moe.kurenai.tdlight.annotation.NoArg")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/KurenaiRyu/tdlight-sdk")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
                }
            }
        }

//        create<MavenPublication>("maven") {
//            // groupId = project.group
//            // artifactId = project.name
//            // version = project.version
//            from(components["java"])
//        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}