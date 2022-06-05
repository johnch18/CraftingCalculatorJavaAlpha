package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.max;

public class IngredientList {

    private final Map<String, Ingredient> mapping;

    /*
     * Methods
     * */

    public IngredientList() {
        mapping = new HashMap<>();
    }

    public IngredientList(String[] ingredientStrings) throws CCInvalidIngredientString {
        this();
        for (String s : ingredientStrings) {
            addIngredient(new Ingredient(s));
        }
    }

    public IngredientList(Ingredient[] ingredients) {
        this();
        for (Ingredient ingredient : ingredients) {
            addIngredient(ingredient);
        }
    }

    public void addIngredient(Ingredient ingredient) {
        String name = ingredient.getComponentName();
        if (!contains(ingredient)) {
            mapping.put(name, ingredient);
        } else {
            Ingredient temp = mapping.get(name);
            temp.addInPlace(ingredient.getAmount());
        }
    }

    public void subtractIngredient(Ingredient ingredient) {
        String name = ingredient.getComponentName();
        if (contains(ingredient))  {
            Ingredient target = mapping.get(name);
            int n = max(0, target.getAmount() - ingredient.getAmount());
            target.setAmount(n);
            if (target.getAmount() <= 0) {
                mapping.remove(name);
            }
        }
    }

    public void combineWith(IngredientList other) {
        for (Map.Entry<String, Ingredient> entry : other.getIterator()) {
            addIngredient(entry.getValue());
        }
    }

    public boolean contains(Component component) {
        return contains(new Ingredient(component));
    }
    
    public boolean contains(Ingredient ingredient) {
        return mapping.containsKey(ingredient.getComponentName());
    }

    public Set<Map.Entry<String, Ingredient>> getIterator() {
        return getMapping().entrySet();
    }

    /*
    * Getters and Setters
    * */

    public Map<String, Ingredient> getMapping() {
        return mapping;
    }

}
