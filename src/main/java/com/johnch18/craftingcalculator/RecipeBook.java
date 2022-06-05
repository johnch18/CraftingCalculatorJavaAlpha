package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;

import java.util.ArrayList;
import java.util.List;

public class RecipeBook {

    private List<Recipe> recipes;

    public RecipeBook() {
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void addRecipe(String[] outputs, String[] inputs) throws CCInvalidIngredientString {
        addRecipe(new Recipe(outputs, inputs));
    }

    public void addRecipe(Ingredient[] outputs, Ingredient[] inputs) {
        addRecipe(new Recipe(new IngredientList(outputs), new IngredientList(inputs)));
    }

    public void addRecipe(IngredientList outputs, IngredientList inputs) {
        addRecipe(new Recipe(outputs, inputs));
    }


    public List<Recipe> getRecipesByOutput(String component) {
        return getRecipesByOutput(Component.getComponent(component));
    }

    public List<Recipe> getRecipesByOutput(Component component) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getOutputs().contains(component))
                results.add(recipe);
        }
        return results;
    }

    /*
    *   Getters and Setters
    * */

    public List<Recipe> getRecipes() {
        return recipes;
    }

}
