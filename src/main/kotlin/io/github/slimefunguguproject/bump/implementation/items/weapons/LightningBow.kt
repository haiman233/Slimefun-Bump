package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.handlers.BowUseHandler
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.inventory.ItemStack

class LightningBow(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    hunger: Int,
) : BumpBow(itemGroup, itemStack, recipeType, recipe, hunger) {

    override fun getItemHandler() = BowUseHandler { e: EntityShootBowEvent, p: Player, item: ItemStack ->
        e.isCancelled = true
        val target = p.getTargetBlock(null, 200)
        if (target.type == Material.AIR) {
            return@BowUseHandler
        }

        val targetLocation = target.location
        if (costHunger(p)) {
            damageItem(p, item)

            Bump.localization.sendActionbarMessage(p, "weapon.lightning_bow")

            for (i in 0..9) {
                p.world.strikeLightning(targetLocation)
            }
        } else {
            Bump.localization.sendActionbarMessage(p, "weapon.low-food-level")
        }
    }
}
