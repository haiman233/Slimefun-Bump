@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.implementation.items.machines

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.utils.constant.Keys
import io.github.slimefunguguproject.bump.utils.items.AppraiseUtils
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import io.github.slimefunguguproject.bump.utils.items.ValidateUtils
import io.github.slimefunguguproject.bump.utils.tags.BumpTag
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class AttributeGrindstone(
    itemGroup: ItemGroup,
    item: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : SimpleMenuBlock(itemGroup, item, recipeType, recipe) {

    companion object {

        // energy
        const val ENERGY_CONSUMPTION: Int = 1314
    }

    override val operationSlotItem = Bump.localization.getGuiItem(
        MaterialType.Material(Material.NAME_TAG),
        "ATTRIBUTE_GRINDSTONE_USE",
    )

    override fun getCapacity() = ENERGY_CONSUMPTION

    override fun onOperate(menu: BlockMenu, b: Block, p: Player, action: ClickAction) {
        grind(menu, p)
    }

    private fun grind(menu: BlockMenu, p: Player) {
        val item = menu.getItemInSlot(INPUT_SLOT)

        // null check
        if (!ValidateUtils.noAirItem(item)) {
            Bump.localization.sendMessage(p, "no-input")
            BumpSound.ATTRIBUTE_GRINDSTONE_FAIL.playFor(p)
            return
        }

        // check if input item is appraised
        if (!AppraiseUtils.isAppraised(item)) {
            Bump.localization.sendMessage(p, "machine.attribute-grindstone.invalid")
            BumpSound.ATTRIBUTE_GRINDSTONE_FAIL.playFor(p)
            return
        }

        // check output slot
        if (menu.getItemInSlot(OUTPUT_SLOT) != null) {
            Bump.localization.sendMessage(p, "output-no-space")
            BumpSound.ATTRIBUTE_GRINDSTONE_FAIL.playFor(p)
            return
        }

        // check energy
        val charge = getCharge(menu.location)
        if (charge < ENERGY_CONSUMPTION) {
            Bump.localization.sendMessage(p, "not-enough-power")
            BumpSound.ATTRIBUTE_GRINDSTONE_FAIL.playFor(p)
            return
        }

        val output = item.clone()
        clearAttributes(output)
        menu.replaceExistingItem(INPUT_SLOT, null)
        menu.pushItem(output, OUTPUT_SLOT)

        setCharge(menu.location, 0)
        Bump.localization.sendMessage(p, "machine.attribute-grindstone.success")
        BumpSound.ATTRIBUTE_GRINDSTONE_SUCCEED.playFor(p)
    }

    private fun clearAttributes(itemStack: ItemStack) {
        val meta = itemStack.itemMeta!!
        // check the appraising version
        val version = PersistentDataAPI.getByte(meta, Keys.APPRAISE_VERSION, 1.toByte())

        removeModifiers(itemStack, meta, version)

        // set pdc
        PersistentDataAPI.setBoolean(meta, Keys.APPRAISABLE, true)
        PersistentDataAPI.remove(meta, Keys.APPRAISE_LEVEL)

        // set lore
        val appraisedLore = ChatUtil.color(Bump.localization.getString("lores.appraised"))
        val appraisedLorePrefix = appraisedLore.substring(0, appraisedLore.indexOf("{0}"))
        val lore = if (meta.hasLore()) meta.lore!! else mutableListOf()
        for (i in lore.indices) {
            if (lore[i].startsWith(appraisedLorePrefix)) {
                lore[i] = ChatUtil.color(Bump.localization.getString("lores.not-appraised"))
                break
            }
        }
        meta.lore = lore

        // done
        itemStack.setItemMeta(meta)
    }

    private fun removeModifiers(itemStack: ItemStack, meta: ItemMeta, version: Byte) {
        if (version.toInt() == 1) {
            // v1 legacy, remove all attribute modifiers
            EquipmentSlot.entries.forEach {
                if (BumpTag.getTag(it.name + "_SLOT")!!.isTagged(itemStack.type)) {
                    meta.removeAttributeModifier(it)
                }
            }
        } else {
            // v1, remove all bump attribute modifiers
            val modifierMap = meta.attributeModifiers ?: return

            for ((attribute, modifier) in modifierMap.entries()) {
                val key = NamespacedKey.fromString(modifier.name, Bump.instance)
                if (key != null && AppraiseType.getByKey(key) != null) {
                    meta.removeAttributeModifier(attribute, modifier)
                }
            }
        }
    }
}
