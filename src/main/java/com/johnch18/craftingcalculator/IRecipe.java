package com.johnch18.craftingcalculator;

public interface IRecipe extends ISerializable {

    IIngredientList getInputs();
    IIngredientList getOutputs();
    boolean isEnabled();

}
