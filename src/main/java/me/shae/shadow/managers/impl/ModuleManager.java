package me.shae.shadow.managers.impl;

import com.google.gson.JsonObject;
import me.shae.shadow.Shadow;
import me.shae.shadow.events.api.Handler;
import me.shae.shadow.events.api.Listener;
import me.shae.shadow.events.api.SubscribeEvent;
import me.shae.shadow.events.impl.game.InitializeEvent;
import me.shae.shadow.events.impl.game.KeyPressEvent;
import me.shae.shadow.events.impl.game.ShutdownEvent;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.api.mode.ModuleMode;
import me.shae.shadow.features.modules.api.settings.Setting;
import me.shae.shadow.features.modules.api.settings.impl.ModeSetting;
import me.shae.shadow.managers.api.Manager;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.utils.config.FileUtil;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public final class ModuleManager extends Manager<Class<? extends ModuleBase>, ModuleBase> implements Handler {

    private final Map<ModuleBase, List<Setting<?>>> settings = new HashMap<>();

    public ModuleManager() {
        this.loadModules();

        ManagerFactory.get(EventManager.class).registerHandler(this);
    }

    public void loadModules() {
        Set<Class<? extends ModuleBase>> discovered = new Reflections("me.shae.shadow.features.modules.impl").getSubTypesOf(ModuleBase.class);

        for (Class<? extends ModuleBase> clazz : discovered) {
            try {
                ModuleBase module = clazz.getDeclaredConstructor().newInstance();
                add(clazz, module);
                ManagerFactory.get(EventManager.class).registerHandler(module);

                for (Field declaredField : module.getClass().getDeclaredFields()) {
                    if (declaredField.getType().getSuperclass() != null && declaredField.getType().getSuperclass().equals(Setting.class)) {
                        if (!declaredField.trySetAccessible()) {
                            continue;
                        }

                        try {
                            this.registerSetting(module, (Setting<?>) declaredField.get(module));

                            if (declaredField.get(module) instanceof ModeSetting) {
                                for (ModuleMode<?> mode : ((ModeSetting) declaredField.get(module)).getModes()) {
                                    mode.getSettings().forEach(property -> this.registerSetting(module, property));
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void initialize() {
        final String defaultConfig = FileUtil.readFile("default");

        if (defaultConfig != null) {
            JsonObject object = Shadow.getInstance().getGson().fromJson(defaultConfig, JsonObject.class);

            for (ModuleBase module : this.map.values()) {
                if (object.has(module.getName())) {
                    module.deserialize(object.get(module.getName()).getAsJsonObject());
                }
            }
        }
    }

    public void shutdown() {
        final JsonObject object = new JsonObject();

        for (ModuleBase module : this.map.values()) {
            object.add(module.getName(), module.serialize());
        }

        FileUtil.writeFile("default", object.toString());
    }

    public ModuleBase getByName(String name) {
        for (ModuleBase module : map.values()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public List<ModuleBase> getEnabledModules() {
        return map.values().stream()
                .filter(ModuleBase::isEnabled)
                .collect(Collectors.toList());
    }

    public List<ModuleBase> getModulesByCategory(ModuleCategory category) {
        return map.values().stream()
                .filter(m -> m.getCategory() == category)
                .collect(Collectors.toList());
    }

    public Collection<ModuleBase> getAllModules() {
        return map.values();
    }

    public void registerSetting(final ModuleBase module, final Setting<?> setting) {
        settings.putIfAbsent(module, new ArrayList<>());
        settings.get(module).add(setting);
    }

    public List<Setting<?>> getSettingsByModule(ModuleBase module) {
        return settings.getOrDefault(module, Collections.emptyList());
    }

    @SubscribeEvent
    private final Listener<KeyPressEvent> onKeyPress = event -> {
        final int key = event.keyBind();

        for (ModuleBase module : this.map.values()) {
            if (module.getKeyBind() == key) {
                module.setEnabled(!module.isEnabled());
            }
        }
    };

    @SubscribeEvent
    private final Listener<InitializeEvent> onInitialize = event -> this.initialize();

    @SubscribeEvent
    private final Listener<ShutdownEvent> onShutdown = event -> this.shutdown();
}
