package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.interfaces.IComponent;
import com.johnch18.craftingcalculator.interfaces.IIngredient;
import org.json.JSONObject;

public class Ingredient implements IIngredient {

    private final IComponent component;
    private int amount;

    public Ingredient() {
        component = new Component();
        amount = 0;
    }

    @Override
    public IComponent getComponent() {
        return component;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int getEffectiveAmount() {
        return getAmount();
    }

    @Override
    public void add(IIngredient ingredient) {
        add(ingredient.getAmount());
    }

    @Override
    public void add(int amount) {
        this.amount += amount;
    }

    @Override
    public void subtract(IIngredient ingredient) {
        subtract(ingredient.getAmount());
    }

    @Override
    public void subtract(int amount) {
        this.amount -= amount;
    }

    @Override
    public IIngredient transferAllFrom(IIngredient other) {
        return transferFrom(other, other.getAmount());
    }

    @Override
    public IIngredient transferFrom(IIngredient other, int amount) {
        add(amount);
        other.subtract(amount);
        return this;
    }

    @Override
    public void deserialize(JSONObject json) {
        component.deserialize(json.getJSONObject("item"));
        amount = json.optInt("amount", 1);
    }

    @Override
    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        result.put("item", component.serialize());
        result.put("amount", getAmount());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(amount);
        sb.append("x ");
        sb.append(component.toString());
        return sb.toString();
    }

}
