package io.github.slimefunguguproject.bump.utils.constant

import io.github.slimefunguguproject.bump.Bump
import org.bukkit.NamespacedKey
import java.util.Locale

object Keys {

    val APPRAISABLE = "appraisable".createKey()
    val APPRAISE_LEVEL = "appraise_level".createKey()
    val APPRAISE_VERSION = "appraise_version".createKey()
    val LAST_USED = "last_used".createKey()
    val HEAVEN_BREAKING_SWORD_PROTECTED = "heaven_breaking_sword_protected".createKey()
    val PROJECTILE = "projectile".createKey()

    fun String.createKey() = NamespacedKey(Bump.instance, this.lowercase(Locale.getDefault()))
}
