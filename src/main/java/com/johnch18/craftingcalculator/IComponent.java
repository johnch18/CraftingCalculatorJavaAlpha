package com.johnch18.craftingcalculator;

import java.util.List;

public interface IComponent extends ISerializable {

    String getName();
    int metadata();
    List<IRecipe> getRecipes();

}
