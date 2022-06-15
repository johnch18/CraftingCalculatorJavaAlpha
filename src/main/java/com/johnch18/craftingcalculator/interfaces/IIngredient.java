package com.johnch18.craftingcalculator.interfaces;

public interface IIngredient extends ISerializable {

    IComponent getComponent();

    int getAmount();

    int getEffectiveAmount();

    void add(IIngredient ingredient);

    void add(int amount);

    void subtract(IIngredient ingredient);

    void subtract(int amount);

    IIngredient transferAllFrom(IIngredient other);

    IIngredient transferFrom(IIngredient other, int amount);

}
