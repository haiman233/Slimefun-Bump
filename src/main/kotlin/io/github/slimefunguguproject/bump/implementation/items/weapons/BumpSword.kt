package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.attributes.CooldownItem
import io.github.slimefunguguproject.bump.core.attributes.CostHungerItem
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class BumpSword(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    hunger: Int,
) : SimpleSlimefunItem<ItemUseHandler>(itemGroup, itemStack, recipeType, recipe), NotPlaceable, CostHungerItem,
    CooldownItem {

    init {
        require(hunger in 0..20) { "Hunger cost must be between 0 and 20" }
    }

    private val hungerCostSetting = IntRangeSetting(this, "hunger-cost", 0, hunger, 20)
    private val cooldownSetting = IntRangeSetting(this, "cooldown-in-seconds", 0, 0, Int.MAX_VALUE)

    init {
        addItemSetting(hungerCostSetting)
        addItemSetting(cooldownSetting)
    }

    override fun getHungerCost() = hungerCostSetting.value

    override fun getCooldown() = cooldownSetting.value

    /**
     * This function is called when player use this sword
     *
     * @param p     the [Player] that uses this sword
     * @param sword the [ItemStack] of this sword
     */
    abstract fun onItemUse(p: Player, sword: ItemStack)

    override fun getItemHandler() = ItemUseHandler { e: PlayerRightClickEvent ->
        val p = e.player
        val item = e.item
        if (isCooldown(item)) {
            if (costHunger(p)) {
                setCooldown(item)
                onItemUse(p, item)
            } else {
                Bump.localization.sendActionbarMessage(p, "weapon.low-food-level")
            }
        } else {
            Bump.localization.sendActionbarMessage(p, "weapon.cooldown")
        }
    }
}

