package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;

import java.util.Map;

public class CraftingCalculatorTest {

    public static void test_recursion() throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException {
        //
        RecipeBook recipeBook = new RecipeBook();
        recipeBook.addRecipe(
                new String[]{"netherStar:2"},
                new String[]{"tinyNetherStarDust", "magmaCream"}
        );
        recipeBook.addRecipe(
                new String[]{"tinyNetherStarDust:9"},
                new String[]{"netherStarDust"}
        );
        recipeBook.addRecipe(
                new String[]{"netherStarDust"},
                new String[]{"netherStar"}
        );
        //
        CostResult results = Component.createComponent("netherStar").getCostOf(64, new IngredientList(new String[]{
                "netherStar"
        }));
        display(results);
        //
    }

    public static void test_simple() throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException {
        //
        RecipeBook recipeBook = new RecipeBook();
        recipeBook.addRecipe(
                new String[]{"woodenPickaxe"},
                new String[]{"woodPlank:3", "stick:2"}
        );
        recipeBook.addRecipe(
                new String[]{"woodPlank:4"},
                new String[]{"woodLog:1"}
        );
        recipeBook.addRecipe(
                new String[]{"stick:4"},
                new String[]{"woodPlank:2"}
        );
        //
        Recipe woodPickaxeRecipe = recipeBook.getRecipesByOutput("woodenPickaxe").get(0);
        System.out.println(woodPickaxeRecipe);
        //
        Component test = Component.createComponent("woodenPickaxe");
        CostResult result = test.getCostOf(4, new IngredientList());
        display(result);
    }

    private static void display(CostResult result) {
        System.out.println("Inputs:");
        for (Map.Entry<String, Ingredient> entry : result.getCost().getIterator()) {
            System.out.print('\t');
            System.out.println(entry.getValue().toString());
        }
        System.out.println("Leftovers:");
        for (Map.Entry<String, Ingredient> entry : result.getExcess().getIterator()) {
            System.out.print('\t');
            System.out.println(entry.getValue().toString());
        }
    }

    public static void main(String[] args) throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException {
        test_recursion();
    }

}
