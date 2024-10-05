package io.github.slimefunguguproject.bump.utils.constant

import java.util.regex.Pattern

object Patterns {

    val MINECRAFT_NAMESPACEDKEY: Pattern = Pattern.compile("minecraft:[a-z0-9/._-]+")
    val BUMP_TAG: Pattern = Pattern.compile("#bump:[a-z_]+")
    val BUMP_TAG_CONFIG: Pattern = Pattern.compile("bump:[a-z_]+")
}
