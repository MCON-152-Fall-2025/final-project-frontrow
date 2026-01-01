package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);

    List<Recipe> getAllRecipes();

    Optional<Recipe> getRecipeById(long id);

    boolean deleteRecipe(long id);

    Optional<Recipe> updateRecipe(long id, Recipe updatedRecipe);

    Optional<Recipe> patchRecipe(long id, Recipe partialRecipe);

    // Tag-related operations
    Optional<Recipe> addTagToRecipe(long recipeId, Tag tag);

    Optional<Recipe> removeTagFromRecipe(long recipeId, Tag tag);

    List<Recipe> findRecipesByTag(String tagName);

    List<Recipe> findRecipesByTagId(long tagId);

    // Scaling operations
    Optional<Recipe> scaleServings(long recipeId, int newServings);

    Optional<Recipe> undoLastCommand(long recipeId);
    
}
