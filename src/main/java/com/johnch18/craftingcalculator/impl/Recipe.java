package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.interfaces.IComponent;
import com.johnch18.craftingcalculator.interfaces.IIngredient;
import com.johnch18.craftingcalculator.interfaces.IIngredientList;
import com.johnch18.craftingcalculator.interfaces.IRecipe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class Recipe implements IRecipe {

    private final IIngredientList inputs = new IngredientList();
    private final IIngredientList outputs = new IngredientList();
    private boolean enabled;

    @Override
    public IIngredientList getInputs() {
        return inputs;
    }

    @Override
    public IIngredientList getOutputs() {
        return outputs;
    }

    @Override
    public void addInput(IIngredient ingredient) {
        ingredient.getComponent().addRecipe(this);
        inputs.add(ingredient);
    }

    @Override
    public void addOutput(IIngredient ingredient) {
        ingredient.getComponent().addRecipe(this);
        outputs.add(ingredient);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void deserialize(JSONObject json) {
        JSONArray inputs = json.optJSONArray("inputs");
        JSONArray outputs = json.optJSONArray("outputs");
        for (Object _obj: inputs) {
            JSONObject obj = (JSONObject) _obj;
            Ingredient ingredient = new Ingredient();
            ingredient.deserialize(obj);
            this.inputs.add(ingredient);
        }
        for (Object _obj: outputs) {
            JSONObject obj = (JSONObject) _obj;
            Ingredient ingredient = new Ingredient();
            ingredient.deserialize(obj);
            this.outputs.add(ingredient);
        }
        this.enabled = json.optBoolean("enabled", true);
    }

    @Override
    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        JSONArray inputList = new JSONArray();
        JSONArray outputList = new JSONArray();
        boolean enabled = isEnabled();
        //
        for (Map.Entry<IComponent, IIngredient> ingredient: inputs.getIterator()) {
            JSONObject obj = ingredient.getValue().serialize();
            inputList.put(obj);
        }
        for (Map.Entry<IComponent, IIngredient> ingredient: outputs.getIterator()) {
            JSONObject obj = ingredient.getValue().serialize();
            outputList.put(obj);
        }
        //
        result.put("inputs", inputList);
        result.put("outputs", outputList);
        result.put("enabled", enabled);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<IComponent, IIngredient> entry: inputs.getIterator()) {
            sb.append(entry.getValue().toString());
            sb.append(" ");
        }
        sb.append("-> ");
        for (Map.Entry<IComponent, IIngredient> entry: outputs.getIterator()) {
            sb.append(entry.getValue().toString());
            sb.append(" ");
        }
        return sb.toString();
    }

}
