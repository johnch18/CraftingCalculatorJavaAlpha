package com.johnch18.craftingcalculator.factories;

import com.johnch18.craftingcalculator.impl.Target;
import com.johnch18.craftingcalculator.intr.ITarget;
import org.json.JSONObject;

public class TargetFactory {

    public static ITarget deserialize(JSONObject json) {
        String name = json.getString("name");
        int metadata = json.optInt("metadata", 0);
        String type = json.optString("type", "null");
        switch (type) {
            default: {
                return new Target(name, metadata);
            }
        }
    }

}
