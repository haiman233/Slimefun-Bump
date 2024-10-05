package io.github.slimefunguguproject.bump.implementation.items

import io.github.slimefunguguproject.bump.core.attributes.AppraisableItem
import io.github.slimefunguguproject.bump.implementation.BumpItems
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.items.multiblocks.Compressor
import org.bukkit.inventory.ItemStack

class RandomEquipment(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : SlimefunItem(itemGroup, itemStack, recipeType, recipe), AppraisableItem {

    override fun postRegister() {
        if (isDisabled) return
        val compressor: Compressor = (getById("COMPRESSOR") ?: return) as Compressor
        compressor.addRecipe(arrayOf(item), BumpItems.COMPRESSED_RANDOM_EQUIPMENT)
    }
}
