package io.github.slimefunguguproject.bump.utils

import io.github.slimefunguguproject.bump.utils.GeneralUtils.valueOfOrNull
import io.github.slimefunguguproject.bump.utils.constant.Patterns
import io.github.slimefunguguproject.bump.utils.tags.BumpTag
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.CommonPatterns
import org.bukkit.Material
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.Plugin
import java.io.File

object ConfigUtils {

    fun saveDefaultFile(plugin: Plugin, fileName: String) {
        val file = File(plugin.dataFolder, fileName)
        if (!file.exists()) {
            plugin.saveResource(fileName, false)
        }
    }

    /**
     * This method will parse [List] of material [String] into
     * [Set] of [Material].
     * <br></br>
     * Acceptable elements: minecraft materials, bump tags.
     *
     * @param materialList The [List] of material [String] to be parsed.
     *
     * @return The parsed [Set] of [Material].
     *
     * @throws IllegalArgumentException      when the list is `null` or contains null elements.
     * @throws InvalidConfigurationException when the list contains invalid material or BumpTag.
     * @see BumpTag
     */
    fun parseMaterials(materialList: List<String>): Set<Material> {
        Validate.noNullElements(materialList)

        val materials: MutableSet<Material> = HashSet()
        for (value in materialList) {
            if (Patterns.MINECRAFT_NAMESPACEDKEY.matcher(value).matches()) {
                val material = Material.matchMaterial(value)

                materials.add(material ?: throw InvalidConfigurationException("Invalid minecraft material: $value"))
            } else if (Patterns.BUMP_TAG_CONFIG.matcher(value).matches()) {
                val keyValue = CommonPatterns.COLON.split(value)[1].uppercase()
                val tag = BumpTag.getTag(keyValue)

                materials.addAll(tag?.values ?: throw InvalidConfigurationException("Invalid BumpTag: $keyValue"))
            }
        }
        return materials
    }

    /**
     * This method will parse [List] of equipment slot [String] into
     * [Set] of [EquipmentSlot].
     *
     * @param slotList The [List] of equipment slot [String] to be parsed.
     *
     * @return The parsed [Set] of [EquipmentSlot].
     *
     * @throws IllegalArgumentException      when the list is `null` or contains null elements.
     * @throws InvalidConfigurationException when the list contains invalid EquipmentSlot.
     */
    fun parseEquipmentSlots(slotList: List<String>): Set<EquipmentSlot> {
        Validate.noNullElements(slotList)

        return slotList.map {
            valueOfOrNull<EquipmentSlot>(it.uppercase())
                ?: throw InvalidConfigurationException("Invalid EquipmentSlot value: $it")
        }.toSet()
    }
}
