package io.github.slimefunguguproject.bump.implementation.items.machines

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import io.github.slimefunguguproject.bump.utils.items.ValidateUtils
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset
import net.guizhanss.guizhanlib.slimefun.machines.TickingMenuBlock
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

class ItemConverter(
    itemGroup: ItemGroup,
    item: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : TickingMenuBlock(itemGroup, item, recipeType, recipe) {

    companion object {

        // gui
        private const val INFO_SLOT = 4

        private val INPUT_SLOTS = intArrayOf(
            0, 1, 2, 3
        )

        private val OUTPUT_SLOTS = intArrayOf(
            5, 6, 7, 8
        )

        private val MAPPING = mapOf(
            // materials
            "SUN_ENERGY" to "PHOTOSYNTHETIC_ENERGY",
            "MECHA_GEAR" to "MECHANICAL_GEAR",
            "OLD_COIN" to "BROKEN_GOLD_COIN",
            "MAKE" to "COMPUTER_TECH_CORE",
            "OLD_CPU" to "BROKEN_CPU",
            "CPU" to "CPU",
            "SOUL_PAPER" to "ANCIENT_RUNE_SOUL",
            "KSF_STUFF" to "INSTANT_NOODLE_SEASONING",
            "WATER_SUGAR" to "POP_CANDY",
            "PEACH_WOOD" to "PEACH_WOOD",
            "UPDATE_POWER" to "UPDATE_CORE",
            "COMPRESSED_RANDOM_EQUIPMENT" to "COMPRESSED_RANDOM_EQUIPMENT",

            // food
            "XUEBI" to "SPRITE",
            "KELE" to "COLA",
            "FANGBIANMIAN" to "INSTANT_NOODLE",
            "LATIAO" to "SPICY_STRIPS",
            "KOUXIANGTANG" to "CHEWING_GUM",
            "ZONGZI" to "RICE_DUMPLING",

            // tools
            "GETGOLD_SPADE" to "GET_GOLD_SPADE",
            "QUALITY_IDENTIFIER" to "QUALITY_IDENTIFIER",

            // machines
            "APPRAISAL" to "APPRAISER",
            "ATTRIBUTE_GRINDSTONE" to "ATTRIBUTE_GRINDSTONE",

            // armor
            "RANDOM_HELMET" to "RANDOM_HELMET",
            "RANDOM_HORSE_ARMOR" to "RANDOM_HORSE_ARMOR",

            // weapons
            "RANDOM_SWORD" to "RANDOM_SWORD",
            "LIGHT_BOW" to "LIGHTNING_BOW",
            "WITHERSKULL_BOW" to "WITHER_SKULL_BOW",
            "EMER_SWORD" to "EMERALD_SWORD",
            "BONE_SWORD" to "BONE_SWORD",
            "GUARD_SWORD" to "GUARDIAN_SWORD",
            "PEACH_SWORD" to "PEACH_WOOD_SWORD",
            "SOUL_SWORD" to "SOUL_SWORD",
            "SKY_SWORD" to "HEAVEN_BREAKING_SWORD",
            "DEVIL_SWORD" to "DEMON_SLAYER_SWORD",
            "SKY_DEVIL_SWORD" to "HEAVEN_BREAKING_DEMON_SLAYER_SWORD",
        )
    }

    override fun getInputSlots() = INPUT_SLOTS

    override fun getOutputSlots() = OUTPUT_SLOTS

    override fun setup(preset: BlockMenuPreset) {
        preset.addItem(INFO_SLOT, getInfoGui(), ChestMenuUtils.getEmptyClickHandler())
    }

    override fun tick(b: Block, menu: BlockMenu) {
        for (inputSlot in INPUT_SLOTS) {
            val input = menu.getItemInSlot(inputSlot)
            if (!ValidateUtils.noAirItem(input)) continue
            // get id
            val id = Slimefun.getItemDataService().getItemData(input)
            if (id.isEmpty) continue

            // get output
            val newIdPart = MAPPING[id.get()] ?: continue
            val newId = Bump.localization.idPrefix + newIdPart
            val output = input.clone()
            Slimefun.getItemDataService().setItemData(output, newId)
            Slimefun.getItemTextureService().setTexture(output, newId)

            if (InvUtils.fits(menu.toInventory(), output, *OUTPUT_SLOTS)) {
                menu.replaceExistingItem(inputSlot, null)
                menu.pushItem(output, *OUTPUT_SLOTS)
            }
        }
    }

    private fun getInfoGui() = Bump.localization.getGuiItem(
        MaterialType.Material(Material.BOOK),
        "ITEM_CONVERTER_INFO",
    )
}
