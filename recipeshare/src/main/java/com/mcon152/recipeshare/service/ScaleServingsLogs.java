package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.Recipe;

public class ScaleServingsLogs implements RecipeCommand {

    private final Recipe recipe;
    private final int oldServings;

    public ScaleServingsLogs(Recipe recipe) {
        this.recipe = recipe;
        this.oldServings = recipe.getServings() != null ? recipe.getServings() : 0;
    }

    @Override
    public void undo() {
        recipe.setServings(oldServings);
    }

    @Override
    public Recipe getRecipe() {
        return recipe;
    }
}
