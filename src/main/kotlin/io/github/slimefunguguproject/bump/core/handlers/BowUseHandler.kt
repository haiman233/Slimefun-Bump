package io.github.slimefunguguproject.bump.core.handlers

import io.github.thebusybiscuit.slimefun4.api.exceptions.IncompatibleItemHandlerException
import io.github.thebusybiscuit.slimefun4.api.items.ItemHandler
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.inventory.ItemStack
import java.util.Optional

fun interface BowUseHandler : ItemHandler {

    fun onUse(e: EntityShootBowEvent, p: Player, bow: ItemStack)

    override fun validate(item: SlimefunItem): Optional<IncompatibleItemHandlerException> {
        if (item.item.type != Material.BOW) {
            return Optional.of(IncompatibleItemHandlerException("Only bows can have a BowUseHandler.", item, this))
        }

        return Optional.empty()
    }

    override fun getIdentifier() = BowUseHandler::class.java
}

