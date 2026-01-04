package com.mcon152.recipeshare.domain;

public interface ScaleRecipe {
void scaleRecipe(long recipeId, int newServingSize);

void undo(long recipeId);

}
