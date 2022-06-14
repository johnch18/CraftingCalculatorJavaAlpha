package com.johnch18.craftingcalculator.impl;

import com.johnch18.craftingcalculator.IComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComponentTest {

    IComponent component;

    @BeforeEach
    void setUp() {
        component = new Component();
    }

    @Test
    void test_getIdentifier() {
        int i = component.getIdentifier();

        assertEquals(i, 0);
    }

}
