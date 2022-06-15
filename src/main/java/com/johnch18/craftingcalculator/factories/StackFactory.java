package com.johnch18.craftingcalculator.factories;

import com.johnch18.craftingcalculator.impl.Stack;
import com.johnch18.craftingcalculator.intr.IStack;
import com.johnch18.craftingcalculator.intr.ITarget;
import org.json.JSONObject;

public class StackFactory {

    public static IStack deserialize(JSONObject obj) {
        ITarget target = TargetFactory.deserialize(obj.getJSONObject("item"));
        int amount;
        double chance;
        //
        amount = obj.optInt("amount", 1);
        chance = obj.optDouble("chance", 1.0);
        switch (obj.optString("type", "null")) {
            default: {
                return new Stack(target, amount, chance);
            }
        }
    }

}
