package io.github.slimefunguguproject.bump

import io.github.slimefunguguproject.bump.core.services.ConfigService
import io.github.slimefunguguproject.bump.core.services.IntegrationService
import io.github.slimefunguguproject.bump.core.services.ListenerService
import io.github.slimefunguguproject.bump.core.services.LocalizationService
import io.github.slimefunguguproject.bump.core.services.sounds.SoundService
import io.github.slimefunguguproject.bump.implementation.BumpItems
import io.github.slimefunguguproject.bump.implementation.groups.BumpItemGroups
import io.github.slimefunguguproject.bump.implementation.setup.AppraiseSetup
import io.github.slimefunguguproject.bump.implementation.setup.ResearchSetup
import io.github.slimefunguguproject.bump.implementation.tasks.WeaponProjectileTask
import io.github.slimefunguguproject.bump.utils.WikiUtils
import io.github.slimefunguguproject.bump.utils.tags.BumpTag
import net.byteflux.libby.BukkitLibraryManager
import net.byteflux.libby.Library
import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig
import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
import org.bukkit.Bukkit
import java.util.logging.Level

class Bump : AbstractAddon(
    "SlimefunGuguProject", "Bump", "main", "options.auto-update"
) {

    override fun load() {
        // check if there is central repo prop defined
        val centralRepo = System.getProperty("centralRepository") ?: "https://repo1.maven.org/maven2/"

        logger.info("Loading libraries, please wait...")
        logger.info("If you stuck here for a long time (>30s), try to specify a mirror repository by adding -DcentralRepository=<url> to your JVM arguments.")

        // download libs
        val manager = BukkitLibraryManager(this, "libraries")
        manager.addRepository(centralRepo)
        manager.loadLibrary(
            Library.builder().groupId("org.jetbrains.kotlin").artifactId("kotlin-stdlib").version("2.0.20").build()
        )
        manager.loadLibrary(
            Library.builder().groupId("org.jetbrains.kotlin").artifactId("kotlin-reflect").version("2.0.20").build()
        )

        logger.info("Loaded all required libraries.")
    }

    override fun enable() {
        instance = this

        sendConsole("&6&l____                        ")
        sendConsole("&6&l |  _ \\                       ")
        sendConsole("&6&l | |_) |_   _ _ __ ___  _ __  ")
        sendConsole("&6&l |  _ <| | | | '_ ` _ \\| '_ \\ ")
        sendConsole("&6&l | |_) | |_| | | | | | | |_) |")
        sendConsole("&6&l |____/ \\__,_|_| |_| |_| .__/ ")
        sendConsole("&6&l                       | |    ")
        sendConsole("&6&l                       |_|    ")


        sendConsole("&a&l  Bump 3 for Slimefun4 RC-37+")
        sendConsole("&a&l  Powered By bxx2004, SlimefunGuguProject")
        sendConsole("&a&l  GitHub: https://github.com/SlimefunGuguProject/Bump")
        sendConsole("&a&l  Issues: https://github.com/SlimefunGuguProject/Bump/issues")

        // config
        configService = ConfigService(this)

        // localization
        log(Level.INFO, "Loading language...")
        val lang = configService.lang
        localization = LocalizationService(this, file)
        localization.idPrefix = "BUMP_"
        localization.addLanguage(lang)
        if (lang != DEFAULT_LANG) {
            localization.addLanguage(DEFAULT_LANG)
        }
        log(Level.INFO, "Loaded language {0}.", lang)

        // tags
        BumpTag.reloadAll()

        // sound service
        soundService = SoundService(AddonConfig("sounds.yml"))
        soundService.load(true)

        // appraise setup
        AppraiseSetup.setup()

        // item groups setup
        BumpItemGroups.setup()

        // item setup
        BumpItems

        // researches setup
        if (configService.enableResearches) {
            ResearchSetup.setup()
        }

        // wiki setup
        WikiUtils.setupJson()

        // listeners
        ListenerService(this)

        // tasks
        WeaponProjectileTask.start()

        // Metrics setup
        setupMetrics()
    }

    override fun disable() {
        Bukkit.getScheduler().cancelTasks(this)
    }

    private fun setupMetrics() {
        val metrics = Metrics(this, 14870)
        metrics.addCustomChart(SimplePie("server_language") { configService.lang })
        metrics.addCustomChart(SimplePie("enable_research") { if (configService.enableResearches) "enabled" else "disabled" })
    }

    companion object {

        const val DEFAULT_LANG = "en"

        lateinit var instance: Bump
            private set
        lateinit var configService: ConfigService
            private set
        lateinit var localization: LocalizationService
            private set
        lateinit var soundService: SoundService
            private set
        lateinit var integrationService: IntegrationService
            private set

        fun scheduler() = getScheduler()

        fun sfTickCount() = getSlimefunTickCount()

        fun log(level: Level, message: String) {
            instance.logger.log(level, message)
        }

        fun log(level: Level, ex: Throwable, message: String) {
            instance.logger.log(level, ex) { message }
        }

        fun debug(message: String) {
            if (!Companion::configService.isInitialized || !configService.debug) {
                return
            }
            log(Level.INFO, "[DEBUG] $message")
        }
    }
}
