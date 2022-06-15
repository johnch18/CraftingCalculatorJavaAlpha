package com.johnch18.craftingcalculator.interfaces;

import org.json.JSONObject;

public interface ISerializable {

    void deserialize(JSONObject json);

    JSONObject serialize();

}
