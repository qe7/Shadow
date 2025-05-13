package me.shae.shadow.ui.components.impl;

import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.impl.client.ColorModule;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.font.FontType;
import me.shae.shadow.font.renderer.TTFRenderer;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.FontManager;
import me.shae.shadow.ui.components.ChildComponent;
import me.shae.shadow.utils.FontAdaptor;
import net.minecraft.src.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class KeyBindComponent implements ChildComponent {

    private final ModuleBase module;

    private final int width;
    private int height;

    private float positionX, positionY;

    private boolean listening, hovering;

    public KeyBindComponent(ModuleBase module, int width, int height) {
        this.module = module;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, float x, float y) {
        this.positionX = x;
        this.positionY = y;

        this.hovering = isHovered(mouseX, mouseY);

        final Color disabledColor = this.hovering ? new Color(58, 56, 56, 150) : new Color(0, 0, 0, 150);

        Gui.drawRect(x, y, x + width, y + height, disabledColor.getRGB());

        if (listening) {
            FontAdaptor.renderString("Listening...", x + 4, y + ((float) height / 2) - (FontAdaptor.getStringHeight("Listening...", CustomFontModule.clickGuiCustomFont.getValue()) / 2), ColorModule.getColorSetting().getValue(), CustomFontModule.clickGuiCustomFont.getValue());
        } else {
            FontAdaptor.renderString("KeyBind: ", x + 4, y + ((float) height / 2) - (FontAdaptor.getStringHeight("KeyBind: ", CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
            FontAdaptor.renderString(Keyboard.getKeyName(module.getKeyBind()), x + width - FontAdaptor.getStringWidth(Keyboard.getKeyName(module.getKeyBind()), CustomFontModule.clickGuiCustomFont.getValue()) - 4, y + ((float) height / 2) - (FontAdaptor.getStringHeight(Keyboard.getKeyName(module.getKeyBind()), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
        }
    }

    @Override
    public boolean keyPressed(char c, int i) {
        if (listening) {
            if (i == Keyboard.KEY_ESCAPE) {
                listening = false;
                return true;
            }

            if (i == Keyboard.KEY_DELETE || i == Keyboard.KEY_BACK) {
                module.setKeyBind(0);
                listening = false;
                return true;
            }

            module.setKeyBind(i);
            listening = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (hovering) {
            if (clickedButton == 0) {
                listening = !listening;
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
        return mouseX >= positionX && mouseX <= positionX + width && mouseY >= positionY && mouseY < positionY + height;
    }

    @Override
    public float getTotalHeight() {
        return height;
    }

    public static List<String> splitStringsIntoArrays(String string, int panelWidth, TTFRenderer fontRenderer) {
        List<String> result = new ArrayList<>();

        if (string == null || string.isEmpty()) {
            return result; // Return an empty list for null or empty strings
        }

        String[] lines = string.split("\\n"); // Split the string by explicit newlines

        for (String line : lines) {
            String[] words = line.split(" "); // Split each line into words
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                String testLine;

                // Add the word to the current line and test its width
                if (currentLine.isEmpty()) {
                    testLine = word;
                } else {
                    testLine = currentLine + " " + word;
                }

                if (fontRenderer.getWidth(testLine) > panelWidth) {
                    // If adding this word exceeds the width, finalize the current line
                    if (currentLine.length() > 0) {
                        result.add(currentLine.toString());
                        currentLine = new StringBuilder(word); // Start a new line with the current word
                    } else {
                        // If a single word is too long, split the word itself
                        for (int i = 0; i < word.length(); i++) {
                            int endIndex = i + 1;
                            while (endIndex <= word.length() && fontRenderer.getWidth(word.substring(i, endIndex)) <= panelWidth) {
                                endIndex++;
                            }
                            result.add(word.substring(i, endIndex - 1));
                            i = endIndex - 2; // Adjust the index to account for the processed part
                        }
                    }
                } else {
                    // Otherwise, keep building the current line
                    if (!currentLine.isEmpty()) {
                        currentLine.append(" ");
                    }
                    currentLine.append(word);
                }
            }

            // Add the last line of the current segment if it's not empty
            if (!currentLine.isEmpty()) {
                result.add(currentLine.toString());
            }
        }

        return result;
    }
}
