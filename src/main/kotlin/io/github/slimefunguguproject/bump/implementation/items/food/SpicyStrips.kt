package io.github.slimefunguguproject.bump.implementation.items.food

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.FoodLevelUtils
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class SpicyStrips(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : ConsumableFood(itemGroup, itemStack, recipeType, recipe) {

    override fun applyFoodEffects(p: Player) {
        FoodLevelUtils.add(p, 2)
        p.addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION, 200, 2))

        Bump.scheduler().run {
            if (p.hasPotionEffect(PotionEffectType.HUNGER)) {
                p.removePotionEffect(PotionEffectType.HUNGER)
            }
        }
    }
}
