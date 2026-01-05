package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.domain.ScaleRecipe;
import com.mcon152.recipeshare.domain.Tag;
import com.mcon152.recipeshare.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService, ScaleRecipe {

    private final RecipeRepository repo;
    private final Map<Long, Integer> lastServings = new HashMap<>();

    public RecipeServiceImpl(RecipeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(null); // ensure new entity
        return repo.save(recipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return repo.findAll();
    }

    @Override
    public Optional<Recipe> getRecipeById(long id) {
        return repo.findById(id);
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Recipe> updateRecipe(long id, Recipe updatedRecipe) {
        return repo.findById(id).map(existing -> {
            // Preserve entity type (do not replace the DB row with a different subtype)
            existing.setTitle(updatedRecipe.getTitle());
            existing.setDescription(updatedRecipe.getDescription());
            existing.setIngredients(updatedRecipe.getIngredients());
            existing.setInstructions(updatedRecipe.getInstructions());
            existing.setServings(updatedRecipe.getServings());

            // Update author if provided
            if (updatedRecipe.getAuthor() != null) {
                existing.setAuthor(updatedRecipe.getAuthor());
            }

            // Update tags if provided (replace all tags)
            if (updatedRecipe.getTags() != null) {
                existing.clearTags();
                updatedRecipe.getTags().forEach(existing::addTag);
            }

            return repo.save(existing);
        });
    }

    @Override
    public Optional<Recipe> patchRecipe(long id, Recipe partialRecipe) {
        return repo.findById(id).map(existing -> {
            if (partialRecipe.getTitle() != null) existing.setTitle(partialRecipe.getTitle());
            if (partialRecipe.getDescription() != null) existing.setDescription(partialRecipe.getDescription());
            if (partialRecipe.getIngredients() != null) existing.setIngredients(partialRecipe.getIngredients());
            if (partialRecipe.getInstructions() != null) existing.setInstructions(partialRecipe.getInstructions());
            if (partialRecipe.getServings() != null) existing.setServings(partialRecipe.getServings());

            // Patch author if provided
            if (partialRecipe.getAuthor() != null) {
                existing.setAuthor(partialRecipe.getAuthor());
            }

            // Patch tags if provided (replace all tags)
            if (partialRecipe.getTags() != null && !partialRecipe.getTags().isEmpty()) {
                existing.clearTags();
                partialRecipe.getTags().forEach(existing::addTag);
            }

            return repo.save(existing);
        });
    }

    // Tag-related operations

    @Override
    public Optional<Recipe> addTagToRecipe(long recipeId, Tag tag) {
        return repo.findById(recipeId).map(recipe -> {
            recipe.addTag(tag);
            return repo.save(recipe);
        });
    }

    @Override
    public Optional<Recipe> removeTagFromRecipe(long recipeId, Tag tag) {
        return repo.findById(recipeId).map(recipe -> {
            recipe.removeTag(tag);
            return repo.save(recipe);
        });
    }

    @Override
    public List<Recipe> findRecipesByTag(String tagName) {
        return repo.findAll().stream().filter(recipe -> recipe.getTags().stream().anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName))).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findRecipesByTagId(long tagId) {
        return repo.findAll().stream().filter(recipe -> recipe.getTags().stream().anyMatch(tag -> tag.getId() != null && tag.getId().equals(tagId))).collect(Collectors.toList());
    }

    public void scaleRecipe(long recipeId, int newServingSize) {
        if (newServingSize > 0) {
        Optional<Recipe> recipe = repo.findById(recipeId);
            //hashmap that stores the recipe Id and it's older value
            if(recipe.isPresent()){
            lastServings.put(recipeId, recipe.get().getServings());
            recipe.get().setServings(newServingSize);
            repo.save(recipe.get()); // persist changes
        }}
    }

    //get the last value for a certain recipe and update it to it's servings to it's older value
    public void undo(long recipeId) {
        //find last value of the recipe and update it

        if (lastServings.containsKey(recipeId)) {
            Optional<Recipe> currRecipe = getRecipeById(recipeId);

            if (currRecipe.isPresent()) {
                int oldServings = lastServings.get(recipeId);
                currRecipe.get().setServings(oldServings);
                repo.save(currRecipe.get()); // persist changes
                lastServings.remove(recipeId); // remove after undo
            }

        }

    }
}
