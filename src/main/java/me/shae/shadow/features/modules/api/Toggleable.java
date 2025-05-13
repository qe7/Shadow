package me.shae.shadow.features.modules.api;

public interface Toggleable {
    void setEnabled(boolean enabled);
    boolean isEnabled();
    void toggle();

    default void onEnable() {}
    default void onDisable() {}
}
