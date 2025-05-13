package me.shae.shadow.features.modules.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
@Getter
public enum ModuleCategory {
    COMBAT("Combat", Color.RED),
    MISC("Misc", Color.CYAN),
    MOVEMENT("Movement", Color.GREEN),
    RENDER("Render", Color.MAGENTA),
    EXPLOIT("Exploit", Color.PINK),
    CLIENT("Client", Color.ORANGE);

    private final String name;
    private final Color color;
}