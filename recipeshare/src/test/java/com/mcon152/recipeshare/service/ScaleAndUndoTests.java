package com.mcon152.recipeshare.service;


import com.mcon152.recipeshare.domain.BasicRecipe;
import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScaleAndUndoTests {

    private RecipeServiceImpl recipeService;
    private RecipeRepository repo;

    @BeforeEach
    void setup() {
        repo = mock(RecipeRepository.class);
        recipeService = new RecipeServiceImpl(repo);
    }


    @Test
    @DisplayName("Scale Recipe scales properly")
    void scaleRecipeTest() {
        Recipe recipe = new BasicRecipe(1L, "Cake", "Delicious cake",
                "Flour, Sugar, Eggs", "Mix and bake", 8);

        when(repo.findById(1L)).thenReturn(Optional.of(recipe));
        when(repo.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        recipeService.scaleRecipe(1L, 24);

        assertEquals(24, recipe.getServings());
        verify(repo).save(recipe);
    }

    @Test
    @DisplayName("Undo undoes properly")
    void undoRecipeTest() {
        Recipe recipe = new BasicRecipe(1L, "Cake", "Delicious cake",
                "Flour, Sugar, Eggs", "Mix and bake", 8);

        when(repo.findById(1L)).thenReturn(Optional.of(recipe));
        when(repo.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        recipeService.scaleRecipe(1L, 24);
        recipeService.undo(1L);

        assertEquals(8, recipe.getServings());
        verify(repo, times(2)).save(recipe);
    }

    @Test
    @DisplayName("Undo does nothing if recipe was never scaled")
    void undoDoesNothingIfNotScaled() {
        Recipe recipe = new BasicRecipe(1L, "Cake", "Delicious cake",
                "Flour, Sugar, Eggs", "Mix and bake", 8);

        when(repo.findById(1L)).thenReturn(Optional.of(recipe));
        when(repo.save(any(Recipe.class))).thenAnswer(invocation -> invocation.getArgument(0));

        recipeService.undo(1L);

        assertEquals(8, recipe.getServings());
        verify(repo, never()).save(any(Recipe.class));
    }

    @Test
    @DisplayName("Scale without recipe does nothing")
    void scaleWithoutRecipeTest() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        recipeService.scaleRecipe(1L, 10);

        verify(repo, never()).save(any(Recipe.class));
    }
}