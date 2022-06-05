package com.johnch18.craftingcalculator;

public class CostResult {

    private IngredientList cost, excess;

    /*
    * Methods
    * */

    public CostResult() {
        cost = new IngredientList();
        excess = new IngredientList();
    }

    /*
    * Getters and Setters
    * */

    public IngredientList getCost() {
        return cost;
    }

    public IngredientList getExcess() {
        return excess;
    }

}
