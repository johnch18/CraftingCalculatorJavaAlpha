package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.factories.RecipeFactory;
import com.johnch18.craftingcalculator.intr.IRecipe;
import com.johnch18.craftingcalculator.intr.IRecipeBook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeBook implements IRecipeBook {

    public static final String VERSION = "2.0.0";

    /*
     * Stores and manages a table of recipes for serialization, deserialization, calculation, etc.
     * */

    // The table of recipes
    private final List<IRecipe> recipes = new ArrayList<>();

    // Does nothing, not necessary, just nice considering loadRecipesFromFile
    public RecipeBook() {
    }

    // Initializes from file
    public RecipeBook(String filename) {
        loadRecipesFromFile(filename);
    }

    @Override
    public List<IRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public void addRecipe(IRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public void loadRecipesFromFile(String filename) {
        String fileContent = Utility.readFile(filename);
        if (fileContent != null)
            deserialize(new JSONObject(fileContent));
    }

    @Override
    public void dumpRecipesToFile(String filename) {
        JSONObject serialized = serialize();
        Utility.writeFile(filename, serialized.toString());
    }

    @Override
    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        JSONArray recipeArray = new JSONArray();
        for (IRecipe r: getRecipes())
            recipeArray.put(r.serialize());
        result.put("version", VERSION);
        result.put("recipes", recipeArray);
        return result;
    }

    public void deserialize(JSONObject json) {
        String version = json.optString("version", VERSION);
        JSONArray recipeArray = json.optJSONArray("recipes");
        for (Object __obj: recipeArray)
            addRecipe(RecipeFactory.deserialize((JSONObject) __obj));
    }

}
