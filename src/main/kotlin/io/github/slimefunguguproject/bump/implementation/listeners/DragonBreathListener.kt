package io.github.slimefunguguproject.bump.implementation.listeners

import io.github.slimefunguguproject.bump.Bump
import org.bukkit.Particle
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class DragonBreathListener(plugin: Bump) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onPlayerDamaged(e: EntityDamageByEntityEvent) {
        val damager = e.damager
        if (damager is AreaEffectCloud
            && damager.particle == Particle.DRAGON_BREATH
            && damager.source is Player
            && e.entity is Player
            && (damager.source as Player).uniqueId == e.entity.uniqueId
        ) {
            e.isCancelled = true
        }
    }
}
