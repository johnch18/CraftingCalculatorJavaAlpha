package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.Utility;

import java.io.FileNotFoundException;

public class CommandManual implements Command {

    public static final String manualPath = "./src/main/resources/manual.md";
    public final String manualText = Utility.readFile(manualPath);

    public CommandManual() throws FileNotFoundException {
    }


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
        repl.getDialog().printf("%s\n", manualText);
    }

}
