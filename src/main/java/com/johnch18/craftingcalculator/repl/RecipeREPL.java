package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.RecipeBook;
import com.johnch18.craftingcalculator.exceptions.CCExceptionNonCritical;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecipeREPL {

    public static final Map<String, Command> commands = new HashMap<>();
    private final REPLDialog dialog;
    private RecipeBook recipeBook;
    private String fileName;
    private boolean running;

    /*
     * Methods
     * */

    public RecipeREPL(String fileName) throws FileNotFoundException {
        setFileName(fileName);
        setRunning(true);
        loadCommands();
        dialog = new REPLDialog(this);
        loadBookFromFile();
    }

    private static void addCommand(Command command) {
        commands.put(command.hook(), command);
    }

    public void loadCommands() throws FileNotFoundException {
        addCommand(new CommandExit());
        addCommand(new CommandHelp());
        addCommand(new CommandManual());
        addCommand(new CommandSave());
        addCommand(new CommandLoad());
        addCommand(new CommandListComponents());
        addCommand(new CommandListRecipes());
        addCommand(new CommandAddComponent());
        addCommand(new CommandAddRecipe());
        addCommand(new CommandCost());
        addCommand(new CommandTree());
    }

    public void loadBookFromFile() {
        loadBookFromFile(fileName);
    }

    public void loadBookFromFile(String fileName) {
        try {
            recipeBook = RecipeBook.loadBookFromFile(fileName);
        } catch (IOException e) {
            dialog.printf("Unable to open '%s'.", fileName);
            e.printStackTrace();
        } catch (CCExceptionNonCritical e) {
            dialog.printf("An error occurred\n");
            e.printStackTrace();
        }
    }

    public void repl() {
        dialog.printf("" + // IntelliJ t'ings
                "Welcome to the crafting calculator CLI." +
                " Please type 'help', 'help `command`', or 'manual' if you get stuck.\n");
        while (running) {
            String command = dialog.read();
            eval(command);
            // Print implicit
            // Loop implicit
        }
    }

    public void eval(String toParse) {
        String[] split = toParse.split(" ");
        if (split.length < 1)
            return;
        String command = split[0];
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        if (commands.containsKey(command)) {
            commands.get(command).execute(this, args);
        } else {
            dialog.printf("'%s' is not a valid command.\n");
        }
    }

    public void quit() {
        if (recipeBook.isDirty())
            dialog.unsavedChangesDialog();
        exit();
    }

    public void saveBook(String fileName) {
        recipeBook.dumpBookToFile(fileName);
    }

    public void exit() {
        setRunning(false);
    }

    /*
     * Getters and Setters
     * */

    public RecipeBook getRecipeBook() {
        return recipeBook;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public REPLDialog getDialog() {
        return dialog;
    }

}
