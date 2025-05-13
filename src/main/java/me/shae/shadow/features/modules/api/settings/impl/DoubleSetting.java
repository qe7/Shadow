package me.shae.shadow.features.modules.api.settings.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.features.modules.api.settings.Setting;

@Getter
public final class DoubleSetting extends Setting<Double> {

    private final double min, max, increment;

    @Setter
    private String unit;

    public DoubleSetting(String name, String displayName, double value, double min, double max, double increment) {
        super(name, displayName, value);
        this.min = min;
        this.max = max;
        this.increment = increment;
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

        this.setValue(object.get("value").getAsDouble());
    }
}
