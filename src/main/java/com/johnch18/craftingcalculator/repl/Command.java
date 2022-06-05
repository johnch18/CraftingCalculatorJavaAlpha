package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;

import java.io.FileNotFoundException;

public interface Command {

    String hook();

    String helpString();

    void execute(RecipeREPL repl, String[] args);

}
