package io.github.slimefunguguproject.bump.core.services

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.implementation.listeners.BowShootListener
import io.github.slimefunguguproject.bump.implementation.listeners.DragonBreathListener
import io.github.slimefunguguproject.bump.implementation.listeners.HeavenBreakingSwordListener

class ListenerService(plugin: Bump) {
    init {
        BowShootListener(plugin)
        DragonBreathListener(plugin)
        HeavenBreakingSwordListener(plugin)
    }
}
