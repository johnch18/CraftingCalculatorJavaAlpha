package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.RecipeBook;
import com.johnch18.craftingcalculator.exceptions.CCExceptionNonCritical;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RecipeREPL {

    public static final Map<String, Command> commands = new HashMap<>();
    private final Scanner scanner;
    private RecipeBook recipeBook;
    private String fileName;
    private boolean running;

    /*
     * Methods
     * */

    public RecipeREPL(String fileName) {
        setFileName(fileName);
        setRunning(true);
        loadCommands();
        scanner = new Scanner(System.in);
        loadBookFromFile();
    }

    public void loadCommands() {
        addCommand(new CommandExit());
        addCommand(new CommandHelp());
    }

    private static void addCommand(Command command) {
        commands.put(command.hook(), command);
    }

    public void loadBookFromFile() {
        loadBookFromFile(fileName);
    }

    public void loadBookFromFile(String fileName) {
        try {
            recipeBook = RecipeBook.loadBookFromFile(fileName);
        } catch (IOException e) {
            printf("Unable to open '%s'.", fileName);
            e.printStackTrace();
        } catch (CCExceptionNonCritical e) {
            printf("An error occurred\n");
            e.printStackTrace();
        }
    }

    public void repl() {
        printf("Welcome to the crafting calculator CLI. Please type `help` or `help command` if you get stuck.\n");
        while (running) {
            String command = read();
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
            printf("'%s' is not a valid command.\n");
        }
    }

    public void quit() {
        if (recipeBook.isDirty())
            unsavedChangesDialog();
        exit();
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
            saveBook(fileName);
            printf("Successfully wrote out to '%s'.\n", fileName);
        }
    }

    public void saveBook(String fileName) {
        recipeBook.dumpBookToFile(fileName);
    }

    public void exit() {
        setRunning(false);
    }

    public String read() {
        return read(">");
    }

    public String read(String prompt) {
        printf("%s ", prompt);
        return scanner.nextLine();
    }

    public void printf(String fmt, Object... objects) {
        System.out.printf(fmt, objects);
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

}
