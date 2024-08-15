plugins {
    kotlin("jvm") version "2.0.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("org.sonarqube") version "5.0.0.4638"
}

group = "io.github.slimefunguguproject"
version = "UNOFFICIAL"
description = "Bump"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
}

dependencies {
    library(kotlin("stdlib"))
    library(kotlin("reflect"))
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.Slimefun:Slimefun4:RC-37")
    implementation("net.guizhanss:GuizhanLib-api:1.7.6")
    implementation("dev.sefiraat:SefiLib:0.2.6")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.compileKotlin {
    kotlinOptions {
        javaParameters = true
        jvmTarget = "17"
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
    minimize()
    archiveClassifier = ""
}

bukkit {
    main = "io.github.slimefunguguproject.bump.Bump"
    apiVersion = "1.16"
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
