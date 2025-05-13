package me.shae.shadow.events.impl.render;

import me.shae.shadow.events.api.Event;
import net.minecraft.src.ScaledResolution;

public record ScreenEvent(ScaledResolution scaledResolution) implements Event {
}
