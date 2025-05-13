package me.shae.shadow.ui.components.impl;

import me.shae.shadow.features.modules.api.settings.impl.GroupSetting;
import me.shae.shadow.features.modules.impl.client.ColorModule;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.font.FontType;
import me.shae.shadow.font.renderer.TTFRenderer;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.FontManager;
import me.shae.shadow.ui.components.SettingComponent;
import me.shae.shadow.utils.FontAdaptor;
import net.minecraft.src.Gui;

import java.awt.*;

public final class GroupComponent extends SettingComponent {

    private final GroupSetting setting;

    private float positionX, positionY;

    private boolean hovering;

    public GroupComponent(GroupSetting setting, int width, int height) {
        super(setting, width, height);
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, float x, float y) {
        this.positionX = x;
        this.positionY = y;

        this.hovering = isHovered(mouseX, mouseY);

        final Color enabledColor = this.hovering ? ColorModule.getColorSetting().getValue().brighter() : ColorModule.getColorSetting().getValue();
        final Color disabledColor = this.hovering ? new Color(58, 56, 56, 150) : new Color(0, 0, 0, 150);

        Gui.drawRect(x, y, x + getWidth(), y + getHeight(), disabledColor.getRGB());
        Gui.drawRect(x + 1, y + 1, x + getWidth() - 1, y + getHeight() - 1, enabledColor.getRGB());
        FontAdaptor.renderString(setting.getDisplayName(), x + 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(setting.getDisplayName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
        FontAdaptor.renderString(setting.isDrawn() ? "." : "...", positionX + getWidth() - FontAdaptor.getStringWidth(setting.isDrawn() ? "." : "...", CustomFontModule.clickGuiCustomFont.getValue()) - 4, positionY + (((float) getHeight() / 2) - (FontAdaptor.getStringHeight(setting.getDisplayName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2)), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
    }

    @Override
    public boolean keyPressed(char c, int i) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (hovering) {
            if (clickedButton == 1) {
                setting.setDrawn(!setting.isDrawn());
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
