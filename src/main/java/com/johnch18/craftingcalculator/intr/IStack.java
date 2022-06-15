package com.johnch18.craftingcalculator.intr;

public interface IStack extends ISerializable {

    /*
    * Stores a group of ITargets, and their chance of being produced.
    * */

    // Stores the provided target
    ITarget getTarget();
    // Sets target
    void setTarget(ITarget target);
    // Stores the amount produced
    int getAmount();
    // Sets amount
    void setAmount(int amount);
    // Stores the odds of getting the amount prescribed
    double getChance();
    // Get chance
    void setChance(double chance);
    // Reflects getAmount * getChance
    int getEffectiveAmount();
    // Transfers the balance from one stack to the other
    void transferFrom(IStack stack);

}
