package me.shae.shadow.features.modules.api.settings;

import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.utils.config.Serialized;

import java.util.function.BooleanSupplier;

public abstract class Setting<T> implements Serialized {
    @Getter
    private final String name;
    @Getter
    private final String displayName;
    @Setter
    @Getter
    private T value;

    private BooleanSupplier supplier;

    public Setting(String name, String displayName, T value) {
        this.name = name;
        this.displayName = displayName;
        this.value = value;
    }

    public boolean shouldHide() {
        return supplier != null && !supplier.getAsBoolean();
    }

    public <V extends Setting<?>> V supplyIf(BooleanSupplier supplier) {
        this.supplier = supplier;
        return (V) this;
    }
}
