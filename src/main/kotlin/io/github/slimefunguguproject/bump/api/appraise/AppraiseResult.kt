@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.api.appraise

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.BumpRegistry
import io.github.slimefunguguproject.bump.utils.GeneralUtils
import io.github.slimefunguguproject.bump.utils.constant.Keys
import io.github.slimefunguguproject.bump.utils.items.AppraiseUtils.getOperation
import io.github.slimefunguguproject.bump.utils.items.ValidateUtils.noAirItem
import io.github.slimefunguguproject.bump.utils.tags.BumpTag
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemStack
import java.util.UUID

class AppraiseResult private constructor(
    val type: AppraiseType,
    val result: Map<AppraiseAttribute, Double>,
) {

    var totalPercent = 0.0
        private set

    init {
        for (entry in result.entries) {
            totalPercent += entry.key.getWeightedPercent(entry.value)
        }
    }

    class Builder(private val type: AppraiseType) {

        private val result: MutableMap<AppraiseAttribute, Double> = mutableMapOf()

        fun attribute(attribute: AppraiseAttribute, value: Double) = apply { result[attribute] = value }

        fun build() = AppraiseResult(type, result)
    }

    fun getStars(): Byte {
        for ((key, value) in BumpRegistry.starThresholds.entries) {
            if (totalPercent >= key) {
                return value
            }
        }
        return 0
    }

    fun apply(itemStack: ItemStack) {
        require(noAirItem(itemStack)) { "ItemStack cannot be null or air." }
        val meta = itemStack.itemMeta!!
        val material = itemStack.type
        val stars = getStars()

        // attributes
        for ((key, value) in result) {
            val attr = key.attribute

            for (slot in type.validEquipmentSlots) {
                // Check if the material is applicable for slot
                val tag = BumpTag.getTag(slot.name + "_SLOT")!!
                if (tag.isTagged(material)) {
                    meta.addAttributeModifier(
                        attr,
                        AttributeModifier(UUID.randomUUID(), type.key.toString(), value, getOperation(attr), slot)
                    )
                }
            }
        }

        // lore
        val loreLine: String = Bump.localization.getLore("appraised", GeneralUtils.getStars(stars))
        if (meta.hasLore()) {
            val lore = meta.lore!!
            for (i in lore.indices) {
                if (lore[i] == ChatUtil.color(Bump.localization.getLore("not-appraised"))) {
                    lore[i] = ChatUtil.color(loreLine)
                    break
                }
            }
            meta.lore = lore
        } else {
            val lore: MutableList<String> = ArrayList()
            lore.add(ChatUtil.color(loreLine))
            meta.lore = lore
        }

        // pdc
        PersistentDataAPI.setByte(meta, Keys.APPRAISE_LEVEL, stars)
        PersistentDataAPI.setByte(meta, Keys.APPRAISE_VERSION, 2.toByte())

        itemStack.setItemMeta(meta)
    }
}
