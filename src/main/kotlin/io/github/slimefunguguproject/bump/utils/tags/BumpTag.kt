package io.github.slimefunguguproject.bump.utils.tags

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.api.exceptions.TagMisconfigurationException
import io.github.slimefunguguproject.bump.utils.constant.Keys.createKey
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import java.util.logging.Level

enum class BumpTag : Tag<Material> {
    /**
     * This includes all swords.
     */
    SWORD,

    /**
     * This includes all helmets.
     */
    HELMET,

    /**
     * This includes all chestplates.
     */
    CHESTPLATE,

    /**
     * This includes all leggings.
     */
    LEGGINGS,

    /**
     * This includes all boots.
     */
    BOOTS,

    /**
     * This includes all horse armors.
     */
    HORSE_ARMOR,

    /**
     * This includes all axes.
     */
    AXE,

    /**
     * This includes all pickaxes.
     */
    PICKAXE,

    /**
     * This includes all shovels.
     */
    SHOVEL,

    /**
     * This includes all hoes.
     */
    HOE,

    /**
     * This includes all fishing rods.
     */
    FISHING_ROD,

    /**
     * This includes all bows.
     */
    BOW,

    /**
     * This includes all kinds of heads.
     */
    HEAD,

    /**
     * This includes all carpets.
     */
    CARPET,

    /**
     * This includes all materials that is suitable for main hand slot.
     */
    HAND_SLOT,

    /**
     * This includes all materials that is suitable for off hand slot.
     */
    OFF_HAND_SLOT,

    /**
     * This includes all materials that is suitable for head slot.
     */
    HEAD_SLOT,

    /**
     * This includes all materials that is suitable for chest slot.
     */
    CHEST_SLOT,

    /**
     * This includes all materials that is suitable for legs slot.
     */
    LEGS_SLOT,

    /**
     * This includes all materials that is suitable for feet slot.
     */
    FEET_SLOT;

    private var key: NamespacedKey = name.createKey()
    private val materials: MutableSet<Material> = HashSet()
    private val additionalTags: MutableSet<Tag<Material>> = HashSet()

    init {
        this.key = name.createKey()
    }

    companion object {

        /**
         * Get a tag by the name. null if not found.
         */
        fun getTag(name: String) = entries.firstOrNull { it.name == name }

        /**
         * Reload all tags.
         */
        fun reloadAll() {
            for (tag in entries) {
                tag.reload()
            }
        }
    }

    /**
     * Reload this [BumpTag] from resources.
     */
    fun reload() {
        try {
            TagParser(this).parse { materialSet: Set<Material>, additionalTagSet: Set<Tag<Material>> ->
                materials.clear()
                materials.addAll(materialSet)

                additionalTags.clear()
                additionalTags.addAll(additionalTagSet)
            }
        } catch (ex: TagMisconfigurationException) {
            Bump.log(
                Level.SEVERE, ex,
                "An error has occurred while trying to load Bump tag: $name"
            )
        }
    }

    override fun getKey() = key

    override fun isTagged(material: Material): Boolean {
        return if (materials.contains(material)) {
            true
        } else {
            return additionalTags.any { it.isTagged(material) }
        }
    }

    override fun getValues(): Set<Material> {
        return if (additionalTags.isEmpty()) {
            materials.toSet()
        } else {
            materials.union(additionalTags.flatMap { it.values }).toSet()
        }
    }

    fun isEmpty() = materials.isEmpty() && values.isEmpty()
}
