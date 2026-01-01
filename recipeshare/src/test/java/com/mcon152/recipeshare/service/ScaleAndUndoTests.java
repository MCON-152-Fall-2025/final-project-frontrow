/**
 Test recipe scales properly
 **/
@Test
@DisplayName("Scale Recipe scales properly")
void scaleRecipeTest(){
    Recipe recipe = new BasicRecipe
            (1L, "Cake", "Delicious cake", "Flour, Sugar, Eggs",
                    "Mix and bake", 8);
    recipe.(scaleRecipe(1L, 24));
    assertEquals(recipe.getServings(), 24);
}

@Test
@DisplayName("Undo undoes properly")
void UndoRecipeTest(){
    Recipe recipe = new BasicRecipe
            (1L, "Cake", "Delicious cake", "Flour, Sugar, Eggs",
                    "Mix and bake", 8);
    recipe.(scaleRecipe(1L, 24));
    assertEquals(recipe.undo(1L), 8);
}



