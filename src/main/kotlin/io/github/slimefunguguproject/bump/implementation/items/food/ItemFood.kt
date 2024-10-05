package io.github.slimefunguguproject.bump.implementation.items.food

import io.github.slimefunguguproject.bump.Bump
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils
import net.guizhanss.guizhanlib.common.Cooldown
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID


abstract class ItemFood(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>
) : UnplaceableBlock(itemGroup, itemStack, recipeType, recipe) {

    private val cooldown = Cooldown<UUID>()

    override fun getItemHandler() = ItemUseHandler { e: PlayerRightClickEvent ->
        e.cancel()
        val p = e.player

        if (!cooldown.check(p.uniqueId)) {
            Bump.localization.sendActionbarMessage(p, "food.cooldown")
            return@ItemUseHandler
        }

        val messageKey = id.replace(Bump.localization.idPrefix, "").lowercase()
        Bump.localization.sendActionbarMessage(p, "food.$messageKey")

        if (p.gameMode != GameMode.CREATIVE) {
            ItemUtils.consumeItem(e.item, false)
        }

        object : BukkitRunnable() {
            var count = 7

            override fun run() {
                if (count > 0) {
                    p.playSound(p.location, Sound.ENTITY_GENERIC_EAT, 1f, 1f)
                    count--
                } else {
                    applyFoodEffects(p)
                    cancel()
                }
            }
        }.runTaskTimer(Bump.instance, 1L, 4L)

        cooldown[p.uniqueId] = 2000L
    }

    abstract fun applyFoodEffects(p: Player)
}
