@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.implementation.items.machines

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.core.BumpRegistry
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.implementation.items.RandomEquipment
import io.github.slimefunguguproject.bump.utils.items.AppraiseUtils
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import io.github.slimefunguguproject.bump.utils.items.ValidateUtils
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction
import me.mrCookieSlime.Slimefun.api.BlockStorage
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.logging.Level

class Appraiser(
    itemGroup: ItemGroup,
    item: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : SimpleMenuBlock(itemGroup, item, recipeType, recipe) {

    companion object {

        // energy
        const val ENERGY_CONSUMPTION = 114514

        private const val APPRAISE_TYPE_SLOT = 4
        private const val APPRAISE_TYPE_KEY = "appraise_type"
    }

    override val operationSlotItem = Bump.localization.getGuiItem(
        MaterialType.Material(Material.NAME_TAG),
        "APPRAISER_USE",
    )

    override fun getCapacity() = ENERGY_CONSUMPTION

    override fun onNewInstance(menu: BlockMenu, b: Block) {
        super.onNewInstance(menu, b)
        updateSelector(menu, b.location)
    }

    override fun onOperate(menu: BlockMenu, b: Block, p: Player, action: ClickAction) {
        appraise(menu, p)
    }

    override fun register(addon: SlimefunAddon) {
        if (BumpRegistry.appraiseTypes.isEmpty()) {
            Bump.log(Level.WARNING, "No appraise types registered, Appraiser will not be registered.")
            return
        }
        super.register(addon)
    }

    private fun openSelector(p: Player, menu: BlockMenu, l: Location) {
        AppraiserSelector({ type: AppraiseType ->
            BlockStorage.addBlockInfo(l, APPRAISE_TYPE_KEY, type.key.toString())
            updateSelector(menu, l)
            BumpSound.APPRAISE_TYPE_SELECT.playFor(p)
            menu.open(p)
        }, {
            menu.open(p)
        }).open(p)
    }

    private fun appraise(menu: BlockMenu, p: Player) {
        val item = menu.getItemInSlot(INPUT_SLOT)

        // null check
        if (!ValidateUtils.noAirItem(item)) {
            Bump.localization.sendMessage(p, "no-input")
            BumpSound.APPRAISER_FAIL.playFor(p)
            return
        }

        // validate input
        if (!validate(item)) {
            Bump.localization.sendMessage(p, "machine.appraiser.invalid")
            BumpSound.APPRAISER_FAIL.playFor(p)
            return
        }

        // check if input item is already appraised
        if (AppraiseUtils.isAppraised(item)) {
            Bump.localization.sendMessage(p, "machine.appraiser.appraised")
            BumpSound.APPRAISER_FAIL.playFor(p)
            return
        }

        // check output slot
        if (menu.getItemInSlot(OUTPUT_SLOT) != null) {
            Bump.localization.sendMessage(p, "output-no-space")
            BumpSound.APPRAISER_FAIL.playFor(p)
            return
        }

        // check energy
        val charge: Int = getCharge(menu.location)
        if (charge < ENERGY_CONSUMPTION) {
            Bump.localization.sendMessage(p, "not-enough-power")
            BumpSound.APPRAISER_FAIL.playFor(p)
            return
        }

        // Check current appraise type
        val type: AppraiseType = getCurrentType(menu.location)
        if (!type.hasPermission(p)) {
            Bump.localization.sendMessage(p, "no-permission")
            BumpSound.APPRAISER_FAIL.playFor(p)
            return
        }
        if (!type.isValidItem(item)) {
            Bump.localization.sendMessage(p, "machine.appraiser.not-accepted")
            BumpSound.APPRAISER_FAIL.playFor(p)
            return
        }

        val output = item.clone()
        val result = type.appraise()

        result.apply(output)

        menu.replaceExistingItem(INPUT_SLOT, null)
        menu.pushItem(output, OUTPUT_SLOT)

        setCharge(menu.location, 0)
        Bump.localization.sendMessage(p, "machine.appraiser.success")
        BumpSound.APPRAISER_SUCCEED.playFor(p)
    }

    private fun validate(itemStack: ItemStack) =
        getByItem(itemStack) is RandomEquipment || AppraiseUtils.isAppraisable(itemStack)

    private fun updateSelector(menu: BlockMenu, l: Location) {
        val type: AppraiseType = getCurrentType(l)
        menu.replaceExistingItem(
            APPRAISE_TYPE_SLOT, Bump.localization.getGuiItem(
                MaterialType.Material(Material.PAPER),
                "APPRAISER_SELECTOR",
                type.name,
                *type.description.toTypedArray()
            )
        )
        menu.addMenuClickHandler(APPRAISE_TYPE_SLOT) { player: Player, _, _, _ ->
            openSelector(player, menu, l)
            false
        }
    }

    private fun getCurrentType(loc: Location): AppraiseType {
        val current: String? = BlockStorage.getLocationInfo(loc, APPRAISE_TYPE_KEY)
        var type: AppraiseType? = null
        if (current != null) {
            val key: NamespacedKey? = NamespacedKey.fromString(current, Bump.instance)
            if (key != null) {
                type = AppraiseType.getByKey(key)
            }
        }

        if (type == null) {
            type = BumpRegistry.appraiseTypes.first()
        }
        return type
    }
}
