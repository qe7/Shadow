package me.shae.shadow.features.modules.api.settings.impl;

import com.google.gson.JsonObject;
import me.shae.shadow.features.modules.api.settings.Setting;

import java.awt.*;

public final class ColorSetting extends Setting<Color> {

    public ColorSetting(String name, String displayName, Color value) {
        super(name, displayName, value);
    }

    @Override
    public JsonObject serialize() {
        final JsonObject object = new JsonObject();

        object.addProperty("red", this.getValue().getRed());
        object.addProperty("green", this.getValue().getGreen());
        object.addProperty("blue", this.getValue().getBlue());
        object.addProperty("alpha", this.getValue().getAlpha());

        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        if (!object.has("red") || !object.has("green") || !object.has("blue") || !object.has("alpha")) {
            return;
        }

        this.setValue(new Color(object.get("red").getAsInt(), object.get("green").getAsInt(), object.get("blue").getAsInt(), object.get("alpha").getAsInt()));
    }
}
