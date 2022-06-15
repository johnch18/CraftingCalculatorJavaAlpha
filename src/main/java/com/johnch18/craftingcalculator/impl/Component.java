package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.interfaces.IComponent;
import com.johnch18.craftingcalculator.interfaces.IIngredient;
import com.johnch18.craftingcalculator.interfaces.IRecipe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Component implements IComponent {

    private final List<IRecipe> recipes = new ArrayList<>();
    private String name;
    private int metadata;
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int metadata() {
        return metadata;
    }

    @Override
    public List<IRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public void addRecipe(IRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public void setMetadata(int metadata) {
        this.metadata = metadata;
    }

    @Override
    public void deserialize(JSONObject json) {
        setName(json.getString("name"));
        setMetadata(json.optInt("metadata", 0));
    }

    @Override
    public JSONObject serialize() {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("metadata", metadata);
        return object;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(":");
        sb.append(metadata);
        return sb.toString();
    }

}
