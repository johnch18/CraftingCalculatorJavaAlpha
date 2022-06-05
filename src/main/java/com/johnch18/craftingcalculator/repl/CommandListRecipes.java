package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.Recipe;

public class CommandListRecipes implements Command {

    @Override
    public String hook() {
        return "ls-recipes";
    }

    @Override
    public String helpString() {
        return "Lists all recipes in the recipe book.";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        for (Recipe recipe : repl.getRecipeBook().getRecipes()) {
            repl.getDialog().printf("%s\n", recipe.toStringFancy());
        }
    }

}
