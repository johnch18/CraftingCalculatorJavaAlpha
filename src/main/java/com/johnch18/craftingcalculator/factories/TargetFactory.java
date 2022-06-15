package com.johnch18.craftingcalculator.factories;

import com.johnch18.craftingcalculator.impl.Target;
import com.johnch18.craftingcalculator.intr.ITarget;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TargetFactory {

    private final static Map<ITarget, ITarget> targetMap = new HashMap<>();

    public static ITarget deserialize(JSONObject json) {
        String name = json.getString("name");
        int metadata = json.optInt("metadata", 0);
        String type = json.optString("type", "null");
        ITarget target;
        switch (type) {
            default: {
                target = new Target(name, metadata);
                targetMap.put(target, target);
                break;
            }
        }
        if (targetMap.containsKey(target))
            return targetMap.get(target);
        return target;
    }

}
