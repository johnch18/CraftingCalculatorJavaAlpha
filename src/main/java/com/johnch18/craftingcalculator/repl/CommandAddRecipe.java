package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.Ingredient;
import com.johnch18.craftingcalculator.IngredientList;
import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;

public class CommandAddRecipe implements Command {

    @Override
    public String hook() {
        return "add-recipe";
    }

    @Override
    public String helpString() {
        return "Adds a recipe to the book. Usage: 'add-recipe [inputIngredients...] -> [outputIngredients...]'";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        if (args.length < 3) {
            repl.getDialog().printf("You need at least one input, one output, and the separation arrow.\n");
            return;
        }
        //
        IngredientList inputList = new IngredientList();
        IngredientList outputList = new IngredientList();
        int index = 0;
        try {
            for (; index < args.length; index++) {
                if (args[index].equals("->"))
                    break;
                inputList.addIngredient(new Ingredient(args[index]));
            }
            for (index += 1; index < args.length; index++) {
                outputList.addIngredient(new Ingredient(args[index]));
            }
        } catch (CCInvalidIngredientString e) {
            e.printStackTrace();
            repl.getDialog().printf("Invalid ingredient string '%s'\n", args[index]);
        }
        repl.getRecipeBook().addRecipe(outputList, inputList);
    }

}
