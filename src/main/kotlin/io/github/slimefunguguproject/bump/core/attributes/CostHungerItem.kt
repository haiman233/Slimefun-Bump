package io.github.slimefunguguproject.bump.core.attributes

import io.github.slimefunguguproject.bump.utils.FoodLevelUtils
import io.github.thebusybiscuit.slimefun4.core.attributes.ItemAttribute
import org.bukkit.GameMode
import org.bukkit.entity.Player

interface CostHungerItem : ItemAttribute {

    /**
     * This variable returns the hunger cost of the item.
     */
    fun getHungerCost(): Int

    /**
     * This method will check whether [Player]'s food level is sufficient to cost.
     *
     * @param p the [Player] that uses the item
     *
     * @return if player has enough hunger
     */
    fun checkHunger(p: Player): Boolean {
        return if (p.gameMode != GameMode.CREATIVE) {
            p.foodLevel >= getHungerCost()
        } else {
            true
        }
    }

    /**
     * This method will cost hunger of a [Player].
     *
     * @param p the [Player] that uses the item
     *
     * @return if player has reduced enough hunger
     */
    fun costHunger(p: Player): Boolean {
        return if (checkHunger(p)) {
            FoodLevelUtils.set(p, p.foodLevel - getHungerCost())
        } else {
            false
        }
    }
}
