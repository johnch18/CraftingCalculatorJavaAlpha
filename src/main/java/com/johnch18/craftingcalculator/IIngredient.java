package com.johnch18.craftingcalculator;

public interface IIngredient extends ISerializable {

    IComponent getComponent();
    int getAmount();
    int getEffectiveAmount();

}
