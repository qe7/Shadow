package me.shae.shadow.ui.components.impl;

import me.shae.shadow.features.modules.api.settings.impl.EnumSetting;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.font.FontType;
import me.shae.shadow.font.renderer.TTFRenderer;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.FontManager;
import me.shae.shadow.ui.components.SettingComponent;
import me.shae.shadow.utils.FontAdaptor;
import net.minecraft.src.Gui;

import java.awt.*;

public final class EnumComponent extends SettingComponent {

    private final EnumSetting<?> setting;

    private float positionX, positionY;

    private boolean hovering;

    public EnumComponent(EnumSetting<?> setting, int width, int height) {
        super(setting, width, height);
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, float x, float y) {
        this.positionX = x;
        this.positionY = y;

        this.hovering = isHovered(mouseX, mouseY);

        final Color disabledColor = this.hovering ? new Color(58, 56, 56, 150) : new Color(0, 0, 0, 150);

        Gui.drawRect(x, y, x + getWidth(), y + getHeight(), disabledColor.getRGB());
        FontAdaptor.renderString(setting.getDisplayName(), x + 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(setting.getDisplayName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
        FontAdaptor.renderString(String.valueOf(setting.getValue().getName()), x + getWidth() - FontAdaptor.getStringWidth(String.valueOf(setting.getValue().getName()), CustomFontModule.clickGuiCustomFont.getValue()) - 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(String.valueOf(setting.getValue().getName()), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
    }

    @Override
    public boolean keyPressed(char c, int i) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (hovering) {
            if (clickedButton == 0) {
                setting.cycleForward();
                return true;
            } else if (clickedButton == 1) {
                setting.cycleBackward();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= positionX && mouseX <= positionX + getWidth() && mouseY >= positionY && mouseY < positionY + getHeight();
    }

    @Override
    public float getTotalHeight() {
        return this.getHeight();
    }
}
