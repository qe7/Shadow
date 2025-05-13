package me.shae.shadow.ui.components.impl;

import me.shae.shadow.features.modules.api.settings.impl.IntSetting;
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

public final class IntComponent extends SettingComponent {

    private final IntSetting setting;

    private float positionX, positionY;

    private boolean hovering, dragging;

    public IntComponent(IntSetting setting, int width, int height) {
        super(setting, width, height);
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, float x, float y) {
        this.positionX = x;
        this.positionY = y;

        this.hovering = isHovered(mouseX, mouseY);

        if (dragging) {
            updateSlider(mouseX);
        }

        double percentage = (double) (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

        final Color enabledColor = this.hovering ? ColorModule.getColorSetting().getValue().brighter() : ColorModule.getColorSetting().getValue();
        final Color disabledColor = this.hovering ? new Color(58, 56, 56, 150) : new Color(0, 0, 0, 150);

        Gui.drawRect(x, y, x + getWidth(), y + getHeight(), disabledColor.getRGB());

        if (percentage != 0) {
            Gui.drawRect(x + 1, positionY + 1, x + (float) ((getWidth() - 2) * percentage) + 1, positionY + getHeight() - 1, new Color(250, 250, 250, 255).getRGB());
            Gui.drawRect(x + 1, positionY + 1, x + (float) ((getWidth() - 2) * percentage), positionY + getHeight() - 1, enabledColor.getRGB());
        }

        FontAdaptor.renderString(setting.getDisplayName(), x + 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(setting.getDisplayName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
        FontAdaptor.renderString(setting.getValue() + (setting.getType() != IntSetting.Type.NONE ? " " + setting.getType().getSymbol() : "") + (setting.getUnit() != null ? " " + setting.getUnit() : ""), x + getWidth() - FontAdaptor.getStringWidth(setting.getValue() + (setting.getType() != IntSetting.Type.NONE ? " " + setting.getType().getSymbol() : "") + (setting.getUnit() != null ? " " + setting.getUnit() : ""), CustomFontModule.clickGuiCustomFont.getValue()) - 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(setting.getValue() + (setting.getType() != IntSetting.Type.NONE ? " " + setting.getType().getSymbol() : "") + (setting.getUnit() != null ? " " + setting.getUnit() : ""), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
    }

    @Override
    public boolean keyPressed(char c, int i) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (hovering) {
            if (clickedButton == 0) {
                dragging = true;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging) {
            dragging = false;
            return true;
        }

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

    private void updateSlider(final int mouseX) {
        double relativeMouseX = Math.min(Math.max(mouseX - positionX - 4, 0), getWidth() - 8);
        double newValue = setting.getMin() + (relativeMouseX / (float) (getWidth() - 8)) * (setting.getMax() - setting.getMin());

        if (newValue < setting.getMin()) {
            newValue = setting.getMin();
        } else if (newValue > setting.getMax()) {
            newValue = setting.getMax();
        }

        setting.setValue((int) roundToIncrement(newValue, setting.getIncrement()));
    }

    private double roundToIncrement(double value, double increment) {
        return Math.round(value / increment) * increment;
    }
}
