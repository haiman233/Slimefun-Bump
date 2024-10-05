package io.github.slimefunguguproject.bump.implementation

import io.github.slimefunguguproject.bump.Bump
import io.github.slimefunguguproject.bump.implementation.groups.BumpItemGroups
import io.github.slimefunguguproject.bump.implementation.items.RandomEquipment
import io.github.slimefunguguproject.bump.implementation.items.food.ChewingGum
import io.github.slimefunguguproject.bump.implementation.items.food.Cola
import io.github.slimefunguguproject.bump.implementation.items.food.InstantNoodle
import io.github.slimefunguguproject.bump.implementation.items.food.RiceDumpling
import io.github.slimefunguguproject.bump.implementation.items.food.SpicyStrips
import io.github.slimefunguguproject.bump.implementation.items.food.Sprite
import io.github.slimefunguguproject.bump.implementation.items.machines.Appraiser
import io.github.slimefunguguproject.bump.implementation.items.machines.AttributeGrindstone
import io.github.slimefunguguproject.bump.implementation.items.machines.ItemConverter
import io.github.slimefunguguproject.bump.implementation.items.materials.BumpMaterial
import io.github.slimefunguguproject.bump.implementation.items.tools.GetGoldSpade
import io.github.slimefunguguproject.bump.implementation.items.tools.QualityIdentifier
import io.github.slimefunguguproject.bump.implementation.items.weapons.DemonSlayerSword
import io.github.slimefunguguproject.bump.implementation.items.weapons.HeavenBreakingDemonSlayerSword
import io.github.slimefunguguproject.bump.implementation.items.weapons.HeavenBreakingSword
import io.github.slimefunguguproject.bump.implementation.items.weapons.LightningBow
import io.github.slimefunguguproject.bump.implementation.items.weapons.NormalSword
import io.github.slimefunguguproject.bump.implementation.items.weapons.SoulSword
import io.github.slimefunguguproject.bump.implementation.items.weapons.WitherSkullBow
import io.github.slimefunguguproject.bump.implementation.recipes.BumpRecipeTypes
import io.github.slimefunguguproject.bump.utils.GeneralUtils
import io.github.slimefunguguproject.bump.utils.items.AppraiseUtils
import io.github.slimefunguguproject.bump.utils.items.MaterialType
import io.github.slimefunguguproject.bump.utils.items.RecipeUtils
import io.github.slimefunguguproject.bump.utils.items.buildSlimefunItem
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder
import net.guizhanss.guizhanlib.minecraft.utils.compatibility.EnchantmentX
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack


object BumpItems {

    // <editor-fold desc="Materials">
    val PHOTOSYNTHETIC_ENERGY = buildSlimefunItem<BumpMaterial> {
        id = "PHOTOSYNTHETIC_ENERGY"
        material = MaterialType.Material(Material.BELL)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = arrayOf(
            ItemStack(Material.CHORUS_FLOWER), ItemStack(Material.SUNFLOWER), ItemStack(Material.CHORUS_FLOWER),
            null, null, null,
            null, null, null
        )
    }

