package me.shae.shadow.features.modules.api.settings.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.features.modules.api.settings.Setting;

@Getter
public final class IntSetting extends Setting<Integer> {

    private final int min, max, increment;

    @Setter
    private String unit = "";

    @Setter
    private Type type = Type.NONE;

    public IntSetting(String name, String displayName, int value, int min, int max, int increment) {
        super(name, displayName, value);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    @Getter
    public enum Type {
        PERCENTAGE("%"),
        ANGLE("°"),
        NONE("");

        private final String symbol;

        Type(final String symbol) {
            this.symbol = symbol;
        }
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

        this.setValue(object.get("value").getAsInt());
    }
}
