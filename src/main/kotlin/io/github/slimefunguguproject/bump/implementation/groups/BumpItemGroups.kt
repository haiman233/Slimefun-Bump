@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.implementation.groups

import dev.sefiraat.sefilib.slimefun.itemgroup.DummyItemGroup
import dev.sefiraat.sefilib.slimefun.itemgroup.MenuItem
import dev.sefiraat.sefilib.slimefun.itemgroup.SimpleFlexGroup
import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.constant.Keys.createKey
import io.github.slimefunguguproject.bump.utils.constant.Strings
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object BumpItemGroups {

    val MAIN: SimpleFlexGroup = SimpleFlexGroup(
        Bump.instance,
        Bump.localization.getItemGroupName("main"),
        "main".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.DIAMOND),
            "main"
        )
    )

    val WIKI: ItemStack = Bump.localization.getItemGroupItem(
        MaterialType.Material(Material.KNOWLEDGE_BOOK),
        "wiki"
    )

    val MATERIALS: ItemGroup = DummyItemGroup(
        "materials".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.NETHER_STAR),
            "materials"
        )
    )

    val FOOD: ItemGroup = DummyItemGroup(
        "food".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.BREAD),
            "food"
        )
    )

    val TOOLS: ItemGroup = DummyItemGroup(
        "tools".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.DIAMOND_PICKAXE),
            "tools"
        )
    )

    val MACHINES: ItemGroup = DummyItemGroup(
        "machines".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.ANVIL),
            "machines"
        )
    )

    val ARMOR: ItemGroup = DummyItemGroup(
        "armor".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.DIAMOND_HELMET),
            "armor"
        )
    )

    val WEAPONS: ItemGroup = DummyItemGroup(
        "weapons".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.DIAMOND_SWORD),
            "weapons"
        )
    )

    val APPRAISE_INFO: AppraiseInfoGroup = AppraiseInfoGroup(
        Bump.localization.getItemGroupName("appraise_info"),
        "appraise_info".createKey(),
        Bump.localization.getItemGroupItem(
            MaterialType.Material(Material.NAME_TAG),
            "appraise_info"
        )
    )

    val HIDDEN: ItemGroup = DummyItemGroup(
        "hidden".createKey(),
        CustomItemStack(
            Material.BARRIER,
            "&bBump - Hidden/Legacy Items"
        )
    )

    fun setup() {
        val addon = Bump.instance
        val wikiMenuItem = MenuItem(WIKI) { p: Player, _, _, _ ->
            p.closeInventory()
            p.sendMessage("")
            Bump.localization.sendMessage(p, "click-here")

            val lang = Bump.configService.lang
            if (lang.startsWith("zh")) {
                p.sendMessage(ChatColor.GRAY.toString() + Strings.WIKI_CN)
            } else {
                p.sendMessage(ChatColor.GRAY.toString() + Strings.WIKI_EN)
            }

            p.sendMessage("")
            false
        }

        MAIN.addMenuItem(wikiMenuItem)
        MAIN.addItemGroup(MATERIALS)
        MAIN.addItemGroup(FOOD)
        MAIN.addItemGroup(TOOLS)
        MAIN.addItemGroup(MACHINES)
        MAIN.addItemGroup(ARMOR)
        MAIN.addItemGroup(WEAPONS)
        MAIN.addItemGroup(APPRAISE_INFO)

        MAIN.register(addon)

        MATERIALS.register(addon)
        FOOD.register(addon)
        MACHINES.register(addon)
        TOOLS.register(addon)
        ARMOR.register(addon)
        WEAPONS.register(addon)
        APPRAISE_INFO.register(addon)
        HIDDEN.register(addon)
    }
}
