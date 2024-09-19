package io.github.slimefunguguproject.bump.implementation.items.tools

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.implementation.BumpItems
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock
import net.guizhanss.guizhanlib.utils.RandomUtil
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.min

class GetGoldSpade(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : UnplaceableBlock(itemGroup, itemStack, recipeType, recipe) {

    init {
        addItemHandler(getToolUseHandler())
    }

    private fun getToolUseHandler() =
        ToolUseHandler { e: BlockBreakEvent, item: ItemStack, _: Int, drops: MutableList<ItemStack> ->
            if (e.block.type != Material.SAND) return@ToolUseHandler

            val luckLevel = min(item.getEnchantmentLevel(Enchantment.LUCK), 5)
            if (!RandomUtil.testChance(30 + luckLevel * 10, 100)) return@ToolUseHandler

            if (BumpItems.BROKEN_GOLD_COIN.item?.isDisabledIn(e.block.world) == true) return@ToolUseHandler

            drops.clear()
            drops.add(BumpItems.BROKEN_GOLD_COIN)
            Bump.localization.sendActionbarMessage(e.player, "tool.get_gold_spade")
        }
}
