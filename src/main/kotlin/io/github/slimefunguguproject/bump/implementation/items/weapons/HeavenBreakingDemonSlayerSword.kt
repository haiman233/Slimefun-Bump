package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.implementation.tasks.WeaponProjectileTask
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import net.guizhanss.guizhanlib.minecraft.utils.compatibility.PotionEffectTypeX
import org.bukkit.entity.DragonFireball
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

class HeavenBreakingDemonSlayerSword(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    hunger: Int,
) : BumpSword(itemGroup, itemStack, recipeType, recipe, hunger) {

    override fun onItemUse(p: Player, sword: ItemStack) {
        Bump.localization.sendActionbarMessage(p, "weapon.heaven_breaking_demon_slayer_sword.activated")

        p.isGlowing = true
        p.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 300, 3))
        p.addPotionEffect(PotionEffect(PotionEffectTypeX.RESISTANCE, 300, 3))

        object : BukkitRunnable() {
            var count: Int = 3

            override fun run() {
                if (count > 0) {
                    BumpSound.HEAVEN_BREAKING_DEMON_SLAYER_SWORD_USE.playFor(p)
                    val projectile = p.launchProjectile(DragonFireball::class.java)
                    WeaponProjectileTask.track(projectile)
                    count--
                } else {
                    cancel()
                    p.isGlowing = false
                    Bump.localization.sendActionbarMessage(p, "weapon.heaven_breaking_demon_slayer_sword.deactivated")
                }
            }
        }.runTaskTimer(Bump.instance, 1L, 100L)
    }
}
