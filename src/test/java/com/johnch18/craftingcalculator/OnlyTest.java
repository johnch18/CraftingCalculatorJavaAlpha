package com.johnch18.craftingcalculator;

import com.johnch18.craftingcalculator.impl.RecipeBook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OnlyTest {

    @BeforeAll
    static void preInit() {

    }

    @BeforeEach
    void init() {

    }

    @Test
    void test() {
        RecipeBook recipeBook = new RecipeBook();
        recipeBook.loadRecipesFromFile("./test'.json");
        recipeBook.dumpRecipesToFile("./test.json");
    }

}
