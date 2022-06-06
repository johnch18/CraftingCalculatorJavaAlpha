package com.johnch18.craftingcalculator.repl;

import com.johnch18.craftingcalculator.CostResult;
import com.johnch18.craftingcalculator.Ingredient;
import com.johnch18.craftingcalculator.IngredientList;
import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;

import java.util.Arrays;
import java.util.Map;

public class CommandCost implements ICommand {

    @Override
    public String hook() {
        return "cost";
    }

    @Override
    public String helpString() {
        return "Gets the cost of an ingredient, factors in if you already have some of the inputs." +
                "Usage: 'cost `ingredient` [`ingredientsAvailable`...]'";
    }

    @Override
    public void execute(RecipeREPL repl, String[] args) {
        CostResult costResult;
        if (args.length < 1) {
            repl.getDialog().printf("You need to supply an ingredient.\n");
            return;
        }
        try {
            Ingredient target = new Ingredient(args[0]);
            String[] preCacheStrings = Arrays.copyOfRange(args, 1, args.length);
            IngredientList preCache = new IngredientList(preCacheStrings);
            costResult = target.getCost(preCache);
        } catch (CCInvalidIngredientString e) {
            e.printStackTrace();
            repl.getDialog().printf("Invalid ingredient\n");
            return;
        } catch (CCRecursionException e) {
            e.printStackTrace();
            repl.getDialog().printf("Recursion limit exceeded.\n");
            return;
        } catch (CCNullPtrException e) {
            e.printStackTrace();
            repl.getDialog().printf("Something really weird went wrong, please report this to author.\n");
            return;
        }
        repl.getDialog().printf("Inputs required:\n");
        for (Map.Entry<String, Ingredient> entry: costResult.getCost().getIterator()) {
            repl.getDialog().printf("    %s\n", entry.getValue().toStringFancy());
        }
        repl.getDialog().printf("Leftover Items:\n");
        for (Map.Entry<String, Ingredient> entry: costResult.getExcess().getIterator()) {
            repl.getDialog().printf("    %s\n", entry.getValue().toStringFancy());
        }
    }

}
