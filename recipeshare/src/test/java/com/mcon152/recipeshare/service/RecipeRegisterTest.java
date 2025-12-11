package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.*;
import com.mcon152.recipeshare.domain.RecipeRegistry;
import com.mcon152.recipeshare.web.RecipeRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeRegisterTest {
    @BeforeAll
    static void setup() {
        // Ensure the registry is initialized.
        RecipeRegistry.register("BASIC", new BasicRecipe());
        RecipeRegistry.register("VEGETARIAN", new VegetarianRecipe());
        RecipeRegistry.register("DESSERT", new DessertRecipe());
        RecipeRegistry.register("DAIRY", new DairyRecipe());
    }

    @Test
    void createsBasicByDefault_andCopiesFields() {
        RecipeRequest req = new RecipeRequest();
        req.setTitle("Title");
        req.setDescription("Desc");
        req.setIngredients("I");
        req.setInstructions("N");
        req.setServings(3);

        Recipe r = RecipeRegistry.createFromRequest(req);
        assertTrue(r instanceof BasicRecipe);
        assertNull(r.getId());
        assertEquals("Title", r.getTitle());
        assertEquals("Desc", r.getDescription());
        assertEquals("I", r.getIngredients());
        assertEquals("N", r.getInstructions());
        assertEquals(Integer.valueOf(3), r.getServings());
    }

    @Test
    void createsSpecifiedSubtypes_caseInsensitive() {
        RecipeRequest req = new RecipeRequest();
        req.setType("vegetarian");
        Recipe r = RecipeRegistry.createFromRequest(req);
        assertTrue(r instanceof VegetarianRecipe);

        req.setType("DESSERT");
        r = RecipeRegistry.createFromRequest(req);
        assertTrue(r instanceof DessertRecipe);

        req.setType("DAIRY");
        r = RecipeRegistry.createFromRequest(req);
        assertTrue(r instanceof DairyRecipe);

        req.setType("BASIC");
        r = RecipeRegistry.createFromRequest(req);
        assertTrue(r instanceof BasicRecipe);
    }

    @Test
    void nullRequest_returnsBasic_withNullFields() {
        Recipe r = RecipeRegistry.createFromRequest(null);
        assertTrue(r instanceof BasicRecipe);
        assertNull(r.getTitle());
        assertNull(r.getDescription());
        assertNull(r.getIngredients());
        assertNull(r.getInstructions());
        assertNull(r.getServings());
    }

    @Test
    void unknownType_defaultsToBasic() {
        RecipeRequest req = new RecipeRequest();
        req.setType("UNKNOWN");
        Recipe r = RecipeRegistry.createFromRequest(req);
        assertTrue(r instanceof BasicRecipe);
    }
}
