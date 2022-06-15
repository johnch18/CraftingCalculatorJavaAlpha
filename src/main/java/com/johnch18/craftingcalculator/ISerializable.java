package com.johnch18.craftingcalculator;

import org.json.JSONObject;

public interface ISerializable {

    void deserialize(JSONObject json);
    JSONObject serialize();

}
