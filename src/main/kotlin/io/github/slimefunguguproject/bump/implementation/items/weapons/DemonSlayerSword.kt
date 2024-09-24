package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.implementation.tasks.WeaponProjectileTask
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import net.guizhanss.guizhanlib.minecraft.utils.compatibility.ParticleX
import org.bukkit.entity.Player
import org.bukkit.entity.SmallFireball
import org.bukkit.inventory.ItemStack

class DemonSlayerSword(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    hunger: Int,
) : BumpSword(itemGroup, itemStack, recipeType, recipe, hunger) {

    override fun onItemUse(p: Player, sword: ItemStack) {
        Bump.localization.sendActionbarMessage(p, "weapon.demon_slayer_sword")

        BumpSound.DEMON_SLAYER_SWORD_USE.playFor(p)

        for (i in 0..19) {
            val projectile = p.launchProjectile(SmallFireball::class.java)
            WeaponProjectileTask.track(projectile)
            p.spawnParticle(ParticleX.ENCHANT, p.location, 1)
        }
    }
}
