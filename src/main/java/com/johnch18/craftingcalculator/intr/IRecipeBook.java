package com.johnch18.craftingcalculator.intr;

import java.util.List;

public interface IRecipeBook extends ISerializable {

    /*
     * Stores a group of recipes and serves as the primary entry point for serialization/deserialization
     * */

    // Contains recipes used by class
    List<IRecipe> getRecipes();
    // Adds recipe
    void addRecipe(IRecipe recipe);
    // Loads an IRecipeBook from a provided filename
    void loadRecipesFromFile(String filename);
    // Dumps an IRecipeBook to a provided filename
    void dumpRecipesToFile(String filename);

}
