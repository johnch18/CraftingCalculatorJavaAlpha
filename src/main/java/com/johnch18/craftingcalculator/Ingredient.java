package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.exceptions.CCInvalidIngredientString;

public class Ingredient {

    private Component component;
    private int amount;
    private double chance;
    private boolean enabled;

    /*
     * Methods
     * */

    public Ingredient() {
        setComponent(null);
        setAmount(1);
        setChance(1.0f);
        setEnabled(true);
    }


    public Ingredient(String ingredientString) throws CCInvalidIngredientString {
        this();
        if (ingredientString.contains(":"))
            initializeFromString(ingredientString);
        else
            setComponent(Component.getComponent(ingredientString));
    }

    public Ingredient(Component component) {
        this(component, 1, 1.0f);
    }

    public Ingredient(Component component, int amount) {
        this(component, amount, 1.0f);
    }

    public Ingredient(Component component, int amount, double chance) {
        setComponent(component);
        setAmount(amount);
        setChance(chance);
        setEnabled(true);
    }

    private void initializeFromString(String ingredientString) throws CCInvalidIngredientString {
        String[] parts = ingredientString.split(":");
        // Needs to be at least 1 long
        if (parts.length <= 0)
            throw new CCInvalidIngredientString("Invalid string");
        //
        String name = parts[0];
        int amount = 1;
        double chance = 1.0;
        boolean isFluid = false;
        // int metadata = 0;
        String amountString = null;
        String chanceString = null;
        // String metadataString = null;
        //
        if (parts.length > 1) amountString = parts[1];
        if (parts.length > 2) chanceString = parts[2];
        // TODO: Implement metadata handling
        // if (parts.length > 3) metadataString = parts[3];
        // Get the amount and determine if it's a fluid
        if (amountString != null) {
            if (amountString.contains("L")) {
                isFluid = true;
                amount = Integer.parseInt(amountString.substring(0, amountString.length() - 1));
            } else {
                amount = Integer.parseInt(amountString);
            }
        }
        // Get the chance and handle if it's a percentage
        if (chanceString != null) {
            if (chanceString.contains("%")) {
                chance = Double.parseDouble(chanceString.substring(0, chanceString.length() - 1)) / 100.0f;
            } else {
                chance = Double.parseDouble(chanceString);
            }
        }
        // TODO: Implement metadata handling
        // if (metadataString != null) metadata = Integer.parseInt(metadataString);
        //
        setComponent(Component.getComponent(name, isFluid));
        setAmount(amount);
        setChance(chance);
        // setMetadata(metadata);
    }

    public boolean isSameAs(Ingredient targetIngredient) {
        return component.isSameAs(targetIngredient.component);
    }


    public Ingredient add(int n) {
        return new Ingredient(getComponent(), getAmount() + n, getChance());
    }

    public void addInPlace(int n) {
        amount += n;
    }

    public Ingredient subtract(int n) {
        return new Ingredient(getComponent(), getAmount() - n, getChance());
    }

    public void subInPlace(int n) {
        amount -= n;
    }

    public Ingredient multiply(int n) {
        return new Ingredient(getComponent(), getAmount() * n, getChance());
    }

    public Ingredient divide(int n) {
        return new Ingredient(getComponent(), getAmount() / n, getChance());
    }

    public String toString() {
        String result = "";
        result += getComponentName();
        result += ":";
        result += String.valueOf(getAmount());
        if (getComponent().isFluid())
            result += "L";
        result += ":";
        result += String.valueOf(getChance());
        return result;
    }

    public String getComponentName() {
        if (component != null)
            return getComponent().getName();
        return null;
    }

    public int getEffectiveNumber() {
        return (int) (amount * chance);
    }

    /*
     * Getters and Setters
     * */

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isValid() {
        return component != null && amount > 0;
    }

    public String toStringFancy() {
        StringBuilder sb = new StringBuilder();
        if (amount > 0) {
            if (amount != 1 || component.isFluid()) {
                sb.append(amount);
                if (component.isFluid()) {
                    sb.append("L ");
                } else {
                    sb.append("x ");
                }
            }
            sb.append(getComponent().getFancyName());
        }
        return sb.toString();
    }

}
