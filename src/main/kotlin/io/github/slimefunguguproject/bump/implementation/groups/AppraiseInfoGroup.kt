package io.github.slimefunguguproject.bump.implementation.groups

import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import io.github.slimefunguguproject.bump.implementation.menu.AppraiseTypeMenu
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class AppraiseInfoGroup(
    private val name: String,
    key: NamespacedKey,
    item: ItemStack
) : FlexItemGroup(key, item) {

    override fun isVisible(player: Player, playerProfile: PlayerProfile, guideMode: SlimefunGuideMode) = false

    override fun open(p: Player, profile: PlayerProfile, mode: SlimefunGuideMode) {
        AppraiseInfoMenu(name, { type: AppraiseType ->
            // open the detail menu
            AppraiseTypeMenu(type) { open(p, profile, mode) }.open(p)
        }, {
            // back to main menu
            SlimefunGuide.openItemGroup(profile, BumpItemGroups.MAIN, mode, 1)
        }).open(p)
    }
}
