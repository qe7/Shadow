package me.shae.shadow.features.modules.api;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.events.api.Handler;
import me.shae.shadow.features.modules.api.settings.Setting;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.ModuleManager;
import me.shae.shadow.utils.IMinecraft;
import me.shae.shadow.utils.config.JsonObjectBuilder;
import me.shae.shadow.utils.config.Serialized;

@Getter
@Setter
public abstract class ModuleBase implements Toggleable, Serialized, Handler, IMinecraft {

    protected final String name;
    protected final String description;
    protected final ModuleCategory category;

    protected String suffix = "";

    protected boolean enabled;
    protected boolean drawn = true;
    protected boolean hidden = false;

    protected int keyBind = 0;

    public ModuleBase() {
        ModuleInfo info = this.getClass().getAnnotation(ModuleInfo.class);
        if (info == null) {
            throw new IllegalStateException("ModuleBase is missing @ModuleInfo annotation: " + getClass().getSimpleName());
        }

        this.name = info.name();
        this.description = info.description();
        this.category = info.category();
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    @Override
    public void toggle() {
        setEnabled(!this.enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean listening() {
        return this.enabled;
    }

    @Override
    public JsonObject serialize() {
        JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();

        jsonObjectBuilder.add("enabled", isEnabled()).add("drawn", isDrawn()).add("keyBind", getKeyBind());

        if (!ManagerFactory.get(ModuleManager.class).getSettingsByModule(this).isEmpty()) {
            final JsonObject settings = new JsonObject();

            for (Setting<?> setting : ManagerFactory.get(ModuleManager.class).getSettingsByModule(this)) {
                settings.add(setting.getClass().getSimpleName(), setting.serialize());
            }

            jsonObjectBuilder.build().add("settings", settings);
        }

        return jsonObjectBuilder.build();
    }

    @Override
    public void deserialize(JsonObject object) {
        this.setEnabled(object.get("enabled").getAsBoolean());
        this.setDrawn(object.get("drawn").getAsBoolean());
        this.setKeyBind(object.get("keyBind").getAsInt());

        if (object.has("settings")) {
            final JsonObject settings = object.getAsJsonObject("settings");

            for (Setting<?> setting : ManagerFactory.get(ModuleManager.class).getSettingsByModule(this)) {
                if (settings.has(setting.getName())) {
                    try {
                        setting.deserialize(settings.getAsJsonObject(setting.getName()));
                    } catch (Exception e) {

                    }
                }
            }
        }
    }
}
