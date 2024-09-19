@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.implementation.items.tools

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.BumpRegistry
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.utils.items.AppraiseUtils
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import io.github.slimefunguguproject.bump.utils.items.ValidateUtils
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler
import io.github.thebusybiscuit.slimefun4.implementation.items.LimitedUseItem
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler
import net.guizhanss.guizhanlib.minecraft.utils.InventoryUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A quality identifier can mark available [ItemStack]
 * as appraisable.
 *
 * @author ybw0014
 */
class QualityIdentifier(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : LimitedUseItem(itemGroup, itemStack, recipeType, recipe) {

    companion object {

        const val MAX_USES = 5

        // gui
        private val BACKGROUND_SLOT = intArrayOf(
            0, 8, 9, 17, 18, 22, 26
        )
        private val INPUT_BORDER = intArrayOf(
            1, 2, 3, 10, 12, 19, 20, 21
        )
        private val OUTPUT_BORDER = intArrayOf(
            5, 6, 7, 14, 16, 23, 24, 25
        )
        private const val INFO_SLOT = 4
        private const val INPUT_SLOT = 11
        private const val APPRAISE_BUTTON = 13
        private const val OUTPUT_SLOT = 15
    }

    init {
        maxUseCount = MAX_USES
        addItemHandler(itemHandler)
    }

    override fun getItemHandler() = ItemUseHandler { e: PlayerRightClickEvent ->
        e.cancel()
        val p = e.player
        val paperItemStack = e.item

        if (paperItemStack.type == Material.AIR) {
            return@ItemUseHandler
        }

        if (paperItemStack.amount > 1) {
            Bump.localization.sendMessage(p, "stacked")
            return@ItemUseHandler
        }

        // Open menu
        val menu = ChestMenu(paperItemStack.itemMeta!!.displayName)
        createMenu(p, menu, paperItemStack)
        menu.open(p)
    }

    private fun createMenu(p: Player, menu: ChestMenu, paperItemStack: ItemStack) {
        menu.isPlayerInventoryClickable = true

        // Open sound
        menu.addMenuOpeningHandler { BumpSound.QUALITY_IDENTIFIER_OPEN.playFor(p) }

        // Setup menu
        for (i in BACKGROUND_SLOT) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler())
        }
        for (i in INPUT_BORDER) {
            menu.addItem(i, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler())
        }
        for (i in OUTPUT_BORDER) {
            menu.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler())
        }

        // Add status
        menu.addItem(INFO_SLOT, getUsesLeftGui(getUsesLeft(paperItemStack)), ChestMenuUtils.getEmptyClickHandler())

        // Add menu close handler
        menu.addMenuCloseHandler { _: Player ->
            InventoryUtil.push(p, menu.getItemInSlot(INPUT_SLOT))
            InventoryUtil.push(p, menu.getItemInSlot(OUTPUT_SLOT))
        }

        // Block Quality identifier click
        menu.addPlayerInventoryClickHandler { _, _, item: ItemStack?, _ ->
            val sfItem = getByItem(item)
            sfItem !is QualityIdentifier
        }

        // Add appraise button handler
        menu.addItem(APPRAISE_BUTTON, getUseGui(), getAppraiseButtonHandler(p, menu, paperItemStack))
    }

    private fun getAppraiseButtonHandler(p: Player, menu: ChestMenu, paperItemStack: ItemStack) =
        MenuClickHandler { _, _, _, _ ->
            // Check input slot
            val input = menu.getItemInSlot(INPUT_SLOT)

            if (!ValidateUtils.noAirItem(input)) {
                Bump.localization.sendMessage(p, "no-input")
                BumpSound.QUALITY_IDENTIFIER_FAIL.playFor(p)
                return@MenuClickHandler false
            }

            // Check output slot
            if (menu.getItemInSlot(OUTPUT_SLOT) != null) {
                Bump.localization.sendMessage(p, "output-no-space")
                BumpSound.QUALITY_IDENTIFIER_FAIL.playFor(p)
                return@MenuClickHandler false
            }

            // validate item
            if (isValidItem(input)) {
                // item can be marked appraisable
                val output = input.clone()
                AppraiseUtils.setAppraisable(output)
                menu.replaceExistingItem(INPUT_SLOT, null)
                menu.replaceExistingItem(OUTPUT_SLOT, output)

                damageItem(p, paperItemStack)

                /*
                 * The paper is used up, should close the gui.
                 * Otherwise, update the status slot.
                 */
                if (paperItemStack.type == Material.AIR) {
                    p.closeInventory()
                } else {
                    menu.replaceExistingItem(INFO_SLOT, getUsesLeftGui(getUsesLeft(paperItemStack)))

                    // play sound only if appraisal paper is not broken
                    BumpSound.QUALITY_IDENTIFIER_SUCCEED.playFor(p)
                }

                Bump.localization.sendMessage(p, "tool.quality_identifier.success")
            } else {
                Bump.localization.sendMessage(p, "tool.quality_identifier.invalid")
                BumpSound.QUALITY_IDENTIFIER_FAIL.playFor(p)
            }
            false
        }

    /**
     * Validate the item. The item that can be marked appraisable
     * should meet these requirements:
     * - is a valid item for any appraise type
     * - has not been appraised yet
     * - has not been marked appraisable yet
     *
     * @param itemStack The [ItemStack] to be validated.
     *
     * @return If the [ItemStack] is applicable to appraisal paper.
     */
    private fun isValidItem(itemStack: ItemStack): Boolean {
        return if (BumpRegistry.appraiseTypes.any { it.isValidItem(itemStack) }) {
            Bump.debug("Item is accepted by an appraise type: $itemStack")
            Bump.debug("appraised: ${AppraiseUtils.isAppraised(itemStack)}")
            Bump.debug("appraisable: ${AppraiseUtils.isAppraisable(itemStack)}")
            !AppraiseUtils.isAppraised(itemStack) && !AppraiseUtils.isAppraisable(itemStack)
        } else {
            false
        }
    }

    /**
     * Get the uses left of the quality identifier.
     */
    private fun getUsesLeft(itemStack: ItemStack): Int {
        return PersistentDataAPI.getInt(itemStack.itemMeta!!, storageKey, maxUseCount)
    }

    /**
     * Get the GUI item to indicate the uses left.
     */
    private fun getUsesLeftGui(usesLeft: Int) =
        CustomItemStack(Material.LIME_STAINED_GLASS_PANE, LoreBuilder.usesLeft(usesLeft))

    /**
     * Get the GUI item of use button.
     */
    private fun getUseGui(): ItemStack = Bump.localization.getGuiItem(
        MaterialType.Material(Material.PAPER),
        "QUALITY_IDENTIFIER_USE",
    )
}

