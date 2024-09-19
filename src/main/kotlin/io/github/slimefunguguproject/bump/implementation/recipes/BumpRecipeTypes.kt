package io.github.slimefunguguproject.bump.implementation.recipes

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.constant.Keys.createKey
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import org.bukkit.Material


object BumpRecipeTypes {

    val GET_GOLD_SPADE: RecipeType = RecipeType(
        "get_gold_spade".createKey(),
        CustomItemStack(
            Material.GOLDEN_SHOVEL,
            Bump.localization.getItemName("_RECIPE_GET_GOLD_SPADE"),
            Bump.localization.getItemLore("_RECIPE_GET_GOLD_SPADE")
        )
    )
}
