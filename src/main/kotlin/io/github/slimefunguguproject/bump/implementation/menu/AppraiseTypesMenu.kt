@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.implementation.menu

import io.github.bakedlibs.dough.items.CustomItemStack
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.core.BumpRegistry
import io.github.thebusybiscuit.slimefun4.core.services.sounds.SoundEffect
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer
import kotlin.math.ceil
import kotlin.math.min

/**
 * This class will open a menu with all [AppraiseType].
 *
 * @author ybw0014
 */
abstract class AppraiseTypesMenu(
    private val name: String,
    private val successCallback: Consumer<AppraiseType>,
    private val backCallback: Runnable
) {

    companion object {

        private const val PAGE_SIZE = 36

        private const val GUIDE_BACK = 1
        private const val GUIDE_INFO = 4

        private const val PAGE_PREVIOUS = 46
        private const val PAGE_NEXT = 52

        private val HEADER = intArrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8
        )
        private val FOOTER = intArrayOf(
            45, 46, 47, 48, 49, 50, 51, 52, 53
        )
    }

    /**
     * Return the [ItemStack] to display the information of the current page.
     */
    abstract fun getInfoItem(): ItemStack

    /**
     * Open the menu to [Player].
     *
     * @param p The [Player] to open the menu to.
     */
    fun open(p: Player) {
        val chestMenu = ChestMenu(name)

        for (slot in HEADER) {
            chestMenu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler())
        }
        for (slot in FOOTER) {
            chestMenu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler())
        }

        chestMenu.isEmptySlotsClickable = false
        displayCollection(p, chestMenu, 1)
        chestMenu.open(p)
    }

    private fun displayCollection(p: Player, menu: ChestMenu, page: Int) {
        val appraiseTypes = BumpRegistry.appraiseTypes
            .filter { type -> type.hasPermission(p) }
            .toList()
        val total = appraiseTypes.size
        val totalPages = ceil(total / PAGE_SIZE.toDouble()).toInt()
        val start = (page - 1) * PAGE_SIZE
        val end = min(start + PAGE_SIZE, total)

        val subList = appraiseTypes.subList(start, end)

        // header & footer
        menu.replaceExistingItem(GUIDE_INFO, getInfoItem())
        setupFooter(p, menu, page, totalPages)

        // Sound
        SoundEffect.GUIDE_BUTTON_CLICK_SOUND.playFor(p)

        // Back
        menu.replaceExistingItem(
            GUIDE_BACK, ChestMenuUtils.getBackButton(
                p,
                "",
                ChatColor.GRAY.toString() + Slimefun.getLocalization().getMessage(p, "guide.back.guide")
            )
        )
        menu.addMenuClickHandler(GUIDE_BACK) { _, _, _, _ ->
            backCallback.run()
            false
        }

        // list appraise types
        for (i in 0 until PAGE_SIZE) {
            val slot = i + 9

            if (i + 1 <= subList.size) {
                val appraiseType = subList[i]
                menu.replaceExistingItem(slot, getDisplayItem(appraiseType))
                menu.addMenuClickHandler(slot) { _, _, _, _ ->
                    successCallback.accept(appraiseType)
                    false
                }
            } else {
                menu.replaceExistingItem(slot, null)
                menu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler())
            }
        }
    }

    private fun setupFooter(player: Player, menu: ChestMenu, page: Int, totalPages: Int) {
        menu.replaceExistingItem(PAGE_PREVIOUS, ChestMenuUtils.getPreviousButton(player, page, totalPages))
        menu.addMenuClickHandler(PAGE_PREVIOUS) { p: Player, _, _, _ ->
            val previousPage = page - 1
            if (previousPage >= 1) {
                displayCollection(p, menu, previousPage)
            }
            false
        }

        menu.replaceExistingItem(PAGE_NEXT, ChestMenuUtils.getNextButton(player, page, totalPages))
        menu.addMenuClickHandler(PAGE_NEXT) { p: Player, _, _, _ ->
            val nextPage = page + 1
            if (nextPage <= totalPages) {
                displayCollection(p, menu, nextPage)
            }
            false
        }
    }

    private fun getDisplayItem(type: AppraiseType): ItemStack {
        return CustomItemStack(
            Material.PAPER,
            type.name,
            type.description
        )
    }
}
