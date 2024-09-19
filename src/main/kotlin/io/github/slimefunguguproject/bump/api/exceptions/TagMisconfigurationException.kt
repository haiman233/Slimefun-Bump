package io.github.slimefunguguproject.bump.api.exceptions

import io.github.slimefunguguproject.bump.utils.tags.BumpTag
import org.bukkit.NamespacedKey
import javax.annotation.ParametersAreNonnullByDefault

class TagMisconfigurationException : Exception {

    /**
     * This constructs a new [TagMisconfigurationException] for the given
     * [BumpTag]'s [NamespacedKey] with the provided context.
     *
     * @param key     The [NamespacedKey] of [BumpTag]
     * @param message The message to display
     */
    constructor(key: NamespacedKey, message: String)
        : super("Tag '$key' has been misconfigured: $message")

    /**
     * This constructs a new [TagMisconfigurationException] for the given
     * [BumpTag]'s [NamespacedKey] with the provided context.
     *
     * @param key   The [NamespacedKey] of [BumpTag]
     * @param cause The [Throwable] which has caused this to happen
     */
    @ParametersAreNonnullByDefault
    constructor(key: NamespacedKey, cause: Throwable)
        : super("Tag '" + key + "' has been misconfigured (" + cause.message + ')', cause)


    companion object {

        private val serialVersionUID = 1145141919810L
    }
}
