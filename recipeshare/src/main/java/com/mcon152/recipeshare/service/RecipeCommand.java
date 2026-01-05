package com.mcon152.recipeshare.service;


import com.mcon152.recipeshare.domain.Recipe;

public interface RecipeCommand {
    void undo();       // revert the change
    Recipe getRecipe();
}