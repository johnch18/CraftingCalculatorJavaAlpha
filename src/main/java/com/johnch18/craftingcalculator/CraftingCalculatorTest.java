package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;

import java.util.Map;

public class CraftingCalculatorTest {

    public static void test_recursion() throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException {
        //
        Recipe netherStarRecipe = new Recipe(
                new String[]{"netherStar:2"},
                new String[]{"tinyNetherStarDust", "magmaCream"}
        );
        Recipe tinyNetherStarDustRecipe = new Recipe(
                new String[]{"tinyNetherStarDust:9"},
                new String[]{"netherStarDust"}
        );
        Recipe netherStarDustRecipe = new Recipe(
                new String[]{"netherStarDust"},
                new String[]{"netherStar"}
        );
        //
        System.out.println("Recipe:");
        System.out.println(netherStarRecipe);
        //
        Ingredient target = new Ingredient("netherStar:64");
        CostResult results = netherStarRecipe.getCost(target, new String[]{"tinyNetherStarDust"});
        //
        display(results);
        //
    }

    public static void test_simple() throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException {
        //
        Recipe woodPickaxeRecipe = new Recipe(
                new String[]{"woodenPickaxe"},
                new String[]{"woodPlank:3", "stick:2"}
        );
        Recipe woodPlankRecipe = new Recipe(
                new String[]{"woodPlank:4"},
                new String[]{"woodLog:1"}
        );
        Recipe stickRecipe = new Recipe(
                new String[]{"stick:4"},
                new String[]{"woodPlank:2"}
        );
        //
        System.out.println(woodPickaxeRecipe);
        //
        Ingredient target = new Ingredient("woodenPickaxe:4");
        CostResult result = woodPickaxeRecipe.getCost(target);
        //
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
