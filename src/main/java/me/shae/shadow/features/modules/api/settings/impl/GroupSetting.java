package me.shae.shadow.features.modules.api.settings.impl;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.features.modules.api.settings.Setting;

public final class GroupSetting extends Setting<Boolean> {

    @Getter
    @Setter
    private boolean drawn;

    public GroupSetting(String name, String displayName, Boolean value) {
        super(name, displayName, value);
    }

    @Override
    public JsonObject serialize() {
        return new JsonObject();
    }

    @Override
    public void deserialize(JsonObject object) {

    }
}
