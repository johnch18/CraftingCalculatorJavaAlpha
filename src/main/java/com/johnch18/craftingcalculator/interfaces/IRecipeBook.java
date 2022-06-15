package com.johnch18.craftingcalculator.interfaces;

import org.json.JSONObject;

import java.util.List;

public interface IRecipeBook extends ISerializable {

    List<IRecipe> getRecipes();

    void loadFromFile(String fileName);

    void dumpToFile(String fileName);

    void addRecipe(IRecipe recipe);


}
