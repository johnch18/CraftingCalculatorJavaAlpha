package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.intr.IInventory;
import com.johnch18.craftingcalculator.intr.IRecipe;
import com.johnch18.craftingcalculator.intr.IStack;
import com.johnch18.craftingcalculator.intr.ITarget;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class Recipe implements IRecipe {

    private final IInventory inputs = new Inventory();
    private final IInventory outputs = new Inventory();
    private boolean enabled;

    @Override
    public IInventory getInputs() {
        return inputs;
    }

    @Override
    public IInventory getOutputs() {
        return outputs;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void addInput(IStack stack) {
        inputs.add(stack);
    }

    @Override
    public void addOutput(IStack stack) {
        outputs.add(stack);
    }

    @Override
    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        //
        JSONArray inputArray = new JSONArray();
        JSONArray outputArray = new JSONArray();
        boolean enabled = isEnabled();
        //
        for (Map.Entry<ITarget, IStack> entry: inputs.getStacks().entrySet())
            inputArray.put(entry.getValue().serialize());
        for (Map.Entry<ITarget, IStack> entry: outputs.getStacks().entrySet())
            outputArray.put(entry.getValue().serialize());
        //
        result.put("inputs", inputArray);
        result.put("outputs", outputArray);
        result.put("enabled", enabled);
        //
        return result;
    }

}
