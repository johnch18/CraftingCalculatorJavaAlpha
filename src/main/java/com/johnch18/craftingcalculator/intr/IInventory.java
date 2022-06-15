package com.johnch18.craftingcalculator.intr;

import java.util.Map;

public interface IInventory {

    /*
     * Stores and manages a group of items, combines stacks and prevents overuse.
     * */

    /* Gets the stacks stored, would have used a hashed set, but you can't actually get the data stored in Java :].
     * Honestly feels like the designers forgot that not every mutable aspect of an object is relevant to comparisons.
     * Have to use the already embedded 'target' field inside IStack because that's the only part I care about.
     * I'm not a Java Expert (tm) so feel free to correct me on this.
     */
    Map<ITarget, IStack> getStacks();
    // Adds a stack
    void add(IStack stack);
    // Checks if inventory contains target
    boolean contains(ITarget target);
    // Checks if inventory contains stack
    boolean contains(IStack stack);
    //
    IStack get(ITarget target);
    //
    IStack get(IStack stack);

}
