package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.BasicRecipe;
import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScaleAndUndoLogging {

    private RecipeServiceImpl recipeService;
    private RecipeRepository repo;
    private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
    @BeforeEach
    void setup() {
        repo = mock(RecipeRepository.class);
        recipeService = new RecipeServiceImpl(repo);
    }
    @Test
    @DisplayName("Scale and Undo with Logging")
    void scaleAndUndoLogging() {

        Recipe recipe = new BasicRecipe(
                1L,
                "Chocolate Cake",
                "Delicious chocolate cake",
                "Flour, Sugar, Cocoa, Eggs",
                "Mix ingredients and bake",
                8
        );

        when(repo.findById(1L)).thenReturn(Optional.of(recipe));
        when(repo.save(any(Recipe.class))).thenAnswer(invocation -> {
            Recipe r = invocation.getArgument(0);
            log.info("Repository saved: " + r.getTitle() + " | Servings: " + r.getServings());
            return r;
        });

        log.info("Original servings: " + recipe.getServings());

        recipeService.scaleRecipe(1L, 12);
        log.info("After scale (should be 12): " + recipe.getServings());

        recipeService.undo(1L);
        log.info("After undo (should be 8): " + recipe.getServings());
    }
}
