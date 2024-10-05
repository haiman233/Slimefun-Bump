@file:Suppress("deprecation")

package io.github.slimefunguguproject.bump.utils.items

import io.github.slimefunguguproject.bump.utils.general.RequiredProperty
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil
import net.guizhanss.guizhanlib.minecraft.utils.compatibility.ItemFlagX
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class DisplayItemBuilder {

    var material: MaterialType by RequiredProperty()
    var name: String by RequiredProperty()
    var lore: List<String> by RequiredProperty()

    var postCreate: (ItemStack) -> Unit = {}

    fun build(): ItemStack {
        val item = material.convert()
        val meta = item.itemMeta!!
        meta.setDisplayName(ChatUtil.color(name))
        meta.lore = lore.map { ChatUtil.color(it) }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlagX.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ENCHANTS)
        item.itemMeta = meta
        postCreate(item)
        return item
    }
}

inline fun buildDisplayItem(
    builder: DisplayItemBuilder.() -> Unit
): ItemStack {
    return DisplayItemBuilder().apply(builder).build()
}

