package bxx2004.bump.interfaces;

import bxx2004.bump.util.Utils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * This interface indicates that the item will cost hunger when use.
 *
 * @author ybw0014
 */
public interface CostHungerItem {
    /**
     * This method returns the {@link ItemSetting} of hunger cost.
     *
     * @return the {@link ItemSetting} of hunger cost
     */
    ItemSetting<Integer> getHungerCostSetting();

    /**
     * This method returns the hunger cost from {@link ItemSetting}.
     *
     * @return the hunger cost
     */
    default int getHungerCost() {
        return getHungerCostSetting().getValue();
    }

    /**
     * This method will check whether {@link Player}'s food level is sufficient to cost.
     *
     * @param p the {@link Player} that uses the item
     *
     * @return if player has enough hunger
     */
    default boolean checkHunger(Player p) {
        if (p.getGameMode() != GameMode.CREATIVE) {
            return p.getFoodLevel() >= getHungerCost();
        } else {
            return true;
        }
    }

    /**
     * This method will cost hunger of a {@link Player}.
     *
     * It will call {@link FoodLevelChangeEvent} and cost {@link Player}'s
     * hunger if {@link Player}'s {@link GameMode} is not creative and
     * the event is not cancelled.
     *
     * @param p the {@link Player} that uses the item
     *
     * @return if player has reduced enough hunger
     */
    default boolean costHunger(Player p) {
        if (checkHunger(p)) {
            return Utils.changeFoodLevel(p,p.getFoodLevel() - getHungerCost());
        } else {
            return false;
        }
    }
}