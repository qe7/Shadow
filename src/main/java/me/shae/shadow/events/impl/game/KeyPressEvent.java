package me.shae.shadow.events.impl.game;

import me.shae.shadow.events.api.Event;

public record KeyPressEvent(int keyBind) implements Event {
}
