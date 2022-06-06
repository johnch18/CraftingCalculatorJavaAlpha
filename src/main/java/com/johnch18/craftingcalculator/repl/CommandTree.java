package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.Ingredient;
import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;

public class CommandTree implements ICommand {

    @Override
    public String hook() {
        return "tree";
    }

    @Override
    public String helpString() {
        return "Prints the crafting tree of the provided ingredient.";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        Ingredient ingredient;
        if (args.length < 1) {
            repl.getDialog().printf("You need an ingredient.\n");
            return;
        }
        try {
            ingredient = new Ingredient(args[0]);
        } catch (CCInvalidIngredientString e) {
            e.printStackTrace();
            repl.getDialog().printf("Invalid ingredient: '%s'\n", args[0]);
            return;
        }
        repl.getDialog().printf("%s\n", ingredient.getTree().render());
    }

}
