package io.github.slimefunguguproject.bump.implementation.listeners

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.constant.Keys
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import org.bukkit.entity.Monster
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class HeavenBreakingSwordListener(plugin: Bump) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onPlayerHitGround(e: EntityDamageEvent) {
        val p = e.entity
        if (p !is Player) return
        if (e.cause == EntityDamageEvent.DamageCause.FALL
            && PersistentDataAPI.getBoolean(p, Keys.HEAVEN_BREAKING_SWORD_PROTECTED)
        ) {
            // cancel fall damage
            e.isCancelled = true
            Bump.localization.sendActionbarMessage(p, "weapon.sky_sword.protected")
            PersistentDataAPI.setBoolean(p, Keys.HEAVEN_BREAKING_SWORD_PROTECTED, false)

            // distribute damage evenly to nearby hostiles
            val damage = e.damage
            if (damage <= 0) return
            val nearbyHostiles = p.world.getNearbyEntities(p.location, 5.0, 5.0, 5.0) { it is Monster }
            if (nearbyHostiles.isEmpty()) return
            val damagePerHostile = damage / nearbyHostiles.size
            nearbyHostiles.forEach { (it as Monster).damage(damagePerHostile, p) }
        }
    }
}
