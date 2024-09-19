package io.github.slimefunguguproject.bump.core

import io.github.slimefunguguproject.bump.api.appraise.AppraiseType
import org.bukkit.NamespacedKey

object BumpRegistry {

    val appraiseTypeKeys: MutableMap<NamespacedKey, AppraiseType> = mutableMapOf()
    val appraiseTypes: MutableSet<AppraiseType> = mutableSetOf()
    val starThresholds: MutableMap<Byte, Byte> = mutableMapOf()
}
