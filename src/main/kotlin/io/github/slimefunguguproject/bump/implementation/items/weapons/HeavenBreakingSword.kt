package io.github.slimefunguguproject.bump.implementation.items.weapons

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.core.services.sounds.BumpSound
import io.github.slimefunguguproject.bump.utils.constant.Keys
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import net.guizhanss.guizhanlib.minecraft.utils.compatibility.ParticleX
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

/**
 * [Heaven Breaking Sword][HeavenBreakingSword] will lift player up to the sky when using.
 *
 * @author ybw0014
 */
class HeavenBreakingSword(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
    hunger: Int,
) : BumpSword(itemGroup, itemStack, recipeType, recipe, hunger) {

    override fun onItemUse(p: Player, sword: ItemStack) {
        Bump.localization.sendActionbarMessage(p, "weapon.heaven_breaking_sword.activated")

        // directly up
        p.velocity = Vector(0, 3, 0)

        BumpSound.HEAVEN_BREAKING_SWORD_USE.playFor(p)
        for (i in 0..19) {
            p.spawnParticle(ParticleX.EXPLOSION_EMITTER, p.location, 1)
        }
        PersistentDataAPI.setBoolean(p, Keys.HEAVEN_BREAKING_SWORD_PROTECTED, true)
    }
}
