package com.johnch18.craftingcalculator.factories;

import com.johnch18.craftingcalculator.impl.Recipe;
import com.johnch18.craftingcalculator.intr.IRecipe;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeFactory {

    public static IRecipe deserialize(JSONObject json) {
        IRecipe recipe = new Recipe();
        JSONArray inputArray, outputArray;
        boolean enabled;
        //
        inputArray = json.optJSONArray("inputs");
        outputArray = json.optJSONArray("outputs");
        enabled = json.optBoolean("enabled", true);
        //
        for (Object __obj: inputArray)
            recipe.addInput((StackFactory.deserialize((JSONObject) __obj)));
        for (Object __obj: outputArray)
            recipe.addOutput((StackFactory.deserialize((JSONObject) __obj)));
        recipe.setEnabled(enabled);
        return recipe;
    }

}
