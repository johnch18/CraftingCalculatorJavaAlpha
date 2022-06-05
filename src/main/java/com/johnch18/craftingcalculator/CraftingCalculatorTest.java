package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;
import com.johnch18.craftingcalculator.repl.RecipeREPL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class CraftingCalculatorTest {

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
            if (entry.getValue().isValid()) {
                System.out.print('\t');
                System.out.println(entry.getValue().toStringFancy());
            }
        }
    }

    public static void replTest() throws FileNotFoundException {
        RecipeREPL repl = new RecipeREPL("test.json");
        repl.repl();
    }

    public static void main(
            String[] args
    ) throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException, IOException {
        replTest();
    }

}
