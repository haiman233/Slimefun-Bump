package io.github.slimefunguguproject.bump.utils

import io.github.slimefunguguproject.bump.utils.items.ValidateUtils
import net.guizhanss.guizhanlib.utils.StringUtil
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object GeneralUtils {

    private const val STAR = "⭐"

    /**
     * Get a [String] of consecutive stars, maximum at 10.
     *
     *
     * When there are over 10 stars, returns number + star.
     *
     * @param n The number of stars
     *
     * @return [String] of consecutive stars.
     */
    fun getStars(n: Byte): String {
        var num = n
        return if (num <= 10) {
            val builder = StringBuilder()
            while (num > 0) {
                builder.append(STAR)
                num--
            }
            builder.toString()
        } else {
            "$n $STAR"
        }
    }

    /**
     * Make the [ItemStack] glow.
     *
     * @param item The [ItemStack] to be dealt with.
     */
    fun glowItem(item: ItemStack) {
        if (!ValidateUtils.noAirItem(item)) {
            return
        }

        val itemMeta = item.itemMeta!!
        itemMeta.addEnchant(Enchantment.LUCK, 1, true)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        item.setItemMeta(itemMeta)
    }

    /**
     * Get the [Material] name.
     *
     * @param material The [Material].
     *
     * @return The material name in Simplified Chinese if GuizhanLibPlugin exists. Otherwise, in English.
     */
    fun getMaterialName(material: Material): String {
        try {
            val clazz = Class.forName("net.guizhanss.guizhanlib.minecraft.helper.MaterialHelper")
            val result = clazz.getMethod("getName", Material::class.java).invoke(null, material)
            return result.toString()
        } catch (e: Exception) {
            return StringUtil.humanize(material.toString())
        }
    }

    inline fun <reified T : Enum<T>> valueOfOrNull(name: String): T? =
        enumValues<T>().firstOrNull { it.name == name }
}
