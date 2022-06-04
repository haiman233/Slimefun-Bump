package org.slimefunguguproject.bump.implementation.appraise;

import net.guizhanss.guizhanlib.minecraft.MinecraftTag;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.slimefunguguproject.bump.utils.BumpTag;

import javax.annotation.Nonnull;

/**
 * This enum holds all available appraisal types.
 *
 * @author ybw0014
 */
public enum AppraiseType {
    WEAPON(false) {
        @Override
        public boolean isValidMaterial(@Nonnull Material type) {
            return BumpTag.WEAPON.isTagged(type);
        }

        @Override
        @Nonnull
        public EquipmentSlot getEquipmentSlot(@Nonnull Material type) {
            return EquipmentSlot.HAND;
        }
    },
    ARMOR(false) {
        @Override
        public boolean isValidMaterial(@Nonnull Material type) {
            return BumpTag.ARMOR.isTagged(type);
        }

        @Override
        @Nonnull
        public EquipmentSlot getEquipmentSlot(@Nonnull Material type) {
            if (MinecraftTag.HELMET.isTagged(type)) {
                return EquipmentSlot.HEAD;
            } else if (MinecraftTag.CHESTPLATE.isTagged(type)) {
                return EquipmentSlot.CHEST;
            } else if (MinecraftTag.LEGGINGS.isTagged(type)) {
                return EquipmentSlot.LEGS;
            } else if (MinecraftTag.BOOTS.isTagged(type)) {
                return EquipmentSlot.FEET;
            } else {
                return EquipmentSlot.HAND;
            }
        }
    },
    HORSE_ARMOR(true) {
        @Override
        public boolean isValidMaterial(@Nonnull Material type) {
            return BumpTag.HORSE_ARMOR.isTagged(type);
        }

        @Override
        @Nonnull
        public EquipmentSlot getEquipmentSlot(@Nonnull Material type) {
            return EquipmentSlot.CHEST;
        }
    };

    private final boolean allowVanillaItems;

    AppraiseType(boolean allowVanillaItems) {
        this.allowVanillaItems = allowVanillaItems;
    }

    /**
     * Check if the given {@link Material} is valid.
     *
     * @param type The {@link Material} to be checked
     *
     * @return If the given {@link Material} is valid
     */
    public abstract boolean isValidMaterial(@Nonnull Material type);

    /**
     * Get the {@link EquipmentSlot} for appraisal to be applied to.
     *
     * @param type The {@link Material} of appraisal equipment
     *
     * @return The target {@link EquipmentSlot}
     */
    public abstract EquipmentSlot getEquipmentSlot(@Nonnull Material type);

    /**
     * Check if the given {@link Material} is valid, with SlimefunItem check.
     *
     * @param material the {@link Material} to be checked
     * @param isSlimefunItem if the item is SlimefunItem
     *
     * @return If the given {@link Material} is valid
     */
    public boolean isValidMaterial(@Nonnull Material material, boolean isSlimefunItem) {
        if (isValidMaterial(material)) {
            return allowVanillaItems || isSlimefunItem;
        } else {
            return false;
        }
    }

    /**
     * Get the {@link AppraiseType} from given {@link Material}.
     *
     * @param material The {@link Material}
     *
     * @return Appropriate {@link AppraiseType}
     *
     * @throws IllegalArgumentException when given material is invalid.
     */
    @Nonnull
    public static AppraiseType getFromMaterial(@Nonnull Material material) {
        Validate.notNull(material, "Material should not be null");

        for (AppraiseType type : AppraiseType.values()) {
            if (type.isValidMaterial(material)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid material");
    }
}