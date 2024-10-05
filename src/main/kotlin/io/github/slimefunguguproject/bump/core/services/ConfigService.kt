package io.github.slimefunguguproject.bump.core.services

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.utils.ConfigUtils
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig
import org.bukkit.configuration.ConfigurationSection

class ConfigService(private val plugin: Bump) {

    private var config: AddonConfig = AddonConfig(plugin, "config.yml")
    private var appraiseConfig: Config = Config(plugin, "appraise.yml")

    var autoUpdate = true
        private set
    var debug = false
        private set
    var lang = "en"
        private set
    var enableResearches = true
        private set
    var weaponProjectileDuration = 10
        private set
    var appraiserBroadcastEnabled = true
        private set
    var appraiserBroadcastStarRequirement = 7
        private set
    var appraiseStars: Map<Byte, Byte> = mapOf()
        private set
    var appraiseTypes: ConfigurationSection? = null
        private set

    init {
        reload()
    }

    fun reload() {
        config.reload()
        config.addMissingKeys()
        ConfigUtils.saveDefaultFile(plugin, "appraise.yml")
        appraiseConfig.reload()

        // config.yml
        autoUpdate = config.getBoolean("auto-update", true)
        debug = config.getBoolean("debug", false)
        lang = config.getString("lang", "en")!!
        enableResearches = config.getBoolean("enable-researches", true)
        weaponProjectileDuration = config.getInt("weapons.projectile-duration", 10)
        appraiserBroadcastEnabled = config.getBoolean("appraiser.broadcast.enabled", true)
        appraiserBroadcastStarRequirement = config.getInt("appraiser.broadcast.star-requirement", 7)

        // appraise.yml
        appraiseTypes = appraiseConfig.configuration.getConfigurationSection("types")

        val starsSection = appraiseConfig.configuration.getConfigurationSection("stars")
        if (starsSection != null) {
            val stars = mutableMapOf<Byte, Byte>()
            for (key in starsSection.getKeys(false)) {
                if (key.toByteOrNull() == null) continue
                stars[key.toByte()] = starsSection.getInt(key).toByte()
            }
            appraiseStars = stars
        } else {
            appraiseStars = mapOf()
        }

        config.save()
    }
}
