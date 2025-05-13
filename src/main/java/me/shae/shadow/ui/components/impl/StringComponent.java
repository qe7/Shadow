package me.shae.shadow.ui.components.impl;

import me.shae.shadow.features.modules.api.settings.impl.StringSetting;
import me.shae.shadow.features.modules.impl.client.ColorModule;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.ui.components.SettingComponent;
import me.shae.shadow.utils.FontAdaptor;
import me.shae.shadow.utils.Stopwatch;
import net.minecraft.src.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public final class StringComponent extends SettingComponent {

    private final Stopwatch stopwatch = new Stopwatch();

    private final StringSetting setting;

    private float positionX, positionY;

    private boolean listening, hovering;

    private String currentInput = "";

    public StringComponent(StringSetting setting, int width, int height) {
        super(setting, width, height);
        this.setting = setting;
        this.currentInput = setting.getValue();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, float x, float y) {
        this.positionX = x;
        this.positionY = y;

        this.hovering = isHovered(mouseX, mouseY);

        final Color disabledColor = this.hovering ? new Color(58, 56, 56, 150) : new Color(0, 0, 0, 150);

        Gui.drawRect(x, y, x + getWidth(), y + getHeight(), disabledColor.getRGB());

        if (stopwatch.hasTimeElapsed(50, TimeUnit.MILLISECONDS, true)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                }
            }
        }

        String displayText = listening ? currentInput : setting.getValue();

        if (displayText.isEmpty()) {
            displayText = "Type here...";
        }

        FontAdaptor.renderString(setting.getDisplayName(), x + 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringWidth(setting.getDisplayName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
        FontAdaptor.renderString(displayText, x + getWidth() - FontAdaptor.getStringWidth(displayText, CustomFontModule.clickGuiCustomFont.getValue()) - 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(displayText, CustomFontModule.clickGuiCustomFont.getValue()) / 2), listening ? ColorModule.getColorSetting().getValue() : Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
    }

    @Override
    public boolean keyPressed(char c, int i) {
        if (listening) {
            if (i == Keyboard.KEY_ESCAPE) {
                listening = false;
                currentInput = setting.getValue();
                return true;
            } else if (i == Keyboard.KEY_RETURN) {
                listening = false;
                setting.setValue(currentInput);
                return true;
            } else if (c >= 32 && c <= 126) {
                currentInput += c;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (hovering) {
            if (clickedButton == 0) {
                listening = !listening;
                if (listening) {
                    currentInput = setting.getValue();
                } else {
                    setting.setValue(currentInput);
                }
                return true;
            }
        } else if (listening) {
            listening = false;
            setting.setValue(currentInput);
            return true;
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
        return getHeight();
    }
}