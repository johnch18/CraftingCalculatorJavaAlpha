package com.johnch18.craftingcalculator.intr;

public interface IRecipe extends ISerializable {

    /*
    * Stores a relation between input items and output items.
    * Can also be toggled on and off.
    * */

    // Gets the input stacks
    IInventory getInputs();
    // Gets the output stacks
    IInventory getOutputs();
    // Checks whether the recipe should factor into calculations
    boolean isEnabled();
    // Sets enabled
    void setEnabled(boolean enabled);
    // Adds input
    void addInput(IStack stack);
    // Adds output
    void addOutput(IStack stack);

}
