package io.github.slimefunguguproject.bump.implementation.setup

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.implementation.BumpItems
import io.github.slimefunguguproject.bump.utils.constant.Keys.createKey
import io.github.thebusybiscuit.slimefun4.api.researches.Research
import org.bukkit.inventory.ItemStack

object ResearchSetup {

    private var researchId = 114514

    fun setup() {
        createResearch(
            "materials",
            25,
            BumpItems.PHOTOSYNTHETIC_ENERGY,
            BumpItems.MECHANICAL_GEAR,
            BumpItems.COMPUTER_TECH_CORE,
            BumpItems.BROKEN_CPU,
            BumpItems.CPU,
            BumpItems.ANCIENT_RUNE_SOUL,
            BumpItems.INSTANT_NOODLE_SEASONING,
            BumpItems.POP_CANDY,
            BumpItems.UPDATE_CORE,
        )

        createResearch(
            "food",
            18,
            BumpItems.SPRITE,
            BumpItems.COLA,
            BumpItems.INSTANT_NOODLE,
            BumpItems.SPICY_STRIPS,
            BumpItems.CHEWING_GUM,
            BumpItems.RICE_DUMPLING,
        )

        createResearch(
            "get_gold",
            30,
            BumpItems.GET_GOLD_SPADE,
            BumpItems.BROKEN_GOLD_COIN
        )

        createResearch(
            "appraise",
            50,
            BumpItems.QUALITY_IDENTIFIER,
            BumpItems.APPRAISER,
            BumpItems.ATTRIBUTE_GRINDSTONE,
        )

        createResearch(
            "random_equipment",
            45,
            BumpItems.RANDOM_HELMET,
            BumpItems.RANDOM_CHESTPLATE,
            BumpItems.RANDOM_LEGGINGS,
            BumpItems.RANDOM_BOOTS,
            BumpItems.RANDOM_HORSE_ARMOR,
            BumpItems.RANDOM_SWORD,
        )

        createResearch(
            "bow",
            30,
            BumpItems.LIGHTNING_BOW,
            BumpItems.WITHER_SKULL_BOW,
        )

        createResearch(
            "peach_sword",
            24,
            BumpItems.PEACH_WOOD,
            BumpItems.PEACH_WOOD_SWORD,
        )

        createResearch("normal_sword", 20, BumpItems.EMERALD_SWORD, BumpItems.BONE_SWORD, BumpItems.GUARDIAN_SWORD)

        createResearch("soul_sword", 10, BumpItems.SOUL_SWORD)

        createResearch(
            "heaven_hell",
            33,
            BumpItems.HEAVEN_BREAKING_SWORD,
            BumpItems.DEMON_SLAYER_SWORD,
            BumpItems.HEAVEN_BREAKING_DEMON_SLAYER_SWORD,
        )
    }

    private fun createResearch(key: String, cost: Int, vararg itemStacks: ItemStack) {
        Research("research_$key".createKey(), ++researchId, Bump.localization.getResearchName("key"), cost)
            .addItems(*itemStacks).register()
    }
}
