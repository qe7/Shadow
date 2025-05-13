package me.shae.shadow.events.api;

public interface CancellableEvent extends Event {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}