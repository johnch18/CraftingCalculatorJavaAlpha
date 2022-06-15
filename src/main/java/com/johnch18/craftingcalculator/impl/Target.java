package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.intr.IRecipe;
import com.johnch18.craftingcalculator.intr.ITarget;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Target implements ITarget {
    private final String name;
    private final int metadata;
    private final List<IRecipe> recipes = new ArrayList<>();

    public Target(String name, int metadata) {
        this.name = name;
        this.metadata = metadata;
    }

    @Override
    public JSONObject serialize() {
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("metadata", metadata);
        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMetadata() {
        return metadata;
    }

    @Override
    public List<IRecipe> getRecipes() {
        return recipes;
    }

}
