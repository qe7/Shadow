package me.shae.shadow.utils;

import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.events.api.Handler;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.font.renderer.TTFRenderer;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.FontManager;
import net.minecraft.src.FontRenderer;

import java.awt.*;

public final class FontAdaptor implements IMinecraft, Handler {

    @Getter
    @Setter
    private static FontRenderer fontRenderer = mc.fontRenderer;

    @Getter
    @Setter
    private static TTFRenderer ttfRenderer = ManagerFactory.get(FontManager.class).getFontRenderer(CustomFontModule.fontSetting.getValue().getFontType(), CustomFontModule.fontSize.getValue());

    public static void renderString(String string, float x, float y, Color color, boolean customFont) {
        if (customFont) {
            ttfRenderer.drawStringWithShadow(string, x, y, color.getRGB());
        } else {
            fontRenderer.drawStringWithShadow(string, x, y, color.getRGB());
        }
    }

    public static float getStringHeight(String string, boolean customFont) {
        if (customFont) {
            return ttfRenderer.getHeight(string);
        } else {
            return 9;
        }
    }

    public static float getStringWidth(String string, boolean customFont) {
        if (customFont) {
            return ttfRenderer.getWidth(string);
        } else {
            return fontRenderer.getStringWidth(string);
        }
    }
}
