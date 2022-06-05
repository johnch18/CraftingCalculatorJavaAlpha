package com.johnch18.craftingcalculator.repl;

public class CommandSave implements Command {

    @Override
    public String hook() {
        return "save";
    }

    @Override
    public String helpString() {
        return "Saves current state to the file listed, or to the file first loaded.";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        if (args.length == 0) {
            repl.saveBook(repl.getFileName());
        } else {
            repl.saveBook(args[0]);
        }
    }

}
