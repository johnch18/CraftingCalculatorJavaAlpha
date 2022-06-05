package com.johnch18.craftingcalculator.repl;

public interface Command {

    String hook();

    String helpString();

    void execute(RecipeREPL repl, String[] args);

}
