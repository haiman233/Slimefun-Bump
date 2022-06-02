package bxx2004.bump.slimefun.items.food;

import bxx2004.bump.abstracts.ConsumableFood;
import bxx2004.bump.slimefun.BumpItems;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Sprite.
 *
 * @author ybw0014
 */
public class Zongzi extends ConsumableFood {

    public Xuebi() {
        super(BumpItems.ZONGZI, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            new ItemStack(Material.LILY_PAD), new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.LILY_PAD), 
            new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.WHEAT), new ItemStack(Material.ACACIA_LEAVES), 
            new ItemStack(Material.LILY_PAD), new ItemStack(Material.ACACIA_LEAVES), new ItemStack(Material.LILY_PAD)
        });
    }

    @Override
    public void applyFoodEffects(Player p) {
        p.setFoodLevel(8);
        p.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 500, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 8000, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 2000, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 10000, 3));
        p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 5000, 5));
    }
}