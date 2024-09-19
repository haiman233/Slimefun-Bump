package io.github.slimefunguguproject.bump.implementation.items.food

import io.github.slimefunguguproject.bump.Bump
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack


abstract class ConsumableFood(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : SimpleSlimefunItem<ItemConsumptionHandler>(itemGroup, itemStack, recipeType, recipe) {

    override fun getItemHandler() = ItemConsumptionHandler { e: PlayerItemConsumeEvent, p: Player, _: ItemStack ->
        val messageKey = id.replace(Bump.localization.idPrefix, "").lowercase()
        Bump.localization.sendActionbarMessage(p, "food.$messageKey")

        if (p.gameMode != GameMode.CREATIVE) {
            ItemUtils.consumeItem(e.item, false)
        }
        applyFoodEffects(p)
    }

    abstract fun applyFoodEffects(p: Player)
}
