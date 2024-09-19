@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.utils

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun

object JsonUtils {

    /**
     * This method parses JSON from the [JsonReader].
     *
     * @param reader The [JsonReader] to be read JSON from.
     *
     * @return The root [JsonElement]
     */
    fun parseReader(reader: JsonReader): JsonElement {
        return if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_18)) {
            JsonParser.parseReader(reader)
        } else {
            JsonParser().parse(reader)
        }
    }
}
