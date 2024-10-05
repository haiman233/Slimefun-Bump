@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.implementation.menu

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.appraise.AppraiseAttribute
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.utils.GeneralUtils
import io.github.slimefunguguproject.bump.utils.items.AppraiseUtils
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import io.github.slimefunguguproject.bump.utils.items.buildDisplayItem
import io.github.thebusybiscuit.slimefun4.core.services.sounds.SoundEffect
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.min

class AppraiseTypeMenu(
    private val appraiseType: AppraiseType,
    private val backCallback: Runnable,
) {

    companion object {

        private const val GUIDE_BACK = 0

        private const val INFO_SLOT = 4

        private const val EQUIPMENT_TYPE_SLOT = 12
        private const val MATERIAL_SLOT = 13
        private const val EQUIPMENT_SLOT_SLOT = 14

        private const val ATTRIBUTES_START = 27
    }

    /**
     * Open the menu to [Player].
     *
     * @param p The [Player] to open the menu to.
     */
    fun open(p: Player) {
        val chestMenu = ChestMenu(appraiseType.name)
        setupMenu(p, chestMenu)
        chestMenu.open(p)
    }

    private fun setupMenu(player: Player, menu: ChestMenu) {
        menu.isEmptySlotsClickable = false

        // Sound
        menu.addMenuOpeningHandler { p -> SoundEffect.GUIDE_BUTTON_CLICK_SOUND.playFor(p) }

        // Back
        menu.addItem(
            GUIDE_BACK, ChestMenuUtils.getBackButton(
                player,
                "",
                ChatColor.GRAY.toString() + Slimefun.getLocalization().getMessage(player, "guide.back.guide")
            )
        )
        menu.addMenuClickHandler(GUIDE_BACK) { _, _, _, _ ->
            backCallback.run()
            false
        }

        menu.addItem(INFO_SLOT, getAppraiseInfoItem(appraiseType))
        menu.addMenuClickHandler(INFO_SLOT, ChestMenuUtils.getEmptyClickHandler())

        menu.addItem(EQUIPMENT_TYPE_SLOT, getEquipmentTypeItem(appraiseType))
        menu.addMenuClickHandler(EQUIPMENT_TYPE_SLOT, ChestMenuUtils.getEmptyClickHandler())

        menu.addItem(MATERIAL_SLOT, getMaterialItem(appraiseType))
        menu.addMenuClickHandler(MATERIAL_SLOT, ChestMenuUtils.getEmptyClickHandler())

        menu.addItem(EQUIPMENT_SLOT_SLOT, getEquipmentSlotItem(appraiseType))
        menu.addMenuClickHandler(EQUIPMENT_SLOT_SLOT, ChestMenuUtils.getEmptyClickHandler())

        var slot = ATTRIBUTES_START
        for (attribute in appraiseType.attributes) {
            menu.addItem(slot, getAppraiseAttributeItem(attribute))
            menu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler())

            slot++
            if (slot > 53) {
                break
            }
        }
    }

    private fun getAppraiseInfoItem(type: AppraiseType): ItemStack {
        return buildDisplayItem {
            material = MaterialType.Material(Material.ANVIL)
            name = type.name
            lore = AppraiseUtils.getDescriptionLore(type)
        }
    }

    private fun getEquipmentTypeItem(type: AppraiseType): ItemStack {
        val equipmentType = type.equipmentType.toString()
        return buildDisplayItem {
            material = MaterialType.Material(Material.DIAMOND_SWORD)
            name = Bump.localization.getLore("appraise_info.equipment_type.name", equipmentType)
            lore = listOf(Bump.localization.getLore("appraise_info.equipment_type.${equipmentType.lowercase()}"))
        }
    }

    private fun getMaterialItem(type: AppraiseType): ItemStack {
        val result: ItemStack
        if (type.checkMaterial) {
            // Enabled checking material, display materials as well (cap at 10)
            val matLore: MutableList<String> = mutableListOf(
                Bump.localization.getLore("appraise_info.material.enabled"),
                ""
            )

            val materials = type.validMaterials.toList()
            val size = min(materials.size, 10)

            materials.subList(0, size).forEach {
                matLore.add("${ChatColor.GRAY}${GeneralUtils.getMaterialName(it)}")
            }

            if (materials.size > 10) {
                matLore.add(
                    Bump.localization.getLore("appraise_info.material.enabled_more", materials.size - size)
                )
            }

            result = buildDisplayItem {
                material = MaterialType.Material(Material.FILLED_MAP)
                name = Bump.localization.getLore("appraise_info.material.name")
                lore = matLore
            }
        } else {
            result = buildDisplayItem {
                material = MaterialType.Material(Material.MAP)
                name = Bump.localization.getLore("appraise_info.material.name")
                lore = listOf(Bump.localization.getLore("appraise_info.material.disabled"))
            }
        }
        return result
    }

    private fun getEquipmentSlotItem(type: AppraiseType): ItemStack {
        val itemLore = mutableListOf(
            Bump.localization.getLore("appraise_info.equipment_slot.lore"),
            ""
        )

        type.validEquipmentSlots.forEach {
            itemLore.add(ChatColor.GRAY.toString() + it.toString())
        }

        return buildDisplayItem {
            material = MaterialType.Material(Material.IRON_CHESTPLATE)
            name = Bump.localization.getLore("appraise_info.equipment_slot.name")
            lore = itemLore
        }
    }

    private fun getAppraiseAttributeItem(attribute: AppraiseAttribute): ItemStack {
        return buildDisplayItem {
            material = MaterialType.Material(Material.PAPER)
            name = Bump.localization.getLore("appraise_info.attribute.name", attribute.attribute.toString())
            lore = listOf(
                Bump.localization.getLore("appraise_info.attribute.range", attribute.min, attribute.max),
                Bump.localization.getLore("appraise_info.attribute.weight", attribute.weight),
            )
        }
    }
}
