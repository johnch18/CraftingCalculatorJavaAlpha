package com.johnch18.craftingcalculator.intr;

import org.json.JSONObject;

import java.util.List;

public interface ITarget extends ISerializable {

    /*
    * Represents any singular thing that can be crafted.
    * This could be a wooden plank, a fluid, or even a fusion reactor or house.
    * */

    // Stores the textual representation of a 'target'.
    String getName();
    // Stores the metadata of the 'target', can also be encapsulated into the name.
    int getMetadata();
    // Stores the recipes associated with the 'target'
    List<IRecipe> getRecipes();

}
