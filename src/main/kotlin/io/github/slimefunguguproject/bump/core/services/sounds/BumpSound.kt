package io.github.slimefunguguproject.bump.core.services.sounds

import io.github.slimefunguguproject.bump.Bump
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.logging.Level

/**
 * This enum contains almost all configurable sounds in Bump.
 */
enum class BumpSound(val sound: Sound, val volume: Float, val pitch: Float) {

    APPRAISER_FAIL(Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f),
    APPRAISER_SUCCEED(Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f),
    APPRAISE_TYPE_SELECT(Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f),
    APPRAISE_TYPE_SELECTOR_OPEN(Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f),
    ATTRIBUTE_GRINDSTONE_FAIL(Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f),
    ATTRIBUTE_GRINDSTONE_SUCCEED(Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f),
    QUALITY_IDENTIFIER_OPEN(Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f),
    QUALITY_IDENTIFIER_FAIL(Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f),
    QUALITY_IDENTIFIER_SUCCEED(Sound.ENTITY_VILLAGER_CELEBRATE, 1.0f, 1.0f),
    WITHER_SKULL_BOW_USE(Sound.ENTITY_WITHER_SHOOT, 1.0f, 1.0f),
    SOUL_SWORD_USE(Sound.AMBIENT_CAVE, 1.0f, 1.0f),
    HEAVEN_BREAKING_SWORD_USE(Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f),
    DEMON_SLAYER_SWORD_USE(Sound.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f),
    HEAVEN_BREAKING_DEMON_SLAYER_SWORD_USE(Sound.ENTITY_ENDER_DRAGON_SHOOT, 1.0f, 1.0f),
    ;

    private fun getSoundConfig(): SoundConfig? {
        val config = Bump.soundService.getSoundConfig(this)

        if (config == null) {
            Bump.log(Level.WARNING, "Invalid sound config: $name")
        }

        return config
    }

    /**
     * Play this [BumpSound] at [Player]'s eye location.
     *
     * @param p The [Player] which to play the [BumpSound] to.
     */
    fun playFor(p: Player) {
        val config = getSoundConfig()

        if (config != null) {
            p.playSound(p.eyeLocation, config.sound, SoundCategory.PLAYERS, config.volume, config.pitch)
        }
    }

    /**
     * Play this [BumpSound] at the given [Location] using the
     * provided [SoundCategory].
     *
     * @param loc      The [Location] at which to play the [BumpSound].
     * @param category The [SoundCategory] that should be used.
     */
    fun playAt(loc: Location, category: SoundCategory) {
        val config = getSoundConfig()

        if (config != null) {
            loc.world?.playSound(loc, config.sound, category, config.volume, config.pitch)
        }
    }

    /**
     * Play this [BumpSound] at the given [Block].
     *
     * @param block The [Block] at which to play the [BumpSound].
     */
    fun playAt(block: Block) {
        playAt(block.location, SoundCategory.BLOCKS)
    }
}
