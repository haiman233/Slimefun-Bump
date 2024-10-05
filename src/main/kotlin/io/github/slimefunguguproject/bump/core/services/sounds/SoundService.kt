package io.github.slimefunguguproject.bump.core.services.sounds

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.GeneralUtils.valueOfOrNull
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig
import org.bukkit.Sound
import java.util.logging.Level
import kotlin.math.max

class SoundService(private val config: AddonConfig) {

    private val soundMap: MutableMap<BumpSound, SoundConfig> = mutableMapOf()

    /**
     * Load the configurations of all [BumpSound].
     *
     * @param saveConfig Whether to save config after loading.
     */
    fun load(saveConfig: Boolean) {
        config.reload()

        BumpSound.entries.forEach {
            try {
                loadSound(it)
            } catch (ex: Exception) {
                Bump.log(Level.SEVERE, ex, "An error occurred while loading sound config for ${it.name}.")
            }
        }

        if (saveConfig) {
            config.save()
        }
    }

    /**
     * Get the [SoundConfig] for a specified [BumpSound].
     *
     * @param sound The [BumpSound] to get [SoundConfig] for.
     *
     * @return The [SoundConfig] for the [BumpSound]. `null` if not exists.
     */
    internal fun getSoundConfig(sound: BumpSound): SoundConfig? {
        return soundMap[sound]
    }

    private fun loadSound(sound: BumpSound) {
        setDefault(sound.name + ".sound", sound.sound.name)
        setDefault(sound.name + ".volume", sound.volume)
        setDefault(sound.name + ".pitch", sound.pitch)

        val soundId = config.getString(sound.name + ".sound") ?: sound.sound.name
        val soundEnum = valueOfOrNull<Sound>(soundId) ?: sound.sound
        val volume = max(config.getDouble(sound.name + ".volume"), 0.0)
        val pitch = max(config.getDouble(sound.name + ".pitch"), 0.5)

        val soundConfig = SoundConfig(soundEnum, volume.toFloat(), pitch.toFloat())
        soundMap[sound] = soundConfig
    }

    private fun setDefault(path: String, value: Any) {
        if (!config.contains(path)) {
            config[path] = value
        }
    }
}
