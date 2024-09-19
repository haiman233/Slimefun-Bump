package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.core.attributes.CostHungerItem
import io.github.slimefunguguproject.bump.core.handlers.BowUseHandler
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem
import org.bukkit.inventory.ItemStack


abstract class BumpBow(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    hunger: Int
) : SimpleSlimefunItem<BowUseHandler>(itemGroup, itemStack, recipeType, recipe), DamageableItem, CostHungerItem {

    init {
        require(hunger in 0..20) { "Hunger cost must be between 0 and 20" }
    }

    private val hungerCostSetting = IntRangeSetting(this, "hunger-cost", 0, hunger, 20)
    private val costDurabilitySetting = ItemSetting(this, "cost-durability", true)

    init {
        addItemSetting(costDurabilitySetting)
        addItemSetting(hungerCostSetting)
    }

    override fun isDamageable() = costDurabilitySetting.value

    override fun getHungerCost() = hungerCostSetting.value
}
