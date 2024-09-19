package io.github.slimefunguguproject.bump.api.appraise

import io.github.slimefunguguproject.bump.core.BumpRegistry
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import net.guizhanss.guizhanlib.utils.RandomUtil
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

data class AppraiseType internal constructor(
    val key: NamespacedKey,
    val name: String,
    val description: List<String>,
    val permission: String?,
    val attributes: Set<AppraiseAttribute>,
    val checkMaterial: Boolean,
    val equipmentType: EquipmentType,
    val validMaterials: Set<Material>,
    val validEquipmentSlots: Set<EquipmentSlot>,
    val validSlimefunItemIds: Set<String>,
    val addon: SlimefunAddon,
) {

    fun hasPermission(p: Player) = permission == null || p.hasPermission(permission)

    /**
     * This method checks if specified [ItemStack] fit this [AppraiseType].
     *
     * @param itemStack The [ItemStack] to be checked.
     *
     * @return If the [ItemStack] fit this [AppraiseType].
     */
    fun isValidItem(itemStack: ItemStack): Boolean {
        // Material check
        if (checkMaterial && !validMaterials.contains(itemStack.type)) {
            return false
        }

        // Equipment type check
        val sfItem = SlimefunItem.getOptionalByItem(itemStack)
        return when (equipmentType) {
            EquipmentType.VANILLA -> sfItem.isEmpty
            EquipmentType.SLIMEFUN -> sfItem.isPresent && isAcceptableSlimefunItem(sfItem.get())
            EquipmentType.ANY -> sfItem.isEmpty || isAcceptableSlimefunItem(sfItem.get())
        }
    }

    fun isAcceptableSlimefunItem(sfItem: SlimefunItem): Boolean {
        val id = sfItem.id
        return validSlimefunItemIds.isEmpty() || validSlimefunItemIds.contains(id)
    }

    /**
     * This method will generate random values as [AppraiseResult].
     *
     * @return The [AppraiseResult].
     */
    @Nonnull
    fun appraise(): AppraiseResult {
        val builder = AppraiseResult.Builder(this)

        for (attr in attributes) {
            val value = RandomUtil.randomDouble(attr.min, attr.max)
            builder.attribute(attr, value)
        }

        return builder.build()
    }

    class Builder(
        private val key: NamespacedKey,
    ) {

        private var name: String = key.key
        private var description: List<String> = emptyList()
        private var permission: String? = null
        private var attributes: MutableSet<AppraiseAttribute> = mutableSetOf()
        private var checkMaterial: Boolean = false
        private var equipmentType: EquipmentType = EquipmentType.ANY
        private var validMaterials: MutableSet<Material> = mutableSetOf()
        private var validEquipmentSlots: MutableSet<EquipmentSlot> = mutableSetOf()
        private var validSlimefunItemIds: MutableSet<String> = mutableSetOf()

        private var usedPercent = 0.0

        fun name(name: String) = apply { this.name = name }
        fun description(description: List<String>) = apply { this.description = description }
        fun permission(permission: String?) = apply { this.permission = permission }
        fun attribute(
            attribute: Attribute,
            min: Double,
            max: Double,
            weight: Double = AppraiseAttribute.UNSET_WEIGHT
        ) = apply {
            attributes.add(AppraiseAttribute(attribute, min, max, weight))
            if (weight != AppraiseAttribute.UNSET_WEIGHT) {
                usedPercent += weight
            }
        }

        fun checkMaterial(checkMaterial: Boolean) = apply { this.checkMaterial = checkMaterial }
        fun equipmentType(equipmentType: EquipmentType) = apply { this.equipmentType = equipmentType }
        fun validMaterials(validMaterials: Collection<Material>) = apply {
            for (material in validMaterials) {
                if (material == Material.AIR) error("Material cannot be AIR.")
            }
            this.validMaterials.addAll(validMaterials)
        }

        fun validEquipmentSlots(validEquipmentSlots: Collection<EquipmentSlot>) =
            apply { this.validEquipmentSlots.addAll(validEquipmentSlots) }

        fun validSlimefunItemIds(validSlimefunItemIds: Collection<String>) =
            apply { this.validSlimefunItemIds.addAll(validSlimefunItemIds) }

        fun build(addon: SlimefunAddon): AppraiseType {
            if (BumpRegistry.appraiseTypeKeys.containsKey(key)) {
                error("AppraiseType with key \"$key\" already exists.")
            }

            if (usedPercent > 100.0) {
                error("The sum of weights cannot be greater than 100.")
            }

            val noWeightAttributes = attributes.filter { it.weight == AppraiseAttribute.UNSET_WEIGHT }
            val avgPercent = (100.0 - usedPercent) / noWeightAttributes.size
            for (attribute in noWeightAttributes) {
                attribute.weight = avgPercent
                usedPercent += avgPercent
            }

            if (usedPercent != 100.0) {
                error("The sum of weights must be 100.")
            }

            val appraiseType = AppraiseType(
                key,
                name,
                description,
                permission,
                attributes,
                checkMaterial,
                equipmentType,
                validMaterials,
                validEquipmentSlots,
                validSlimefunItemIds,
                addon
            )

            BumpRegistry.appraiseTypeKeys[key] = appraiseType
            BumpRegistry.appraiseTypes.add(appraiseType)

            return appraiseType
        }
    }

    companion object {

        fun getByKey(key: NamespacedKey) = BumpRegistry.appraiseTypeKeys[key]
    }
}

enum class EquipmentType {
    VANILLA,
    SLIMEFUN,
    ANY
}
