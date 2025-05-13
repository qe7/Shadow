package me.shae.shadow.features.modules.api.settings.impl;

import com.google.gson.JsonObject;
import me.shae.shadow.features.modules.api.settings.Setting;

public final class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(String name, String displayName, Boolean value) {
        super(name, displayName, value);
    }

    @Override
    public JsonObject serialize() {
        final JsonObject object = new JsonObject();

        object.addProperty("value", this.getValue());

        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        if (!object.has("value") || !object.get("value").isJsonPrimitive()) {
            return;
        }

        this.setValue(object.get("value").getAsBoolean());
    }
}
