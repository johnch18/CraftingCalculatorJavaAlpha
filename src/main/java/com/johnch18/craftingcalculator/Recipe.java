package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;

import java.util.Map;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class Recipe {

    public static final int maxRecursionDepth = 5000;
    //
    private final IngredientList inputs;
    private final IngredientList outputs;
    private boolean isEnabled;

    /*
     * Methods
     * */

    public Recipe() {
        inputs = new IngredientList();
        outputs = new IngredientList();
        isEnabled = true;
    }

    public Recipe(Ingredient[] outputs, Ingredient[] inputs) {
        this();
        for (Ingredient output : outputs)
            addOutput(output);
        for (Ingredient input : inputs)
            addInput(input);
    }

    public Recipe(String[] outputs, String[] inputs) throws CCInvalidIngredientString {
        this();
        for (String output : outputs)
            addOutput(new Ingredient(output));
        for (String input : inputs)
            addInput(new Ingredient(input));
    }

    private void getCostRecursive(Ingredient target,
                                  CostResult result) throws CCRecursionException, CCNullPtrException {
        getCostRecursive(target, result.getCost(), result.getExcess(), 0);
    }

    private void getCostRecursive(
            Ingredient initialIngredient,
            IngredientList inputList,
            IngredientList cache,
            int depth
    ) throws CCRecursionException, CCNullPtrException {
        if (depth > maxRecursionDepth)
            throw new CCRecursionException("Maximum Recursion Depth reached.");
        //
        Ingredient targetIngredient = getOutputIngredient(initialIngredient);
        if (targetIngredient == null)
            throw new CCNullPtrException("Invalid targetIngredient");
        // Factor in items in the cache
        subtractFromCache(initialIngredient, cache);
        // If request already satisfied, skip
        if (initialIngredient.getAmount() <= 0)
            return;
        // Calculate number of crafts needed
        int numberOfCrafts = calculateNumberOfCrafts(initialIngredient, targetIngredient);
        // Deal with excess outputs, add them to cache
        factorInOutputs(initialIngredient, numberOfCrafts, cache);
        // Do recursion
        doRecursiveStep(numberOfCrafts, inputList, cache, depth);
    }

    public void doRecursiveStep(
            int numberOfCrafts,
            IngredientList inputList,
            IngredientList cache,
            int depth
    ) throws CCRecursionException, CCNullPtrException {
        for (Map.Entry<String, Ingredient> entry : inputs.getIterator()) {
            // Get the new target ingredient, component, and recipe
            Ingredient newInputIngredient = entry.getValue().multiply(numberOfCrafts);
            Component component = newInputIngredient.getComponent();
            Recipe recipe = component.getActiveRecipe();
            if (recipe == null || !recipe.isEnabled()) {
                // Skip if recipe is not valid
                inputList.addIngredient(newInputIngredient);
            } else {
                // Recurse otherwise
                recipe.getCostRecursive(newInputIngredient, inputList, cache, depth + 1);
            }
        }
    }

    public void factorInOutputs(Ingredient initialIngredient, int numberOfCrafts, IngredientList cache) {
        for (Map.Entry<String, Ingredient> entry : outputs.getIterator()) {
            Ingredient scaledOutput = entry.getValue().multiply(numberOfCrafts);
            if (!scaledOutput.isSameAs(initialIngredient))
                cache.addIngredient(scaledOutput);
            else {
                int delta = scaledOutput.getAmount() - initialIngredient.getAmount();
                if (delta > 0)
                    cache.addIngredient(new Ingredient(scaledOutput.getComponent(), delta));
            }
        }
    }

    public int calculateNumberOfCrafts(Ingredient initialIngredient, Ingredient targetIngredient) {
        double amount = targetIngredient.getAmount();
        double chance = targetIngredient.getChance();
        double factor = amount * chance;
        return (int) ceil(initialIngredient.getAmount() / factor);
    }

    public void subtractFromCache(Ingredient targetIngredient, IngredientList cache) {
        // Factor in already provided items
        for (Map.Entry<String, Ingredient> element : cache.getIterator()) {
            Ingredient cacheIngredient = element.getValue();
            // If matching ingredient found, subtract
            if (cacheIngredient.isSameAs(targetIngredient)) {
                int n = min(targetIngredient.getAmount(), cacheIngredient.getAmount());
                targetIngredient.subInPlace(n);
                cacheIngredient.subInPlace(n);
            }
        }
    }

    private Ingredient getOutputIngredient(Ingredient ingredient) {
        String name = ingredient.getComponent().getName();
        return outputs.getMapping().get(name);
    }

    public void addInput(Ingredient ingredient) {
        inputs.addIngredient(ingredient);
    }

    public void addOutput(Ingredient ingredient) {
        outputs.addIngredient(ingredient);
        ingredient.getComponent().addRecipe(this);
    }

    public CostResult getCost(Ingredient target) throws CCRecursionException, CCNullPtrException {
        return getCost(target, new IngredientList());
    }

    public CostResult getCost(
            Ingredient target,
            String[] preCache
    ) throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException {
        return getCost(target, new IngredientList(preCache));
    }

    public CostResult getCost(
            Ingredient target,
            IngredientList preCache
    ) throws CCRecursionException, CCNullPtrException {
        CostResult result = new CostResult();
        result.getExcess().combineWith(preCache);
        getCostRecursive(target, result);
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("< ");
        for (Map.Entry<String, Ingredient> entry : inputs.getIterator()) {
            result.append(entry.getValue().toString());
            result.append(" ");
        }
        result.append("-> ");
        for (Map.Entry<String, Ingredient> entry : outputs.getIterator()) {
            result.append(entry.getValue().toString());
            result.append(" ");
        }
        result.append(">");
        return result.toString();
    }

    /*
     * Getters and setters
     * */

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public IngredientList getInputs() {
        return inputs;
    }


    public IngredientList getOutputs() {
        return outputs;
    }

}
