package io.github.slimefunguguproject.bump.implementation.setup

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.api.appraise.EquipmentType
import io.github.slimefunguguproject.bump.core.BumpRegistry
import io.github.slimefunguguproject.bump.utils.ConfigUtils
import io.github.slimefunguguproject.bump.utils.GeneralUtils.valueOfOrNull
import io.github.slimefunguguproject.bump.utils.constant.Keys.createKey
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.EquipmentSlot
import java.util.logging.Level

object AppraiseSetup {

    fun setup() {
        setupTypes()
        setupStars()
    }

    private fun setupTypes() {
        Bump.log(Level.INFO, "Loading appraise types...")

        val config = Bump.configService.appraiseTypes
        if (config == null) {
            Bump.log(Level.WARNING, "No appraise types found in config, some features may not work.")
            return
        }

        for (type in config.getKeys(false)) {
            Bump.log(Level.INFO, "Loading appraise type $type...")
            if (!config.getBoolean("$type.enabled")) {
                Bump.log(Level.INFO, "Appraise type $type is disabled.")
                continue
            }

            try {
                // raw values
                val name = config.getString("$type.name") ?: error("Missing name")
                val description = config.getStringList("$type.description")
                val permission = config.getString("$type.permission")
                val equipmentTypeStr = config.getString("$type.equipment-type", "ANY")!!
                val checkMaterial = config.getBoolean("$type.check-material")
                val validMaterials = config.getStringList("$type.materials")
                val validSlimefunItemIds = config.getStringList("$type.slimefun-items")
                val validEquipmentSlots = config.getStringList("$type.equipment-slots")
                val attributesSection =
                    config.getConfigurationSection("$type.attributes") ?: error("Missing attributes")
                val attributes = attributesSection.getKeys(false)

                // parsed values
                val equipmentType = valueOfOrNull<EquipmentType>(equipmentTypeStr) ?: error("Invalid equipment type")
                val equipmentSlots: Set<EquipmentSlot> = ConfigUtils.parseEquipmentSlots(validEquipmentSlots)
                val materials: Set<Material> = ConfigUtils.parseMaterials(validMaterials)

                val appraiseTypeBuilder = AppraiseType.Builder(type.createKey())
                    .name(ChatColor.YELLOW.toString() + name)
                    .permission(permission)
                    .description(description.map { ChatColor.GRAY.toString() + it })
                    .equipmentType(equipmentType)
                    .validEquipmentSlots(equipmentSlots)
                    .checkMaterial(checkMaterial)
                    .validMaterials(materials)
                    .validSlimefunItemIds(validSlimefunItemIds)

                for (attr in attributes) {
                    val attribute = Attribute.valueOf(attr)
                    val min = attributesSection.getDouble("$attr.min")
                    val max = attributesSection.getDouble("$attr.max")
                    val weightStr = attributesSection.getString("$attr.weight", "-1.0")!!
                    val weight = try {
                        weightStr.toDouble()
                    } catch (ex: NumberFormatException) {
                        -1.0
                    }
                    appraiseTypeBuilder.attribute(attribute, min, max, weight)
                }

                val appraiseType = appraiseTypeBuilder.build(Bump.instance)
                Bump.log(Level.INFO, "Loaded appraise type \"$type\"")
                Bump.debug(appraiseType.toString())
            } catch (ex: Exception) {
                Bump.log(Level.SEVERE, ex, "Failed to load appraise type \"$type\"")
            }
        }
    }

    private fun setupStars() {
        val starThreshold = BumpRegistry.starThresholds
        val starMap = Bump.configService.appraiseStars
        if (starMap.isEmpty()) {
            Bump.log(Level.INFO, "Invalid appraise stars in config. Using default values.")
            setDefaultStarThreshold(starThreshold)
            return
        }

        try {
            for ((key, value) in starMap.entries) {
                require(!(key < 0 || key > 100))
                require(!(value < 0 || value > 127))
            }
        } catch (ex: IllegalArgumentException) {
            Bump.log(Level.INFO, "Invalid appraise stars in config. Using default values.")
            setDefaultStarThreshold(starThreshold)
        }
        starThreshold.clear()
        starThreshold.putAll(starMap)
    }

    private fun setDefaultStarThreshold(starThreshold: MutableMap<Byte, Byte>) {
        starThreshold.clear()
        starThreshold[100.toByte()] = 20.toByte()
        starThreshold[98.toByte()] = 10.toByte()
        starThreshold[96.toByte()] = 9.toByte()
        starThreshold[92.toByte()] = 8.toByte()
        starThreshold[88.toByte()] = 7.toByte()
        starThreshold[82.toByte()] = 6.toByte()
        starThreshold[74.toByte()] = 5.toByte()
        starThreshold[64.toByte()] = 4.toByte()
        starThreshold[48.toByte()] = 3.toByte()
        starThreshold[30.toByte()] = 2.toByte()
        starThreshold[10.toByte()] = 1.toByte()
    }
}
