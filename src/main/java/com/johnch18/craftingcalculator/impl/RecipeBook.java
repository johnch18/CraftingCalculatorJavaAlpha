package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.interfaces.IComponent;
import com.johnch18.craftingcalculator.interfaces.IIngredient;
import com.johnch18.craftingcalculator.interfaces.IRecipe;
import com.johnch18.craftingcalculator.interfaces.IRecipeBook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeBook implements IRecipeBook {

    private final List<IRecipe> recipes = new ArrayList<>();

    @Override
    public List<IRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public void loadFromFile(String fileName) {
        String content = Utility.readFromFile(fileName);
        if (content == null)
            return;
        deserialize(new JSONObject(content));
    }

    @Override
    public void dumpToFile(String fileName) {
        Utility.writeToFile(fileName, serialize().toString());
    }

    @Override
    public void addRecipe(IRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public void deserialize(JSONObject json) {
        JSONArray componentArray = json.optJSONArray("recipes");
        for (Object _obj: componentArray) {
            JSONObject obj = ((JSONObject) _obj);
            Recipe recipe = new Recipe();
            recipe.deserialize(obj);
            addRecipe(recipe);
        }
    }

    @Override
    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        JSONArray recipeArray = new JSONArray();
        for (IRecipe recipe: recipes) {
            recipeArray.put(recipe.serialize());
        }
        result.put("recipes", recipeArray);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IRecipe recipe: recipes) {
            sb.append(recipe.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}
