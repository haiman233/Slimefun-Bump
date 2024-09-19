package io.github.slimefunguguproject.bump.utils

import io.github.slimefunguguproject.bump.Bump
import org.bukkit.plugin.Plugin
import java.util.logging.Level

object WikiUtils {

    fun setupJson() {
        try {
            val clazz = Class.forName("net.guizhanss.slimefun4.utils.WikiUtils")
            clazz.getMethod("setupJson", Plugin::class.java).invoke(null, Bump.instance)
        } catch (e: Exception) {
            Bump.log(
                Level.WARNING,
                "Cannot load wiki pages. You can safely ignore this message if you are using Official Slimefun DEV/RC version."
            )
        }
    }
}
