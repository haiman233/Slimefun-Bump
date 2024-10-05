package io.github.slimefunguguproject.bump.utils.items

import org.bukkit.inventory.ItemStack

object ValidateUtils {

    /**
     * Validate the given [ItemStack] is not `null` or an air item.
     *
     * @param itemStack The [ItemStack] to be validated.
     *
     * @return if the [ItemStack] is valid.
     */
    fun noAirItem(itemStack: ItemStack?) = itemStack != null && !itemStack.type.isAir

    /**
     * Validate the given [ItemStack] is not `null`, not an air item, and has item meta.
     *
     * @param itemStack The [ItemStack] to be validated.
     *
     * @return if the [ItemStack] is valid.
     */
    fun noAirAndHasItemMeta(itemStack: ItemStack?) = noAirItem(itemStack) && itemStack!!.hasItemMeta()
}
