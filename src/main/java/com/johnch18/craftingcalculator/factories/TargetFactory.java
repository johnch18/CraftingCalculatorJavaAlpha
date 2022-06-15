package com.johnch18.craftingcalculator.factories;

import com.johnch18.craftingcalculator.impl.Target;
import com.johnch18.craftingcalculator.intr.ITarget;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TargetFactory {

    private final static List<ITarget> targetList = new ArrayList<>();

    public static ITarget deserialize(JSONObject json) {
        String name = json.getString("name");
        int metadata = json.optInt("metadata", 0);
        String type = json.optString("type", "null");
        ITarget target;
        switch (type) {
            default: {
                target = new Target(name, metadata);
            }
        }
        if (targetList.contains(target))
            return targetList.get(targetList.indexOf(targetList));
        return target;
    }

}
