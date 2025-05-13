package me.shae.shadow.utils.config;

import com.google.gson.JsonObject;

public class JsonObjectBuilder {
    private final JsonObject object = new JsonObject();

    public JsonObjectBuilder add(String key, boolean value) {
        object.addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, int value) {
        object.addProperty(key, value);
        return this;
    }

    public boolean getBoolean(JsonObject obj, String key, boolean defaultValue) {
        return obj.has(key) && obj.get(key).isJsonPrimitive() && obj.get(key).getAsJsonPrimitive().isBoolean()
                ? obj.get(key).getAsBoolean()
                : defaultValue;
    }

    public int getInt(JsonObject obj, String key, int defaultValue) {
        return obj.has(key) && obj.get(key).isJsonPrimitive() && obj.get(key).getAsJsonPrimitive().isNumber()
                ? obj.get(key).getAsInt()
                : defaultValue;
    }

    public JsonObject build() {
        return object;
    }
}
