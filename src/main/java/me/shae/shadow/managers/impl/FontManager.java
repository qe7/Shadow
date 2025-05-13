package me.shae.shadow.managers.impl;

import me.shae.shadow.events.api.Handler;
import me.shae.shadow.font.FontType;
import me.shae.shadow.font.renderer.TTFRenderer;
import me.shae.shadow.managers.api.Manager;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public final class FontManager extends Manager<String, TTFRenderer> implements Handler {

    public TTFRenderer getFontRenderer(FontType fontType, float size) {
        String key = getFontKey(fontType, size);
        if (!map.containsKey(key)) {
            map.put(key, createRenderer(fontType.getFontPath(), size));
        }
        return map.get(key);
    }

    private String getFontKey(FontType fontType, float size) {
        return fontType.getFontPath() + ":" + size;
    }

    private TTFRenderer createRenderer(String path, float size) {
        try {
            return new TTFRenderer(
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            Objects.requireNonNull(FontManager.class.getResourceAsStream("/assets/font/" + path + ".ttf"))
                    ).deriveFont(Font.PLAIN, size),
                    true
            );
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}