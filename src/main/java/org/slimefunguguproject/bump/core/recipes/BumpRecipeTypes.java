package org.slimefunguguproject.bump.core.recipes;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import org.slimefunguguproject.bump.implementation.Bump;
import org.slimefunguguproject.bump.implementation.BumpItems;

/**
 * This class holds all {@link RecipeType} of Bump.
 *
 * @author ybw0014
 */
public final class BumpRecipeTypes {
    public static final RecipeType GETGOLD_SPADE = new RecipeType(
        Bump.createKey("getgold_spade"),
        BumpItems.GETGOLD_SPADE
    );

    private BumpRecipeTypes() {
        throw new IllegalStateException("Utility class");
    }
}
