package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.impl.Recipe;
import com.johnch18.craftingcalculator.impl.RecipeBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Testing {

    RecipeBook recipes;

    @BeforeEach
    void setUp() {
        recipes = new RecipeBook();
        recipes.loadFromFile("./test.json");
    }

    @Test
    void test_serialize() {
        recipes.dumpToFile("./test2.json");
    }


}
