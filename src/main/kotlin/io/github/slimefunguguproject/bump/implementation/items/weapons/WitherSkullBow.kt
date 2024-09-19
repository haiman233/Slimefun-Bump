package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.handlers.BowUseHandler
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.implementation.tasks.WeaponProjectileTask
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import org.bukkit.entity.Player
import org.bukkit.entity.WitherSkull
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.inventory.ItemStack

class WitherSkullBow(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    hunger: Int,
) : BumpBow(itemGroup, itemStack, recipeType, recipe, hunger) {

    override fun getItemHandler() = BowUseHandler { e: EntityShootBowEvent, p: Player, item: ItemStack ->
        e.isCancelled = true
        if (costHunger(p)) {
            damageItem(p, item)

            Bump.localization.sendActionbarMessage(p, "weapon.wither_skull_bow")

            BumpSound.WITHER_SKULL_BOW_USE.playFor(p)

            val projectile = p.launchProjectile(WitherSkull::class.java)
            WeaponProjectileTask.track(projectile)
        } else {
            Bump.localization.sendActionbarMessage(p, "weapon.low-food-level")
        }
    }
}
