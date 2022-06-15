package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.intr.IInventory;
import com.johnch18.craftingcalculator.intr.IStack;
import com.johnch18.craftingcalculator.intr.ITarget;

import java.util.HashMap;
import java.util.Map;

public class Inventory implements IInventory {

    private final Map<ITarget, IStack> stacks = new HashMap<>();

    @Override
    public Map<ITarget, IStack> getStacks() {
        return stacks;
    }

    @Override
    public void add(IStack stack) {
        if (contains(stack)) {
            get(stack).transferFrom(stack);
        } else {
            stacks.put(stack.getTarget(), stack);
        }
    }

    @Override
    public boolean contains(ITarget target) {
        return stacks.containsKey(target);
    }

    @Override
    public boolean contains(IStack stack) {
        return contains(stack.getTarget());
    }

    @Override
    public IStack get(ITarget target) {
        return null;
    }

    @Override
    public IStack get(IStack stack) {
        return null;
    }

}
