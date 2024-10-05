package io.github.slimefunguguproject.bump.utils

import io.github.slimefunguguproject.bump.Bump
import io.github.thebusybiscuit.slimefun4.libraries.dough.versions.MinecraftVersion
import io.github.thebusybiscuit.slimefun4.libraries.dough.versions.SemanticVersion
import java.util.logging.Level

private val mcVersion = MinecraftVersion.get()

/**
 * Check whether all the conditions are met.
 */
fun checkConditions(conditions: List<String>): Boolean {
    if (conditions.isEmpty()) return true

    for (condition in conditions) {
        Bump.debug("processing condition: $condition")
        val parts = condition.split(" ")
        when (parts[0]) {
            "v" -> {
                if (parts.size != 3) {
                    Bump.log(Level.WARNING, "Invalid condition: $condition")
                    continue
                }
                if (checkVersionCondition(parts[1], parts[2]) == false) return false
            }

            else -> {
                Bump.log(Level.WARNING, "Unknown condition: $condition")
                continue
            }
        }
    }

    return true
}

private fun checkVersionCondition(sub: String, version: String): Boolean? {
    val target = try {
        SemanticVersion.parse(version)
    } catch (e: IllegalArgumentException) {
        Bump.log(Level.WARNING, "Invalid version: $version")
        return null
    }

    val targetVersion = MinecraftVersion(target.majorVersion, target.minorVersion, target.patchVersion)
    return when (sub) {
        ">=" -> mcVersion >= targetVersion
        "<" -> mcVersion < targetVersion
        else -> {
            Bump.log(Level.WARNING, "Invalid version condition: $sub")
            null
        }
    }
}
