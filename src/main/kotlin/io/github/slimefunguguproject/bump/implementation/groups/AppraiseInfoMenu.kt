package io.github.slimefunguguproject.bump.implementation.groups

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.implementation.menu.AppraiseTypesMenu
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import org.bukkit.Material
import java.util.function.Consumer

internal class AppraiseInfoMenu(
    name: String,
    successCallback: Consumer<AppraiseType>,
    backCallback: Runnable
) : AppraiseTypesMenu(name, successCallback, backCallback) {

    override fun getInfoItem() =
        Bump.localization.getGuiItem(MaterialType.Material(Material.NAME_TAG), "APPRAISE_MENU_INFO")
}
