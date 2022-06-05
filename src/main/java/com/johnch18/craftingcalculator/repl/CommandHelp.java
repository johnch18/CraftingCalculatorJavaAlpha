package com.johnch18.craftingcalculator.repl;

import java.util.Map;

public class CommandHelp implements Command {

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        Map<String, Command> commands = repl.getCommands();
        if (args.length == 0) {
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                execute(repl, new String[]{entry.getValue().hook()});
            }
        } else {
            for (String com : args) {
                if (commands.containsKey(com)) {
                    repl.printf("%s:\n -- %s\n\n", com, commands.get(com).helpString());
                } else {
                    repl.printf("'%s' is not a valid command.\n", com);
                }
            }
        }
    }

    public String hook() {
        return "help";
    }

    public String helpString() {
        // TODO:
        return "";
    }

}
