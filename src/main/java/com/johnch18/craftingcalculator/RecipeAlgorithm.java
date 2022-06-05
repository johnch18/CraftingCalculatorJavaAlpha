package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;

import java.util.Map;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class RecipeAlgorithm {

    public static final int maxRecursionDepth = 500;

    public static void getCostRecursive(
            Recipe recipe,
            Ingredient target,
            CostResult result
    ) throws CCRecursionException, CCNullPtrException {
        getCostRecursive(recipe, target, result.getCost(), result.getExcess(), 0);
    }

    public static void getCostRecursive(
            Recipe recipe,
            Ingredient initialIngredient,
            IngredientList inputList,
            IngredientList cache,
            int depth
    ) throws CCRecursionException, CCNullPtrException {
        if (depth > maxRecursionDepth)
            return;
        //
        Ingredient targetIngredient = recipe.getOutputIngredient(initialIngredient);
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
        factorInOutputs(recipe, initialIngredient, numberOfCrafts, cache);
        // Do recursion
        doRecursiveStep(recipe, numberOfCrafts, inputList, cache, depth);
    }

    public static void doRecursiveStep(
            Recipe recipe,
            int numberOfCrafts,
            IngredientList inputList,
            IngredientList cache,
            int depth
    ) throws CCRecursionException, CCNullPtrException {
        for (Map.Entry<String, Ingredient> entry : recipe.getInputs().getIterator()) {
            // Get the new target ingredient, component, and recipe
            Ingredient newInputIngredient = entry.getValue().multiply(numberOfCrafts);
            Component component = newInputIngredient.getComponent();
            Recipe activeRecipe = component.getActiveRecipe();
            if (activeRecipe == null || !activeRecipe.isEnabled()) {
                // Skip if recipe is not valid
                inputList.addIngredient(newInputIngredient);
            } else {
                // Recurse otherwise
                getCostRecursive(activeRecipe, newInputIngredient, inputList, cache, depth + 1);
            }
        }
    }

    public static void factorInOutputs(Recipe rec, Ingredient initialIngredient, int numberOfCrafts, IngredientList cache) {
        for (Map.Entry<String, Ingredient> entry : rec.getOutputs().getIterator()) {
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

    public static int calculateNumberOfCrafts(Ingredient initialIngredient, Ingredient targetIngredient) {
        double factor = targetIngredient.getEffectiveNumber();
        return (int) ceil(initialIngredient.getAmount() / factor);
    }

    public static void subtractFromCache(Ingredient targetIngredient, IngredientList cache) {
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

}
