package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.utils.FoodLevelUtils
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.ItemStack

class SoulSword(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : SimpleSlimefunItem<ItemUseHandler>(itemGroup, itemStack, recipeType, recipe) {

    override fun getItemHandler() = ItemUseHandler { e: PlayerRightClickEvent ->
        val p = e.player
        val health = p.health
        val maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
        val foodLevel = p.foodLevel

        if (maxHealth <= health) {
            Bump.localization.sendActionbarMessage(p, "weapon.unavailable")
            return@ItemUseHandler
        }
        if (foodLevel >= 2) {
            if (maxHealth - health <= foodLevel) {
                // Food level can be partially converted to full health
                FoodLevelUtils.set(p, (foodLevel - (maxHealth - health)).toInt())
                p.health = maxHealth
                Bump.localization.sendActionbarMessage(p, "weapon.soul_sword.converted-part")
            } else {
                // Food level can be all converted to health
                FoodLevelUtils.set(p, 0)
                p.health = health + foodLevel
                Bump.localization.sendActionbarMessage(p, "weapon.soul_sword.converted-all")
            }

            BumpSound.SOUL_SWORD_USE.playFor(p)
        } else {
            Bump.localization.sendActionbarMessage(p, "weapon.low-food-level")
        }
    }
}

