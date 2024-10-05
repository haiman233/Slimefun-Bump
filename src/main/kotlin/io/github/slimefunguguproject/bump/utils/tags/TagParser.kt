package io.github.slimefunguguproject.bump.utils.tags

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.stream.JsonReader
import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.exceptions.TagMisconfigurationException
import io.github.slimefunguguproject.bump.utils.JsonUtils
import io.github.slimefunguguproject.bump.utils.constant.Patterns
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.CommonPatterns
import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.function.BiConsumer

/**
 * The [TagParser] is responsible for parsing a JSON file into a [BumpTag].
 *
 * @author ybw0014
 * @see BumpTag
 */
internal class TagParser(tag: BumpTag) : Keyed {

    /**
     * Every [Tag] has a [NamespacedKey].
     * This is the [NamespacedKey] for the resulting [Tag].
     */
    private val tagKey = tag.key

    /**
     * This will parse the JSON and run the provided callback with [Sets][Set] of
     * matched [Materials][Material] and [Tags][Tag].
     *
     * @param callback A callback to run after successfully parsing the input.
     *
     * @throws TagMisconfigurationException This is thrown whenever the given input is malformed or no adequate
     * [Material] or [Tag] could be found
     */
    fun parse(callback: BiConsumer<Set<Material>, Set<Tag<Material>>>) {
        val path = "/tags/" + key.key + ".json"
        val materials: MutableSet<Material> = mutableSetOf()
        val tags: MutableSet<Tag<Material>> = mutableSetOf()

        try {
            val stream = Bump::class.java.getResourceAsStream(path) ?: error("Tag file not found within jar: $path")
            val reader = JsonReader(InputStreamReader(stream, StandardCharsets.UTF_8))
            val root: JsonObject = JsonUtils.parseReader(reader).asJsonObject
            val child = root["values"]

            if (child !is JsonArray) {
                error("No values array specified")
            }

            val values = child.asJsonArray

            for (element in values) {
                if (element is JsonPrimitive && element.isString) {
                    parseString(element.asString, materials, tags)
                } else {
                    error("Unexpected value format: " + element.javaClass.simpleName + " - " + element)
                }
            }

            callback.accept(materials, tags)
        } catch (ex: Exception) {
            throw TagMisconfigurationException(key, ex)
        }
    }

    private fun parseString(value: String, materials: MutableSet<Material>, tags: MutableSet<Tag<Material>>) {
        if (Patterns.MINECRAFT_NAMESPACEDKEY.matcher(value).matches()) {
            val material = Material.matchMaterial(value)

            if (material != null) {
                materials.add(material)
            }
        } else if (Patterns.BUMP_TAG.matcher(value).matches()) {
            val keyValue = CommonPatterns.COLON.split(value)[1].uppercase()
            val tag = BumpTag.getTag(keyValue)

            if (tag != null) {
                tags.add(tag)
            } else {
                error("Bump tag not exist: $keyValue")
            }
        }
    }

    override fun getKey() = tagKey
}
