package io.github.slimefunguguproject.bump.implementation.items.machines

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.implementation.menu.AppraiseTypesMenu
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import org.bukkit.Material
import java.util.function.Consumer

internal class AppraiserSelector(
    successCallback: Consumer<AppraiseType>,
    backCallback: Runnable
) : AppraiseTypesMenu(
    Bump.localization.getLore("gui.appraise_type_selector_menu.title"),
    successCallback,
    backCallback
) {

    override fun getInfoItem() =
        Bump.localization.getGuiItem(MaterialType.Material(Material.NAME_TAG), "APPRAISER_SELECTOR_INFO")
}
