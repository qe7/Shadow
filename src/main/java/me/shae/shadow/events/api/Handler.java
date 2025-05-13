package me.shae.shadow.events.api;

public interface Handler {
    default boolean listening() {
        return true;
    }
}
