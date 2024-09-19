@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.implementation.items.machines

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset
import net.guizhanss.guizhanlib.slimefun.machines.MenuBlock
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

abstract class SimpleMenuBlock(
    itemGroup: ItemGroup,
    item: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : MenuBlock(itemGroup, item, recipeType, recipe), EnergyNetComponent {

    companion object {

        // gui
        private val BACKGROUND = intArrayOf(
            0, 4, 8, 9, 17, 18, 22, 26
        )
        private val INPUT_BACKGROUND = intArrayOf(
            1, 2, 3, 10, 12, 19, 20, 21
        )
        private val OUTPUT_BACKGROUND = intArrayOf(
            5, 6, 7, 14, 16, 23, 24, 25
        )

        private const val OPERATION_SLOT = 13

        @JvmStatic
        protected val INPUT_SLOT = 11

        @JvmStatic
        protected val OUTPUT_SLOT = 15
    }

    protected abstract val operationSlotItem: ItemStack

    protected abstract fun onOperate(menu: BlockMenu, b: Block, p: Player, action: ClickAction)

    override fun setup(preset: BlockMenuPreset) {
        preset.drawBackground(ChestMenuUtils.getBackground(), BACKGROUND)
        preset.drawBackground(ChestMenuUtils.getInputSlotTexture(), INPUT_BACKGROUND)
        preset.drawBackground(ChestMenuUtils.getOutputSlotTexture(), OUTPUT_BACKGROUND)

        preset.addItem(OPERATION_SLOT, operationSlotItem)
        preset.addMenuClickHandler(OPERATION_SLOT, ChestMenuUtils.getEmptyClickHandler())
    }

    override fun onBreak(e: BlockBreakEvent, menu: BlockMenu) {
        super.onBreak(e, menu)
        val location = menu.location
        menu.dropItems(location, INPUT_SLOT)
        menu.dropItems(location, OUTPUT_SLOT)
    }

    override fun onNewInstance(menu: BlockMenu, b: Block) {
        super.onNewInstance(menu, b)
        menu.addMenuClickHandler(OPERATION_SLOT) { p: Player, _: Int, _: ItemStack, clickAction: ClickAction ->
            onOperate(menu, b, p, clickAction)
            false
        }
    }

    override fun getEnergyComponentType() = EnergyNetComponentType.CONSUMER

    override fun getInputSlots() = intArrayOf(INPUT_SLOT)

    override fun getOutputSlots() = intArrayOf(OUTPUT_SLOT)
}

