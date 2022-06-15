package com.johnch18.craftingcalculator.interfaces;

import java.util.List;

public interface IComponent extends ISerializable {

    String getName();

    int metadata();

    List<IRecipe> getRecipes();

    void addRecipe(IRecipe recipe);

    void setName(String name);

    void setMetadata(int metadata);

}
