package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.RecipeBook;
import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;

import java.io.IOException;

public class CommandLoad implements ICommand {

    @Override
    public String hook() {
        return "load";
    }

    @Override
    public String helpString() {
        return "Loads from the specified file(s).\n" +
                "WARNING: Loading multiple files could result in weirdness " +
                "and conflicting recipes, you've been warned.";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        if (args.length == 0) {
            repl.getDialog().printf("No files supplied.");
            return;
        }
        for (String file: args) {
            try {
                RecipeBook.loadBookFromFile(repl.getRecipeBook(), file);
            } catch (IOException e) {
                e.printStackTrace();
                repl.getDialog().printf("Unable to open file '%s'.\n", file);
            } catch (CCInvalidIngredientString e) {
                e.printStackTrace();
                repl.getDialog().printf("'%s' contained malformed ingredient strings.\n", file);
            }
        }
    }

}
