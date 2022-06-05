package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.Component;

import java.util.Arrays;

public class CommandAddComponent implements Command {

    @Override
    public String hook() {
        return "add-component";
    }

    @Override
    public String helpString() {
        return "Adds a component to the table. Usage: 'add-component `name` [`isFluid`, [`fancyName`]]'";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        if (args.length < 1) {
            repl.getDialog().printf("You need to supply at least a name.\n");
            return;
        }
        String name = args[0];
        boolean isFluid = false;
        String fancyName = name;
        if (args.length > 1) {
            String isFluidString = args[1];
            if (isFluidString.equals("true"))
                isFluid = true;
        }
        if (args.length > 2) {
            fancyName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        }
        Component.getComponent(name, isFluid, fancyName);
    }

}
