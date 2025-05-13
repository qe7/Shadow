package me.shae.shadow.events.api;

public interface Listener<T extends Event> {
    void call(T event);
}
