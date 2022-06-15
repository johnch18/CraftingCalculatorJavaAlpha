package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.intr.IStack;
import com.johnch18.craftingcalculator.intr.ITarget;
import org.json.JSONObject;

import static java.lang.Math.floor;

public class Stack implements IStack {

    private ITarget target;
    private int amount;
    private double chance;

    public Stack(ITarget target, int amount, double chance) {
        setTarget(target);
        setAmount(amount);
        setChance(chance);
    }

    @Override
    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        result.put("item", target.serialize());
        result.put("amount", amount);
        return result;
    }

    @Override
    public ITarget getTarget() {
        return null;
    }

    @Override
    public void setTarget(ITarget target) {
        this.target = target;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public double getChance() {
        return chance;
    }

    @Override
    public void setChance(double chance) {
        this.chance = chance;
    }

    @Override
    public int getEffectiveAmount() {
        return (int) floor(getAmount() * getChance());
    }

    @Override
    public void transferFrom(IStack stack) {
        this.amount -= stack.getAmount();
        stack.setAmount(0);
    }

}
