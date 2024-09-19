package io.github.slimefunguguproject.bump.utils

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.entity.FoodLevelChangeEvent
import kotlin.math.min

object FoodLevelUtils {

    /**
     * This method calls [FoodLevelChangeEvent] and add [Player]'s
     * food level if [Player]'s [GameMode] is not creative/spectator and
     * the event is not cancelled
     *
     * @param p     the [Player] that food level will be changed
     * @param level the level to be added
     *
     * @return if the food level is added
     */
    fun add(p: Player, level: Int): Boolean {
        val newLevel = min(20, p.foodLevel + level)
        return set(p, newLevel)
    }

    /**
     * This method calls [FoodLevelChangeEvent] and set [Player]'s
     * food level if [Player]'s [GameMode] is not creative/spectator and
     * the event is not cancelled
     *
     * @param p     the [Player] that food level will be changed
     * @param level the target food level
     *
     * @return if the food level is changed
     */
    fun set(p: Player, level: Int): Boolean {
        if (p.gameMode == GameMode.CREATIVE || p.gameMode == GameMode.SPECTATOR) {
            return true
        }

        val event = FoodLevelChangeEvent(p, level)
        Bukkit.getPluginManager().callEvent(event)

        return if (!event.isCancelled) {
            p.foodLevel = event.foodLevel
            true
        } else {
            false
        }
    }
}
