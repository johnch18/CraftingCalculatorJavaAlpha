package com.johnch18.craftingcalculator.repl;

public class CommandManual implements Command {

    @Override
    public String hook() {
        return "manual";
    }

    @Override
    public String helpString() {
        return "Displays the manual for the program.";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {

    }

}
