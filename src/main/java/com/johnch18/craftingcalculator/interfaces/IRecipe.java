package com.johnch18.craftingcalculator.interfaces;

public interface IRecipe extends ISerializable {

    IIngredientList getInputs();

    IIngredientList getOutputs();

    boolean isEnabled();

    void addInput(IIngredient ingredient);

    void addOutput(IIngredient ingredient);

}
