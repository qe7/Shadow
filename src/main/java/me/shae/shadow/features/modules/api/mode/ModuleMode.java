package me.shae.shadow.features.modules.api.mode;

import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.events.api.Handler;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ModuleMode<T extends ModuleBase> implements Handler {

    @Getter
    private final List<Setting<?>> settings = new ArrayList<>();

    protected final Minecraft mc = Minecraft.getMinecraft();

    @Getter
    protected final T module;
    @Setter
    private BooleanSupplier supplier;
    @Getter
    private final String name;
    private boolean lastState;

    public ModuleMode(T module, String name) {
        this.module = module;
        this.name = name;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    @Override
    public boolean listening() {
        boolean flag = module.isEnabled() && supplier.getAsBoolean();

        if (lastState != flag) {
            if (flag) {
                onEnable();
            } else {
                onDisable();
            }

            lastState = flag;
        }

        return flag;
    }
}
