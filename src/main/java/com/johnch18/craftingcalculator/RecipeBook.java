package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeBook {

    private static final String componentFieldName = "components";
    private static final String recipeFieldName = "recipes";

    private final List<Recipe> recipes;
    private boolean isDirty;

    public RecipeBook() {
        setDirty(true);
        recipes = new ArrayList<>();
    }

    public static RecipeBook loadBookFromFile(String fileName) throws IOException, CCInvalidIngredientString {
        RecipeBook result = new RecipeBook();
        return loadBookFromFile(result, fileName);
    }

    public static RecipeBook loadBookFromFile(
            RecipeBook recipeBook,
            String fileName
    ) throws FileNotFoundException, CCInvalidIngredientString {
        String content = Utility.readFile(fileName);
        assert content != null;
        JSONObject object = new JSONObject(content);
        loadComponents(recipeBook, object);
        loadRecipes(recipeBook, object);
        return recipeBook;
    }



    private static void loadRecipes(RecipeBook result, JSONObject object) throws CCInvalidIngredientString {
        for (Object temp : object.getJSONArray(recipeFieldName))
            result.addRecipe(Recipe.deserialize((JSONObject) temp));
    }

    private static void loadComponents(RecipeBook result, JSONObject object) {
        for (Object temp : object.getJSONArray(componentFieldName))
            Component.deserialize((JSONObject) temp);
    }

    public void dumpBookToFile(String fileName) {
        JSONObject obj = new JSONObject();
        //
        dumpComponents(obj);
        dumpRecipes(obj);
        String result = obj.toString();
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDirty(false);
    }

    private void dumpRecipes(JSONObject obj) {
        JSONArray recArray = new JSONArray();
        for (Recipe rec : recipes)
            recArray.put(rec.serialize());
        obj.put(recipeFieldName, recArray);
    }

    public void dumpComponents(JSONObject obj) {
        JSONArray compArray = new JSONArray();
        for (Component comp : Component.getComponents())
            compArray.put(comp.serialize());
        obj.put(componentFieldName, compArray);
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

    private void addRecipe(IngredientList outputs, IngredientList inputs, boolean enabled) {
        Recipe recipe = new Recipe(outputs, inputs);
        recipe.setEnabled(enabled);
        addRecipe(recipe);
        setDirty(true);
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

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

}
