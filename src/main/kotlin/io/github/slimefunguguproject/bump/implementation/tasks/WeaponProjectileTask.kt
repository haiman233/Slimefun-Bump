package io.github.slimefunguguproject.bump.implementation.tasks

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.constant.Keys
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import org.bukkit.entity.Projectile

object WeaponProjectileTask : Runnable {

    private val projectileMap: MutableMap<Projectile, Int> = HashMap()

    /**
     * This method starts this task
     */
    fun start() {
        val duration = Bump.configService.weaponProjectileDuration
        if (duration > 0) {
            Bump.scheduler().repeat(Slimefun.getTickerTask().tickRate, this)
        }
    }

    /**
     * This method will add [Projectile] to tracking list.
     *
     * @param projectile the [Projectile] to be added.
     */
    fun track(projectile: Projectile) {
        projectileMap[projectile] = Bump.sfTickCount()
        PersistentDataAPI.setBoolean(projectile, Keys.PROJECTILE, true)
    }

    override fun run() {
        val currentTick = Bump.sfTickCount()

        val it = projectileMap.entries.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (entry.value + Bump.configService.weaponProjectileDuration < currentTick) {
                val projectile = entry.key
                if (projectile.isValid) {
                    projectile.remove()
                }
                it.remove()
            }
        }
    }


}

