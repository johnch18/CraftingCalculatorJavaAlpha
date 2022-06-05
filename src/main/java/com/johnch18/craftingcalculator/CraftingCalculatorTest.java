package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;
import com.johnch18.craftingcalculator.repl.RecipeREPL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class CraftingCalculatorTest {

    public static void test_recursion() throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException, IOException {
        //
        RecipeBook recipeBook = RecipeBook.loadBookFromFile("./test.json");
        //
        CostResult results = (new Ingredient("netherStar", 64)).getCost();
        display(results);
        //
        recipeBook.dumpBookToFile("./test.json");
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
        //
        CostResult result = Component.getComponent("woodenPickaxe").getCostOf(4, new IngredientList());
        display(result);
    }

    public static void loadBook() throws CCInvalidIngredientString, IOException {
        RecipeBook book = RecipeBook.loadBookFromFile("./test.json");
        book.dumpBookToFile("./test2.json");
    }

    public static void testTree() throws CCInvalidIngredientString, IOException, CCRecursionException, CCNullPtrException {
        RecipeBook book = RecipeBook.loadBookFromFile("./test.json");
        Node root = Component.getComponent("netherStar").getActiveRecipe().getTree(new Ingredient("netherStar:64"));
        System.out.println(root.render());
    }

    private static void display(CostResult result) {
        System.out.println("Inputs:");
        for (Map.Entry<String, Ingredient> entry : result.getCost().getIterator()) {
            if (entry.getValue().isValid()) {
                System.out.print('\t');
                System.out.println(entry.getValue().toStringFancy());
            }
        }
        System.out.println("Leftovers:");
        for (Map.Entry<String, Ingredient> entry : result.getExcess().getIterator()) {
            if (entry.getValue().isValid()){
                System.out.print('\t');
                System.out.println(entry.getValue().toStringFancy());
            }
        }
    }

    public static void replTest() {
        RecipeREPL repl = new RecipeREPL("test.json");
        repl.repl();
    }

    public static void main(String[] args) throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException, IOException {
        replTest();
    }

}
