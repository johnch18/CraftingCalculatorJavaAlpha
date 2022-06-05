package com.johnch18.craftingcalculator.repl;

import java.util.Scanner;

public class REPLDialog {

    private final RecipeREPL repl;
    private final Scanner scanner;

    public REPLDialog(RecipeREPL repl) {
        this.repl = repl;
        scanner = new Scanner(System.in);
    }

    public RecipeREPL getRepl() {
        return repl;
    }

    public void unsavedChangesDialog() {
        printf("You have unsaved changes, if you would like to save them" +
                "enter the name of the file you would like to save to.\n");
        printf("Alternatively, enter a blank line in order to not save.:");
        save();
    }

    public void save() {
        String fileName = read("");
        if (!fileName.equals("")) {
            repl.saveBook(fileName);
            printf("Successfully wrote out to '%s'.\n", fileName);
        }
    }

    public void printf(String fmt, Object... objects) {
        System.out.printf(fmt, objects);
    }

    public String read() {
        return read(">");
    }

    public String read(String prompt) {
        printf("%s ", prompt);
        return scanner.nextLine();
    }

}
