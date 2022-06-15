package com.johnch18.craftingcalculator;

import java.util.Map;

public interface IIngredientList {

    Map<IComponent, IIngredient> getIngredientMap();
    boolean contains(IComponent component);
    boolean contains(IIngredient ingredient);
    IIngredient get(IComponent component);
    IIngredient get(IIngredient ingredient);

}
