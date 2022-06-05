package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;
import com.johnch18.craftingcalculator.exceptions.CCNullPtrException;
import com.johnch18.craftingcalculator.exceptions.CCRecursionException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class Recipe {
    //
    private final IngredientList inputs;
    private final IngredientList outputs;
    private boolean isEnabled;

    /*
     * Methods
     * */

    public Recipe() {
        inputs = new IngredientList();
        outputs = new IngredientList();
        isEnabled = true;
    }

    public Recipe(Ingredient[] outputs, Ingredient[] inputs) throws CCInvalidIngredientString {
        this(new IngredientList(outputs), new IngredientList(inputs));
    }

    public Recipe(String[] outputs, String[] inputs) throws CCInvalidIngredientString {
        this(new IngredientList(outputs), new IngredientList(inputs));
    }


    public Recipe(IngredientList outputs, IngredientList inputs) {
        this();
        for (Map.Entry<String, Ingredient> entry: outputs.getIterator())
            addOutput(entry.getValue());
        for (Map.Entry<String, Ingredient> entry: inputs.getIterator())
            addInput(entry.getValue());
    }

    public Node getTree(Ingredient ingredient) {
        Node node = new Node(ingredient);
        node.generateChildren();
        return node;
    }

    public Ingredient getOutputIngredient(Ingredient ingredient) {
        String name = ingredient.getComponent().getName();
        return outputs.getMapping().get(name);
    }

    public void addInput(Ingredient ingredient) {
        inputs.addIngredient(ingredient);
    }

    public void addOutput(Ingredient ingredient) {
        outputs.addIngredient(ingredient);
        ingredient.getComponent().addRecipe(this);
    }

    public CostResult getCost(Ingredient target) throws CCRecursionException, CCNullPtrException {
        return getCost(target, new IngredientList());
    }

    public CostResult getCost(
            Ingredient target,
            String[] preCache
    ) throws CCInvalidIngredientString, CCRecursionException, CCNullPtrException {
        return getCost(target, new IngredientList(preCache));
    }

    public CostResult getCost(
            Ingredient target,
            IngredientList preCache
    ) throws CCRecursionException, CCNullPtrException {
        CostResult result = new CostResult();
        result.getExcess().combineWith(preCache);
        RecipeAlgorithm.getCostRecursive(this, target, result);
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("< ");
        for (Map.Entry<String, Ingredient> entry : inputs.getIterator()) {
            result.append(entry.getValue().toString());
            result.append(" ");
        }
        result.append("-> ");
        for (Map.Entry<String, Ingredient> entry : outputs.getIterator()) {
            result.append(entry.getValue().toString());
            result.append(" ");
        }
        result.append(">");
        return result.toString();
    }

    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        JSONArray outputList = new JSONArray();
        JSONArray inputList = new JSONArray();
        //
        for (Map.Entry<String, Ingredient> entry: outputs.getIterator())
            outputList.put(entry.getValue().toString());
        for (Map.Entry<String, Ingredient> entry: inputs.getIterator())
            inputList.put(entry.getValue().toString());
        //
        result.put("outputs", outputList);
        result.put("inputs", inputList);
        result.put("enabled", isEnabled());
        return result;
    }

    public static Recipe deserialize(JSONObject object) throws CCInvalidIngredientString {
        JSONArray outputList = object.optJSONArray("outputs");
        JSONArray inputList = object.optJSONArray("inputs");
        boolean enabled = object.optBoolean("enabled", true);
        //
        IngredientList outputs = new IngredientList();
        IngredientList inputs = new IngredientList();
        //
        for (Object obj : outputList)
            outputs.addIngredient(new Ingredient((String)obj));
        for (Object obj : inputList)
            inputs.addIngredient(new Ingredient((String)obj));
        //
        Recipe result = new Recipe(outputs, inputs);
        result.setEnabled(enabled);
        return result;
    }

    /*
     * Getters and setters
     * */

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public IngredientList getInputs() {
        return inputs;
    }


    public IngredientList getOutputs() {
        return outputs;
    }

}