    val MECHANICAL_GEAR = buildSlimefunItem<BumpMaterial> {
        id = "MECHANICAL_GEAR"
        material = MaterialType.Material(Material.ENDER_PEARL)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ADVANCED_CIRCUIT_BOARD,
            null, SlimefunItems.COPPER_WIRE, null,
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ADVANCED_CIRCUIT_BOARD,
            // @formatter:on
        )
    }

    val BROKEN_GOLD_COIN = buildSlimefunItem<BumpMaterial> {
        id = "BROKEN_GOLD_COIN"
        material = MaterialType.Material(Material.GOLD_NUGGET)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = BumpRecipeTypes.GET_GOLD_SPADE
        recipe = RecipeUtils.centerRecipe(ItemStack(Material.SAND))
    }

    val COMPUTER_TECH_CORE = buildSlimefunItem<BumpMaterial> {
        id = "COMPUTER_TECH_CORE"
        material = MaterialType.Material(Material.DIAMOND)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            SlimefunItems.BATTERY, SlimefunItems.BATTERY, SlimefunItems.BATTERY,
            SlimefunItems.COOLING_UNIT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.COOLING_UNIT,
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.ADVANCED_CIRCUIT_BOARD,
            // @formatter:on
        )
    }

    val BROKEN_CPU = buildSlimefunItem<BumpMaterial> {
        id = "BROKEN_CPU"
        material = MaterialType.Material(Material.PRISMARINE_CRYSTALS)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE,
            SlimefunItems.COPPER_WIRE, COMPUTER_TECH_CORE, SlimefunItems.COPPER_WIRE,
            SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE,
            // @formatter:on
        )
    }

    val CPU = buildSlimefunItem<BumpMaterial> {
        id = "CPU"
        material = MaterialType.Material(Material.DIAMOND)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.COMPRESSOR
        recipe = arrayOf(BROKEN_CPU)
    }

    val ANCIENT_RUNE_SOUL = buildSlimefunItem<BumpMaterial> {
        id = "ANCIENT_RUNE_SOUL"
        material = MaterialType.Material(Material.PAPER)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.ANCIENT_ALTAR
        recipe = arrayOf(
            // @formatter:off
            SlimefunItems.MAGIC_LUMP_1, SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.MAGIC_LUMP_1,
            SlimefunItems.SOULBOUND_RUNE, SlimefunItems.SOULBOUND_RUNE, SlimefunItems.SOULBOUND_RUNE,
            SlimefunItems.MAGIC_LUMP_1, SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.MAGIC_LUMP_1,
            // @formatter:on
        )
    }

    val INSTANT_NOODLE_SEASONING = buildSlimefunItem<BumpMaterial> {
        id = "INSTANT_NOODLE_SEASONING"
        material = MaterialType.Material(Material.BEETROOT_SEEDS)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            SlimefunItems.SALT, SlimefunItems.SALT, SlimefunItems.SALT,
            SlimefunItems.CARROT_FERTILIZER, SlimefunItems.CARROT_FERTILIZER, SlimefunItems.CARROT_FERTILIZER,
            SlimefunItems.SALT, SlimefunItems.SALT, SlimefunItems.SALT,
            // @formatter:on
        )
    }

    val POP_CANDY = buildSlimefunItem<BumpMaterial> {
        id = "POP_CANDY"
        material = MaterialType.Material(Material.SUGAR)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.PRESSURE_CHAMBER
        recipe = arrayOf(SlimefunItems.MAGIC_SUGAR)
    }

    val PEACH_WOOD = buildSlimefunItem<BumpMaterial> {
        id = "PEACH_WOOD"
        material = MaterialType.Material(Material.STICK)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            ItemStack(Material.ACACIA_WOOD), ItemStack(Material.BIRCH_WOOD), ItemStack(Material.DARK_OAK_WOOD),
            null, null, null,
            null, null, null
        )
    }

    val UPDATE_CORE = buildSlimefunItem<BumpMaterial> {
        id = "UPDATE_CORE"
        material = MaterialType.Material(Material.ZOMBIE_HEAD)
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            SlimefunItems.POWER_CRYSTAL, SlimefunItems.LAVA_CRYSTAL, SlimefunItems.POWER_CRYSTAL,
            SlimefunItems.LAVA_CRYSTAL, CPU, SlimefunItems.LAVA_CRYSTAL,
            SlimefunItems.GOLD_24K, SlimefunItems.GOLD_24K, SlimefunItems.GOLD_24K,
            // @formatter:on
        )
    }

    val COMPRESSED_RANDOM_EQUIPMENT = buildSlimefunItem<BumpMaterial> {
        id = "COMPRESSED_RANDOM_EQUIPMENT"
        material = MaterialType.Head("bb82d5e2033ce8d9821d53e59d480f3dd60b4f9e555f67056c938c0240e16ffe")
        itemGroup = BumpItemGroups.MATERIALS
        recipeType = RecipeType.COMPRESSOR
        recipe = RecipeUtils.centerRecipe(
            CustomItemStack(Material.DIAMOND_CHESTPLATE, Bump.localization.getLore("any-random-equipment"))
        )
    }
    // </editor-fold>

    // <editor-fold desc="Foods">
    val SPRITE = buildSlimefunItem<Sprite> {
        id = "SPRITE"
        material = MaterialType.Material(Material.POTION)
        itemGroup = BumpItemGroups.FOOD
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = arrayOf(
            // @formatter:off
            POP_CANDY, ItemStack(Material.WATER_BUCKET), POP_CANDY,
            ItemStack(Material.WATER_BUCKET), POP_CANDY, ItemStack(Material.WATER_BUCKET),
            POP_CANDY, ItemStack(Material.WATER_BUCKET), POP_CANDY,
            // @formatter:on
        )
        postCreate = {
            GeneralUtils.glowItem(it)
        }
    }

    val COLA = buildSlimefunItem<Cola> {
        id = "COLA"
        material = MaterialType.Material(Material.POTION)
        itemGroup = BumpItemGroups.FOOD
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = arrayOf(
            // @formatter:off
            POP_CANDY, ItemStack(Material.WATER_BUCKET), POP_CANDY,
            ItemStack(Material.WATER_BUCKET), SlimefunItems.MAGIC_SUGAR, ItemStack(Material.WATER_BUCKET),
            POP_CANDY, ItemStack(Material.WATER_BUCKET), POP_CANDY,
            // @formatter:on
        )
    }

    val INSTANT_NOODLE = buildSlimefunItem<InstantNoodle> {
        id = "INSTANT_NOODLE"
        material = MaterialType.Material(Material.STRING)
        itemGroup = BumpItemGroups.FOOD
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = arrayOf(
            // @formatter:off
            ItemStack(Material.WATER_BUCKET), ItemStack(Material.WATER_BUCKET), ItemStack(Material.WATER_BUCKET),
            INSTANT_NOODLE_SEASONING, INSTANT_NOODLE_SEASONING, INSTANT_NOODLE_SEASONING,
            SlimefunItems.WHEAT_FLOUR, SlimefunItems.WHEAT_FLOUR, SlimefunItems.WHEAT_FLOUR,
            // @formatter:on
        )
    }

    val SPICY_STRIPS = buildSlimefunItem<SpicyStrips> {
        id = "SPICY_STRIPS"
        material = MaterialType.Material(Material.ROTTEN_FLESH)
        itemGroup = BumpItemGroups.FOOD
        recipeType = RecipeType.COMPRESSOR
        recipe = arrayOf(SlimefunItems.WHEAT_FLOUR)
    }

    val CHEWING_GUM = buildSlimefunItem<ChewingGum> {
        id = "CHEWING_GUM"
        material = MaterialType.Material(Material.SUGAR)
        itemGroup = BumpItemGroups.FOOD
        recipeType = RecipeType.COMPRESSOR
        recipe = arrayOf(SlimefunItems.MAGIC_SUGAR)
    }

    val RICE_DUMPLING = buildSlimefunItem<RiceDumpling> {
        id = "RICE_DUMPLING"
        material = MaterialType.Material(Material.GREEN_DYE)
        itemGroup = BumpItemGroups.FOOD
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = arrayOf(
            ItemStack(Material.LILY_PAD), ItemStack(Material.ACACIA_LEAVES), ItemStack(Material.LILY_PAD),
            ItemStack(Material.ACACIA_LEAVES), ItemStack(Material.WHEAT), ItemStack(Material.ACACIA_LEAVES),
            ItemStack(Material.LILY_PAD), ItemStack(Material.ACACIA_LEAVES), ItemStack(Material.LILY_PAD),
        )
    }
    // </editor-fold>

    // <editor-fold desc="Tools">
    val GET_GOLD_SPADE = buildSlimefunItem<GetGoldSpade> {
        id = "GET_GOLD_SPADE"
        material = MaterialType.Material(Material.GOLDEN_SHOVEL)
        itemGroup = BumpItemGroups.TOOLS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, SlimefunItems.GOLD_24K, null,
            null, ItemStack(Material.STICK), null,
            null, ItemStack(Material.STICK), null,
        )
        postCreate = {
            it.addUnsafeEnchantment(Enchantment.MENDING, 1)
        }
    }

    val QUALITY_IDENTIFIER = buildSlimefunItem<QualityIdentifier> {
        id = "QUALITY_IDENTIFIER"
        material = MaterialType.Material(Material.PAPER)
        itemGroup = BumpItemGroups.TOOLS
        recipeType = RecipeType.SMELTERY
        recipe = arrayOf(COMPRESSED_RANDOM_EQUIPMENT)

        +LoreBuilder.usesLeft(QualityIdentifier.MAX_USES)
    }
    // </editor-fold>

    // <editor-fold desc="Machines">
    val APPRAISER = buildSlimefunItem<Appraiser> {
        id = "APPRAISER"
        material = MaterialType.Material(Material.BELL)
        itemGroup = BumpItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            SlimefunItems.BATTERY, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.BATTERY,
            MECHANICAL_GEAR, CPU, MECHANICAL_GEAR,
            SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.COOLING_UNIT, SlimefunItems.ADVANCED_CIRCUIT_BOARD,
            // @formatter:off
        )

        +LoreBuilder.power(Appraiser.ENERGY_CONSUMPTION, " ${Bump.localization.getLore("per-use")}")
    }

    val ATTRIBUTE_GRINDSTONE = buildSlimefunItem<AttributeGrindstone> {
        id = "ATTRIBUTE_GRINDSTONE"
        material = MaterialType.Material(Material.GRINDSTONE)
        itemGroup = BumpItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            SlimefunItems.ELECTRO_MAGNET, APPRAISER, SlimefunItems.ELECTRO_MAGNET,
            MECHANICAL_GEAR, CPU, MECHANICAL_GEAR,
            UPDATE_CORE, null, UPDATE_CORE,
        )

        +LoreBuilder.power(AttributeGrindstone.ENERGY_CONSUMPTION, " ${Bump.localization.getLore("per-use")}")
    }

    val ITEM_CONVERTER = buildSlimefunItem<ItemConverter> {
        id = "ITEM_CONVERTER"
        material = MaterialType.Material(Material.CRAFTING_TABLE)
        itemGroup = BumpItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            null, ItemStack(Material.REDSTONE), null,
            ItemStack(Material.DIAMOND), ItemStack(Material.SMITHING_TABLE), ItemStack(Material.DIAMOND),
            null, ItemStack(Material.REDSTONE_BLOCK), null,
            // @formatter:on
        )
    }
    // </editor-fold>

    // <editor-fold desc="Armor">
    val RANDOM_HELMET = buildSlimefunItem<RandomEquipment> {
        id = "RANDOM_HELMET"
        material = MaterialType.Material(Material.DIAMOND_HELMET)
        itemGroup = BumpItemGroups.ARMOR
        recipeType = RecipeType.ARMOR_FORGE
        recipe = arrayOf(
            ItemStack(Material.DIAMOND), ItemStack(Material.DIAMOND), ItemStack(Material.DIAMOND),
            ItemStack(Material.DIAMOND), UPDATE_CORE, ItemStack(Material.DIAMOND),
            BROKEN_GOLD_COIN, null, BROKEN_GOLD_COIN,
        )
        postCreate = {
            AppraiseUtils.setAppraisable(it)
        }
    }

    val RANDOM_CHESTPLATE = buildSlimefunItem<RandomEquipment> {
        id = "RANDOM_CHESTPLATE"
        material = MaterialType.Material(Material.DIAMOND_CHESTPLATE)
        itemGroup = BumpItemGroups.ARMOR
        recipeType = RecipeType.ARMOR_FORGE
        recipe = arrayOf(
            ItemStack(Material.DIAMOND), BROKEN_GOLD_COIN, ItemStack(Material.DIAMOND),
            ItemStack(Material.DIAMOND), UPDATE_CORE, ItemStack(Material.DIAMOND),
            ItemStack(Material.DIAMOND), ItemStack(Material.DIAMOND), ItemStack(Material.DIAMOND),
        )
        postCreate = {
            AppraiseUtils.setAppraisable(it)
        }
    }

    val RANDOM_LEGGINGS = buildSlimefunItem<RandomEquipment> {
        id = "RANDOM_LEGGINGS"
        material = MaterialType.Material(Material.DIAMOND_LEGGINGS)
        itemGroup = BumpItemGroups.ARMOR
        recipeType = RecipeType.ARMOR_FORGE
        recipe = arrayOf(
            ItemStack(Material.DIAMOND), BROKEN_GOLD_COIN, ItemStack(Material.DIAMOND),
            ItemStack(Material.DIAMOND), UPDATE_CORE, ItemStack(Material.DIAMOND),
            ItemStack(Material.DIAMOND), null, ItemStack(Material.DIAMOND),
        )
        postCreate = {
            AppraiseUtils.setAppraisable(it)
        }
    }

    val RANDOM_BOOTS = buildSlimefunItem<RandomEquipment> {
        id = "RANDOM_BOOTS"
        material = MaterialType.Material(Material.DIAMOND_BOOTS)
        itemGroup = BumpItemGroups.ARMOR
        recipeType = RecipeType.ARMOR_FORGE
        recipe = arrayOf(
            null, null, null,
            ItemStack(Material.DIAMOND), UPDATE_CORE, ItemStack(Material.DIAMOND),
            ItemStack(Material.DIAMOND), BROKEN_GOLD_COIN, ItemStack(Material.DIAMOND)
        )
        postCreate = {
            AppraiseUtils.setAppraisable(it)
        }
    }

    val RANDOM_HORSE_ARMOR = buildSlimefunItem<RandomEquipment> {
        id = "RANDOM_HORSE_ARMOR"
        material = MaterialType.Material(Material.DIAMOND_HORSE_ARMOR)
        itemGroup = BumpItemGroups.ARMOR
        recipeType = RecipeType.ARMOR_FORGE
        recipe = arrayOf(
            null, BROKEN_GOLD_COIN, null,
            null, ItemStack(Material.DIAMOND_HORSE_ARMOR), null,
            null, UPDATE_CORE, null,
        )
        postCreate = {
            AppraiseUtils.setAppraisable(it)
        }
    }
    // </editor-fold>

    // <editor-fold desc="Weapon">
    val RANDOM_SWORD = buildSlimefunItem<RandomEquipment> {
        id = "RANDOM_SWORD"
        material = MaterialType.Material(Material.DIAMOND_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, UPDATE_CORE, null,
            null, COMPUTER_TECH_CORE, null,
            null, ItemStack(Material.STICK), null
        )
        postCreate = {
            AppraiseUtils.setAppraisable(it)
        }
    }

    val LIGHTNING_BOW = buildSlimefunItem<LightningBow>(10) {
        id = "LIGHTNING_BOW"
        material = MaterialType.Material(Material.BOW)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            // @formatter:off
            null, SlimefunItems.STAFF_STORM, SlimefunItems.LIGHTNING_RUNE,
            SlimefunItems.POWER_CRYSTAL, SlimefunItems.LIGHTNING_RUNE, SlimefunItems.STAFF_STORM,
            null, SlimefunItems.STAFF_STORM, SlimefunItems.LIGHTNING_RUNE,
            // @formatter:on
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.POWER, 5)
        }
    }

    val WITHER_SKULL_BOW = buildSlimefunItem<WitherSkullBow>(5) {
        id = "WITHER_SKULL_BOW"
        material = MaterialType.Material(Material.BOW)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, PEACH_WOOD, SlimefunItems.NECROTIC_SKULL,
            PEACH_WOOD, SlimefunItems.POWER_CRYSTAL, SlimefunItems.NECROTIC_SKULL,
            null, PEACH_WOOD, SlimefunItems.NECROTIC_SKULL,
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.POWER, 5)
        }
    }

    val EMERALD_SWORD = buildSlimefunItem<NormalSword> {
        id = "EMERALD_SWORD"
        material = MaterialType.Material(Material.DIAMOND_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, ItemStack(Material.EMERALD), null,
            null, ItemStack(Material.EMERALD), null,
            null, ItemStack(Material.STICK), null
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.SHARPNESS, 2)
        }
    }

    val BONE_SWORD = buildSlimefunItem<NormalSword> {
        id = "BONE_SWORD"
        material = MaterialType.Material(Material.WOODEN_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, ItemStack(Material.BONE_BLOCK, 64), null,
            null, ItemStack(Material.BONE_BLOCK, 64), null,
            null, SlimefunItems.GRANDMAS_WALKING_STICK, null
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.UNBREAKING, 10)
        }
    }

    val GUARDIAN_SWORD = buildSlimefunItem<NormalSword> {
        id = "GUARDIAN_SWORD"
        material = MaterialType.Material(Material.GOLDEN_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, PHOTOSYNTHETIC_ENERGY, null,
            null, PHOTOSYNTHETIC_ENERGY, null,
            null, ItemStack(Material.STICK), null
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.SHARPNESS, 5)
            it.addUnsafeEnchantment(Enchantment.MENDING, 1)
        }
    }

    val PEACH_WOOD_SWORD = buildSlimefunItem<NormalSword> {
        id = "PEACH_WOOD_SWORD"
        material = MaterialType.Material(Material.WOODEN_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, PEACH_WOOD, null,
            null, PEACH_WOOD, null,
            null, ItemStack(Material.STICK), null
        )
        postCreate = {
            it.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5)
            it.addUnsafeEnchantment(EnchantmentX.UNBREAKING, 3)
        }
    }

    val SOUL_SWORD = buildSlimefunItem<SoulSword> {
        id = "SOUL_SWORD"
        material = MaterialType.Material(Material.IRON_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, null, null,
            ANCIENT_RUNE_SOUL, ItemStack(Material.DIAMOND_SWORD), ANCIENT_RUNE_SOUL,
            null, null, null
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.SHARPNESS, 2)
            it.addUnsafeEnchantment(EnchantmentX.UNBREAKING, 1)
        }
    }

    val HEAVEN_BREAKING_SWORD = buildSlimefunItem<HeavenBreakingSword>(5) {
        id = "HEAVEN_BREAKING_SWORD"
        material = MaterialType.Material(Material.DIAMOND_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            SlimefunItems.MAGIC_LUMP_2, SlimefunItems.AIR_RUNE, SlimefunItems.MAGIC_LUMP_2,
            SlimefunItems.RAINBOW_RUNE, SlimefunItems.RAINBOW_RUNE, SlimefunItems.MAGIC_LUMP_2,
            SlimefunItems.AIR_RUNE, SlimefunItems.MAGIC_LUMP_2, null
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.SHARPNESS, 1)
            it.addUnsafeEnchantment(EnchantmentX.UNBREAKING, 1)
            it.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1)
        }
    }

    val DEMON_SLAYER_SWORD = buildSlimefunItem<DemonSlayerSword>(5) {
        id = "DEMON_SLAYER_SWORD"
        material = MaterialType.Material(Material.DIAMOND_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            SlimefunItems.MAGIC_LUMP_2, SlimefunItems.ENDER_RUNE, SlimefunItems.MAGIC_LUMP_2,
            SlimefunItems.FIRE_RUNE, SlimefunItems.FIRE_RUNE, SlimefunItems.MAGIC_LUMP_2,
            SlimefunItems.ENDER_RUNE, SlimefunItems.MAGIC_LUMP_2, null
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.SHARPNESS, 1)
            it.addUnsafeEnchantment(EnchantmentX.UNBREAKING, 1)
            it.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 1)
        }
    }

    val HEAVEN_BREAKING_DEMON_SLAYER_SWORD = buildSlimefunItem<HeavenBreakingDemonSlayerSword>(5) {
        id = "HEAVEN_BREAKING_DEMON_SLAYER_SWORD"
        material = MaterialType.Material(Material.DIAMOND_SWORD)
        itemGroup = BumpItemGroups.WEAPONS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = arrayOf(
            null, null, null,
            HEAVEN_BREAKING_SWORD, UPDATE_CORE, DEMON_SLAYER_SWORD,
            null, null, null
        )
        postCreate = {
            it.addUnsafeEnchantment(EnchantmentX.SHARPNESS, 5)
            it.addUnsafeEnchantment(EnchantmentX.UNBREAKING, 5)
            it.addUnsafeEnchantment(Enchantment.LOYALTY, 5)
        }
    }
    // </editor-fold>
}
