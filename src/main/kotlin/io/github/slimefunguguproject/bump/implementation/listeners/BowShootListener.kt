package io.github.slimefunguguproject.bump.implementation.listeners

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.handlers.BowUseHandler
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent

class BowShootListener(plugin: Bump) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onBowUse(e: EntityShootBowEvent) {
        if (e.entity is Player && e.projectile is Arrow) {
            val bow = SlimefunItem.getByItem(e.bow)

            bow?.callItemHandler(BowUseHandler::class.java) { handler: BowUseHandler ->
                handler.onUse(e, e.entity as Player, e.bow!!)
            }
        }
    }
}
