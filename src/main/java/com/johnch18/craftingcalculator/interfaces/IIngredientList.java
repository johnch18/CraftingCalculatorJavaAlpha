package com.johnch18.craftingcalculator.interfaces;

import java.util.Map;
import java.util.Set;

public interface IIngredientList {

    Map<IComponent, IIngredient> getIngredientMap();

    Set<Map.Entry<IComponent, IIngredient>> getIterator();

    boolean contains(IComponent component);

    boolean contains(IIngredient ingredient);

    IIngredient get(IComponent component);

    IIngredient get(IIngredient ingredient);

    IIngredient add(IIngredient ingredient);

    IIngredient subtract(IIngredient ingredient);

}
