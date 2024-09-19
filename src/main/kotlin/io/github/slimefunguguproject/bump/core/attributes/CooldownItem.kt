package io.github.slimefunguguproject.bump.core.attributes

import io.github.slimefunguguproject.bump.utils.constant.Keys
import io.github.slimefunguguproject.bump.utils.items.ValidateUtils.noAirAndHasItemMeta
import io.github.thebusybiscuit.slimefun4.core.attributes.ItemAttribute
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import org.bukkit.inventory.ItemStack

interface CooldownItem : ItemAttribute {
    companion object {

        private const val INVALID_ITEMSTACK = "Invalid ItemStack."
    }

    /**
     * This variable returns the cooldown time in seconds.
     */
    fun getCooldown(): Int

    /**
     * Set the [ItemStack] to cooldown.
     *
     * @param itemStack The [ItemStack] to be set to cooldown.
     */
    fun setCooldown(itemStack: ItemStack) {
        require(noAirAndHasItemMeta(itemStack)) { INVALID_ITEMSTACK }

        val im = itemStack.itemMeta!!
        PersistentDataAPI.setLong(im, Keys.LAST_USED, System.currentTimeMillis())
        itemStack.setItemMeta(im)
    }

    /**
     * This method checks if given [ItemStack] has cooled down.
     *
     * @param itemStack The [ItemStack] to be checked
     *
     * @return if the item can be used now
     */
    fun isCooldown(itemStack: ItemStack): Boolean {
        require(noAirAndHasItemMeta(itemStack)) { INVALID_ITEMSTACK }

        val im = itemStack.itemMeta!!

        return if (PersistentDataAPI.hasLong(im, Keys.LAST_USED)) {
            val lastUsed = PersistentDataAPI.getLong(im, Keys.LAST_USED)

            lastUsed + getCooldown() * 1000L < System.currentTimeMillis()
        } else {
            true
        }
    }

    /**
     * This method checks if given [ItemStack] has cooled down,
     * if so, set the [ItemStack] to be just used.
     *
     * @param itemStack The [ItemStack] to be checked
     *
     * @return if the item can be used now
     */
    fun checkCooldown(itemStack: ItemStack): Boolean {
        require(noAirAndHasItemMeta(itemStack)) { INVALID_ITEMSTACK }

        return if (isCooldown(itemStack)) {
            setCooldown(itemStack)
            true
        } else {
            false
        }
    }
}
