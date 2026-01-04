package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.BasicRecipe;
import com.mcon152.recipeshare.domain.Recipe;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScaleAndUndoTests {

@InjectMocks
private RecipeServiceImpl recipeService;

@Test
@DisplayName("Scale Recipe scales properly")
void scaleRecipeTest(){
    long recipeId;
  //we need to se the recipeID and then pass that in to scaleRecipe becuase the filed
    //needs to be a long.
    Recipe recipe = new BasicRecipe
            (1L, "Cake", "Delicious cake", "Flour, Sugar, Eggs",
                    "Mix and bake", 8);
   recipeService.scaleRecipe(1L, 24);
    assertEquals(24, recipe.getServings());
}


@Test
@DisplayName("Undo undoes properly")
void UndoRecipeTest(){
    Recipe recipe = new BasicRecipe
            (1L, "Cake", "Delicious cake", "Flour, Sugar, Eggs",
                    "Mix and bake", 8);
    recipeService.scaleRecipe(1L, 24);
    recipeService.undo(1L);
    assertEquals(8, recipe.getServings() );
}



