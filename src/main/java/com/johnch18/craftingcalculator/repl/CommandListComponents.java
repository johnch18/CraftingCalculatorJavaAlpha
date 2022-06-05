package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.Component;

public class CommandListComponents implements Command {

    @Override
    public String hook() {
        return "ls-components";
    }

    @Override
    public String helpString() {
        return "Prints a list of components.";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        for (Component component: Component.getComponents()) {
            repl.getDialog().printf("%s ('%s'", component.getFancyName(), component.getName());
            if (component.isFluid())
                repl.getDialog().printf(", Fluid)");
            else
                repl.getDialog().printf(")");
            repl.getDialog().printf("\n");
        }
    }

}
