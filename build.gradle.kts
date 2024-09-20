import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.20"
    id("com.gradleup.shadow") version "8.3.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("org.sonarqube") version "5.0.0.4638"
}

group = "io.github.slimefunguguproject"
version = "UNOFFICIAL"
description = "Bump"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.alessiodp.com/releases/")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("reflect"))
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    compileOnly("com.github.Slimefun:Slimefun4:e02a0f61d1")
    implementation("net.guizhanss:GuizhanLib-api:1.8.1")
    implementation("dev.sefiraat:SefiLib:0.2.6")
    implementation("org.bstats:bstats-bukkit:3.0.3")
    implementation("net.byteflux:libby-bukkit:1.3.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.compileKotlin {
    compilerOptions {
        javaParameters = true
        jvmTarget = JvmTarget.JVM_21
    }
}

tasks.shadowJar {
    fun doRelocate(from: String) {
        val last = from.split(".").last()
        relocate(from, "io.github.slimefunguguproject.bump.libs.$last")
    }
    doRelocate("net.guizhanss.guizhanlib")
    doRelocate("dev.sefiraat.sefilib")
    doRelocate("org.bstats")
    doRelocate("net.byteflux.libby")
    minimize()
    archiveClassifier = ""
}

bukkit {
    main = "io.github.slimefunguguproject.bump.Bump"
    apiVersion = "1.18"
    authors = listOf("bxx2004", "LobbyTech-MC", "zimzaza4", "haiman233", "ybw0014")
    depend = listOf("Slimefun")
    softDepend = listOf("GuizhanLibPlugin", "SlimefunTranslation")
}

sonar {
    properties {
        property("sonar.projectKey", "SlimefunGuguProject_Bump")
        property("sonar.organization", "slimefunguguproject")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
