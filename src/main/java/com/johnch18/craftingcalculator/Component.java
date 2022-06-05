package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Component {

    // Stores all instances of Components
    private static final Map<String, Component> registry = new HashMap<>();
    // List of recipes
    private final List<Recipe> recipes;
    // Name of component
    private String name;
    // Whether it is a fluid
    private boolean isFluid;
    private String fancyName;

    /*
     * Construction / Destruction
     * */

    private Component(String name) {
        this(name, false);
    }

    private Component(String name, boolean isFluid) {
        this(name, isFluid, null);
    }

    private Component(String name, boolean isFluid, String fancyName) {
        setName(name);
        setFluid(isFluid);
        this.fancyName = fancyName;
        recipes = new ArrayList<>();
    }

    public static Component getComponent(String ingredientString) {
        return getComponent(ingredientString, false);
    }

    public static Component getComponent(String name, boolean isFluid) {
        return getComponent(name, isFluid, name);
    }

    public static Component getComponent(String name, boolean isFluid, String fancyName) {
        /*
         * Factory method to control construction
         * */
        // Get recipe if it exists
        if (registry.containsKey(name))
            return registry.get(name);
        // Create Recipe
        Component result;
        result = new Component(name, isFluid, fancyName);
        registry.put(name, result);
        return result;
    }

    public static Iterable<? extends Component> getComponents() {
        return registry.values();
    }

    public static Component deserialize(JSONObject object) {
        String name = object.optString("name");
        boolean isFluid = object.optBoolean("isFluid", false);
        String fancyName = object.optString("fancyName", name);
        return getComponent(name, isFluid, fancyName);
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public Recipe getActiveRecipe() {
        for (Recipe r : recipes) {
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

    public CostResult getCostOf(
            int n,
            String[] ingredientList
    ) throws CCRecursionException, CCNullPtrException, CCInvalidIngredientString {
        return getCostOf(n, new IngredientList(ingredientList));
    }

    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        result.put("name", getName());
        result.put("isFluid", isFluid());
        result.put("fancyName", getFancyName());
        return result;
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

    public String getFancyName() {
        if (fancyName != null)
            return fancyName;
        return getName();
    }

}
