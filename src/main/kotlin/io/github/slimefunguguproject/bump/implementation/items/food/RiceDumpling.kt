package io.github.slimefunguguproject.bump.implementation.items.food

import io.github.slimefunguguproject.bump.utils.FoodLevelUtils
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class RiceDumpling(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : ItemFood(itemGroup, itemStack, recipeType, recipe) {

    override fun applyFoodEffects(p: Player) {
        FoodLevelUtils.add(p, 8)
        p.addPotionEffect(PotionEffect(PotionEffectType.CONDUIT_POWER, 200, 1))
        p.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 1000, 1))
        p.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1))
        p.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 200, 2))
        p.addPotionEffect(PotionEffect(PotionEffectType.LUCK, 2000, 2))
    }
}
