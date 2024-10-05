@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.utils.items

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.utils.constant.Keys
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil
import net.guizhanss.guizhanlib.minecraft.utils.compatibility.AttributeX
import org.bukkit.ChatColor
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.ItemStack

object AppraiseUtils {

    /**
     * Check if the [ItemStack] is marked as appraisable,
     * which means it can be used in appraisal instrument.
     *
     * @param itemStack The [ItemStack] to be checked.
     *
     * @return Whether the [ItemStack] is marked as appraisable.
     */
    fun isAppraisable(itemStack: ItemStack?): Boolean {
        return if (ValidateUtils.noAirItem(itemStack)) {
            PersistentDataAPI.getBoolean(itemStack!!.itemMeta!!, Keys.APPRAISABLE)
        } else {
            false
        }
    }

    /**
     * Set the [ItemStack] as appraisable in appraisal instrument
     *
     * @param itemStack the [ItemStack] to be set
     */
    fun setAppraisable(itemStack: ItemStack) {
        if (!ValidateUtils.noAirItem(itemStack)) {
            return
        }

        val im = itemStack.itemMeta!!

        // set lore
        val lore = if (im.hasLore()) im.lore!! else ArrayList()
        lore.add("")
        lore.add(ChatUtil.color(Bump.localization.getString("lores.not-appraised")))
        im.lore = lore

        // set pdc
        PersistentDataAPI.setBoolean(im, Keys.APPRAISABLE, true)

        itemStack.setItemMeta(im)
    }

    /**
     * Check if the [ItemStack] is appraised
     *
     * @param itemStack the [ItemStack] to be checked
     *
     * @return if the [ItemStack] is appraised
     */
    fun isAppraised(itemStack: ItemStack): Boolean {
        if (!ValidateUtils.noAirItem(itemStack)) {
            return false
        }
        return PersistentDataAPI.hasByte(itemStack.itemMeta!!, Keys.APPRAISE_LEVEL)
    }

    fun getOperation(attribute: Attribute): AttributeModifier.Operation {
        return when (attribute) {
            Attribute.GENERIC_MOVEMENT_SPEED, AttributeX.GENERIC_JUMP_STRENGTH -> AttributeModifier.Operation.ADD_SCALAR
            else -> AttributeModifier.Operation.ADD_NUMBER
        }
    }

    fun getDescriptionLore(type: AppraiseType) = type.description
        .map { ChatUtil.color("${ChatColor.GRAY}${it}") }
        .toMutableList()
}
