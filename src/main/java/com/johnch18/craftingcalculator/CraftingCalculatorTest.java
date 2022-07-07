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
        // Node root = Component.getComponent("netherStar").getActiveRecipe().getTree(new Ingredient("netherStar:64"));
        Node root = (new Ingredient("netherStar:64")).getTree();
        System.out.println(root.render());
    }

    public static void replTest() throws FileNotFoundException {
    }

    public static void main(
            String[] args
    ) throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException, IOException {
        if (args.length < 1) {
            System.out.println("Please enter a json file to use. Make sure to backup just in case.");
        } else {
            RecipeREPL repl = new RecipeREPL(args[0]);
            repl.repl();
        }
    }

}
