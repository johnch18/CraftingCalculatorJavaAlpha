package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Component {

    // Stores all instances of Components
    private static final Map<String, Component> registry = new HashMap<>();
    // Name of component
    private String name;
    // Whether it is a fluid
    private boolean isFluid;
    // List of recipes
    private final List<Recipe> recipes;

    /*
     * Construction / Destruction
     * */


    public static Component createComponent(String ingredientString) {
        return createComponent(ingredientString, false);
    }

    public static Component createComponent(String name, boolean isFluid) {
        /*
        * Factory method to control construction
        * */
        // Get recipe if it exists
        if (registry.containsKey(name))
            return registry.get(name);
        // Create Recipe
        Component result;
        result = new Component(name, isFluid);
        registry.put(name, result);
        return result;
    }

    private Component(String name) {
        this(name, false);
    }

    private Component(String name, boolean isFluid) {
        setName(name);
        setFluid(isFluid);
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public Recipe getActiveRecipe() {
        for (Recipe r: recipes) {
            if (r.isEnabled())
                return r;
        }
        return null;
    }

    public boolean isSameAs(Component component) {
        return getName().equals(component.getName());
    }

    public CostResult getCostOf(int n) throws CCRecursionException, CCNullPtrException {
        return getCostOf(n, new IngredientList());
    }

    public CostResult getCostOf(int n, IngredientList ingredientList) throws CCRecursionException, CCNullPtrException {
        return getActiveRecipe().getCost(new Ingredient(this, n), ingredientList);
    }

    /*
     * Getters and Setters
     * */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFluid() {
        return isFluid;
    }

    public void setFluid(boolean fluid) {
        isFluid = fluid;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

}
