package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.interfaces.IComponent;
import com.johnch18.craftingcalculator.interfaces.IIngredient;
import com.johnch18.craftingcalculator.interfaces.IIngredientList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IngredientList implements IIngredientList {

    private final Map<IComponent, IIngredient> ingredientMap = new HashMap<>();

    @Override
    public Map<IComponent, IIngredient> getIngredientMap() {
        return ingredientMap;
    }

    @Override
    public Set<Map.Entry<IComponent, IIngredient>> getIterator() {
        return ingredientMap.entrySet();
    }

    @Override
    public boolean contains(IComponent component) {
        return ingredientMap.containsKey(component) && get(component).getAmount() > 0;
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        return contains(ingredient.getComponent());
    }

    @Override
    public IIngredient get(IComponent component) {
        return ingredientMap.get(component);
    }

    @Override
    public IIngredient get(IIngredient ingredient) {
        return get(ingredient.getComponent());
    }

    @Override
    public IIngredient add(IIngredient ingredient) {
        if (contains(ingredient)) {
            IIngredient target = get(ingredient);
            target.add(ingredient);
            return target;
        } else {
            ingredientMap.put(ingredient.getComponent(), ingredient);
            return ingredient;
        }
    }

    @Override
    public IIngredient subtract(IIngredient ingredient) {
        if (contains(ingredient)) {
            IIngredient target = get(ingredient);
            target.subtract(ingredient);
            return target;
        }
        return null;
    }

}
