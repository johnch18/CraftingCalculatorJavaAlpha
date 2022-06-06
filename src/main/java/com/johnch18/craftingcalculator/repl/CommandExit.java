package com.johnch18.craftingcalculator.repl;

public class CommandExit implements ICommand {

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        if (args.length == 0) {
            repl.quit();
        } else if (args.length == 1) {
            if (args[0].equals("now")) {
                repl.exit();
            } else {
                repl.getDialog().printf("Invalid parameter for %s '%s'.\n", hook(), args[0]);
            }
        } else {
            repl.getDialog().printf("Too many parameters to %s.\n", hook());
        }
    }

    @Override
    public String hook() {
        return "exit";
    }

    @Override
    public String helpString() {
        // TODO:
        return "Exits from the program. Adding 'now' as an argument skips any saving dialogs.";
    }

}
