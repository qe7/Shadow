package me.shae.shadow.ui.components.impl;

import me.shae.shadow.features.modules.api.settings.impl.ColorSetting;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.font.FontType;
import me.shae.shadow.font.renderer.TTFRenderer;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.FontManager;
import me.shae.shadow.ui.components.SettingComponent;
import me.shae.shadow.utils.FontAdaptor;
import net.minecraft.src.Gui;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

public final class ColorComponent extends SettingComponent {

    private final ColorSetting setting;

    private float positionX, positionY;

    private boolean open, draggingMain, draggingHue, draggingAlpha, hovering;

    private Color smallDragColor;
    private int opacity = 255;

    private static final int SECTION_SPACING = 2;
    private static final int BUTTON_SPACING = 3;

    public ColorComponent(ColorSetting setting, int width, int height) {
        super(setting, width, height);
        this.setting = setting;
        this.smallDragColor = setting.getValue();
        this.opacity = setting.getValue().getAlpha();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, float x, float y) {
        this.positionX = (int) x;
        this.positionY = (int) y;

        this.hovering = isHovered(mouseX, mouseY);

        final Color disabledColor = this.hovering ? new Color(58, 56, 56, 150) : new Color(0, 0, 0, 150);

        Gui.drawRect(x, y, x + getWidth(), y + getTotalHeight(), disabledColor.getRGB());

        final Color color = setting.getValue();

        Gui.drawRect(x + 1, y + 1, x + getWidth() - 1, y + getHeight() - 1, color.getRGB());

        String hexValue = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        float hexWidth = FontAdaptor.getStringWidth(hexValue, CustomFontModule.clickGuiCustomFont.getValue());
        FontAdaptor.renderString(hexValue, x + getWidth() - hexWidth - 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(setting.getDisplayName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());

        FontAdaptor.renderString(setting.getDisplayName(), x + 4, y + ((float) getHeight() / 2) - (FontAdaptor.getStringHeight(setting.getDisplayName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());

        if (open) {
            int mainPickerX = (int) (x + 4);
            int mainPickerY = (int) (y + getHeight() + SECTION_SPACING);
            int mainPickerWidth = getWidth() - 8 - 5;
            int mainPickerHeight = 50;

            renderMain(mainPickerX, mainPickerY, mainPickerWidth, mainPickerHeight, mouseX, mouseY);

            int hueBarX = mainPickerX + mainPickerWidth + SECTION_SPACING;
            int hueBarWidth = 5;
            renderHueBar(hueBarX, mainPickerY, hueBarWidth, mainPickerHeight, mouseX, mouseY);

            int opacityBarY = mainPickerY + mainPickerHeight + SECTION_SPACING;
            int opacityBarHeight = 5;
            renderOpacityBar(mainPickerX, opacityBarY, mainPickerWidth, opacityBarHeight, mouseX, mouseY);

            int buttonY = opacityBarY + opacityBarHeight + SECTION_SPACING;
            int buttonHeight = 8;
            int rndW = (int) FontAdaptor.getStringWidth("Random", CustomFontModule.clickGuiCustomFont.getValue()) + 3;
            int cpyW = (int) FontAdaptor.getStringWidth("Copy", CustomFontModule.clickGuiCustomFont.getValue()) + 3;
            int pstW = (int) FontAdaptor.getStringWidth("Paste", CustomFontModule.clickGuiCustomFont.getValue()) + 3;

            int copyX = mainPickerX + rndW + BUTTON_SPACING;
            int pasteX = copyX + cpyW + BUTTON_SPACING;

            final boolean rndHovered = isHoveringCoords(mainPickerX, buttonY, rndW, buttonHeight, mouseX, mouseY);
            final boolean copyHovered = isHoveringCoords(copyX, buttonY, cpyW, buttonHeight, mouseX, mouseY);
            final boolean pasteHovered = isHoveringCoords(pasteX, buttonY, pstW, buttonHeight, mouseX, mouseY);

            Gui.drawRect(mainPickerX, buttonY, mainPickerX + rndW, buttonY + buttonHeight, rndHovered ? new Color(58, 56, 56, 255).getRGB() : new Color(38, 36, 36, 255).getRGB());
            FontAdaptor.renderString("Random", mainPickerX + 1f, buttonY + 1, Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());

            Gui.drawRect(copyX, buttonY, copyX + cpyW, buttonY + buttonHeight, copyHovered ? new Color(58, 56, 56, 255).getRGB() : new Color(38, 36, 36, 255).getRGB());
            FontAdaptor.renderString("Copy", copyX + 1f, buttonY + 1, Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());

            Gui.drawRect(pasteX, buttonY, pasteX + pstW, buttonY + buttonHeight, pasteHovered ? new Color(58, 56, 56, 255).getRGB() : new Color(38, 36, 36, 255).getRGB());
            FontAdaptor.renderString("Paste", pasteX + 1f, buttonY + 1, Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());

            if (draggingHue) {
                updateHueFromPosition(mouseY);
            }

            if (draggingMain) {
                updateMainColorFromPosition(mouseX, mouseY);
            }

            if (draggingAlpha) {
                updateAlphaFromPosition(mouseX);
            }

        }
    }

    @Override
    public boolean keyPressed(char c, int i) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        TTFRenderer ttfRenderer = ManagerFactory.get(FontManager.class).getFontRenderer(FontType.ROBOTO, 16);

        if (clickedButton == 0) {
            if (mouseX >= this.positionX && mouseX <= this.positionX + getWidth() && mouseY >= this.positionY && mouseY <= this.positionY + getHeight()) {
                open = !open;
            }

            if (open) {
                int mainPickerX = (int) (this.positionX + 4);
                int mainPickerY = (int) (this.positionY + getHeight() + SECTION_SPACING);
                int mainPickerWidth = getWidth() - 8 - 5;
                int mainPickerHeight = 50;

                int hueBarX = mainPickerX + mainPickerWidth + SECTION_SPACING;
                int hueBarWidth = 5;

                int opacityBarY = mainPickerY + mainPickerHeight + SECTION_SPACING;
                int opacityBarHeight = 5;

                int buttonY = opacityBarY + opacityBarHeight + SECTION_SPACING;
                int buttonHeight = 8;
                int rndW = (int) ttfRenderer.getWidth("Random") + 3;
                int cpyW = (int) ttfRenderer.getWidth("Copy") + 3;
                int pstW = (int) ttfRenderer.getWidth("Paste") + 3;

                int copyX = mainPickerX + rndW + BUTTON_SPACING;
                int pasteX = copyX + cpyW + BUTTON_SPACING;

                if (isHoveringCoords(mainPickerX, buttonY, rndW, buttonHeight, mouseX, mouseY)) {
                    randomColorOption();
                }

                if (isHoveringCoords(copyX, buttonY, cpyW, buttonHeight, mouseX, mouseY)) {
                    copyColorToClipboard();
                }

                if (isHoveringCoords(pasteX, buttonY, pstW, buttonHeight, mouseX, mouseY)) {
                    pasteColorFromClipboard();
                }

                if (isHoveringCoords(hueBarX, mainPickerY, hueBarWidth, mainPickerHeight, mouseX, mouseY)) {
                    draggingHue = true;
                    updateHueFromPosition(mouseY);
                }

                if (isHoveringCoords(mainPickerX, mainPickerY, mainPickerWidth, mainPickerHeight, mouseX, mouseY)) {
                    draggingMain = true;
                    updateMainColorFromPosition(mouseX, mouseY);
                }

                if (isHoveringCoords(mainPickerX, opacityBarY, mainPickerWidth, opacityBarHeight, mouseX, mouseY)) {
                    draggingAlpha = true;
                    updateAlphaFromPosition(mouseX);
                }
            }
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingMain = false;
        draggingHue = false;
        draggingAlpha = false;

        return false;
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= positionX && mouseX <= positionX + getWidth() && mouseY >= positionY && mouseY < positionY + getTotalHeight();
    }

    @Override
    public float getTotalHeight() {
        return super.getHeight() + (open ? 65 + SECTION_SPACING * 3 : 0);
    }

    private void renderMain(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float[] hsb = Color.RGBtoHSB(smallDragColor.getRed(), smallDragColor.getGreen(), smallDragColor.getBlue(), null);
                Color color = Color.getHSBColor(hsb[0], (float) i / width, 1f - (float) j / height);

                Gui.drawRect(x + i, y + j, x + i + 1, y + j + 1, color.getRGB());
            }
        }

        // Draw current color position indicator
        float[] hsb = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);
        int indicatorX = x + (int) (hsb[1] * width);
        int indicatorY = y + (int) ((1f - hsb[2]) * height);

        Gui.drawRect(indicatorX - 2, indicatorY - 2, indicatorX + 2, indicatorY + 2, Color.WHITE.getRGB());
        Gui.drawRect(indicatorX - 1, indicatorY - 1, indicatorX + 1, indicatorY + 1, Color.BLACK.getRGB());
    }

    private void renderHueBar(final int x, final int y, final int width, final int height, int mouseX, int mouseY) {
        for (int i = 0; i < height; i++) {
            Color color = Color.getHSBColor((float) i / height, 1f, 1f);
            Gui.drawRect(x, y + i, x + width, y + i + 1, color.getRGB());
        }

        // Draw current hue position indicator
        float[] hsb = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);
        int indicatorY = y + (int) (hsb[0] * height);

        Gui.drawRect(x - 2, indicatorY - 2, x + 2, indicatorY + 2, Color.WHITE.getRGB());
        Gui.drawRect(x - 1, indicatorY - 1, x + 1, indicatorY + 1, Color.BLACK.getRGB());
    }

    private void renderOpacityBar(final int x, final int y, final int width, final int height, int mouseX, int mouseY) {
        for (int i = 0; i < width; i++) {
            int alpha = (int) ((float) i / width * 255);
            Color color = new Color(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), alpha);
            Gui.drawRect(x + i, y, x + i + 1, y + height, color.getRGB());
        }

        // Draw current alpha position indicator
        int indicatorX = x + (int) ((opacity / 255f) * width);

        Gui.drawRect(indicatorX - 2, y - 2, indicatorX + 2, y + 2, Color.WHITE.getRGB());
        Gui.drawRect(indicatorX - 1, y - 1, indicatorX + 1, y + 1, Color.BLACK.getRGB());
    }

    private void updateHueFromPosition(double mouseY) {
        int mainPickerY = (int) (this.positionY + getHeight() + SECTION_SPACING);
        int mainPickerHeight = 50;

        float hue = (float) Math.max(0, Math.min(1, (mouseY - mainPickerY) / mainPickerHeight));
        Color newHueColor = Color.getHSBColor(hue, 1f, 1f);
        smallDragColor = newHueColor;

        // Update the main color with the new hue but keep saturation and brightness
        float[] hsb = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), null);
        Color newColor = Color.getHSBColor(hue, hsb[1], hsb[2]);
        setting.setValue(new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), opacity));
    }

    private void updateMainColorFromPosition(double mouseX, double mouseY) {
        int mainPickerX = (int) (this.positionX + 4);
        int mainPickerY = (int) (this.positionY + getHeight() + SECTION_SPACING);
        int mainPickerWidth = getWidth() - 8 - 5;
        int mainPickerHeight = 50;

        float saturation = (float) Math.max(0, Math.min(1, (mouseX - mainPickerX) / mainPickerWidth));
        float brightness = (float) Math.max(0, Math.min(1, 1 - (mouseY - mainPickerY) / mainPickerHeight));

        float[] hsb = Color.RGBtoHSB(smallDragColor.getRed(), smallDragColor.getGreen(), smallDragColor.getBlue(), null);
        Color newColor = Color.getHSBColor(hsb[0], saturation, brightness);
        setting.setValue(new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), opacity));
    }

    private void updateAlphaFromPosition(double mouseX) {
        int mainPickerX = (int) (this.positionX + 4);
        int mainPickerWidth = getWidth() - 8 - 5;

        opacity = (int) Math.max(0, Math.min(255, ((mouseX - mainPickerX) / mainPickerWidth) * 255));
        setting.setValue(new Color(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), opacity));
    }

    private void randomColorOption() {
        setting.setValue(new Color(ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), ThreadLocalRandom.current().nextInt(255), opacity));
        smallDragColor = setting.getValue();
    }

    private void copyColorToClipboard() {
        try {
            String hex = String.format("#%02x%02x%02x", setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue());
            copy(hex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pasteColorFromClipboard() {
        try {
            String hex = paste();
            if (hex != null && hex.matches("^#([A-Fa-f0-9]{6})$")) {
                int rgb = (int) Long.parseLong(hex.substring(1), 16);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                Color color = new Color(red, green, blue);
                setting.setValue(color);
                smallDragColor = color;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isHoveringCoords(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private void copy(String text) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Process process;

            if (os.contains("win")) {
                process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "echo " + text + " | clip"});
            } else if (os.contains("mac")) {
                process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "echo " + text + " | pbcopy"});
            } else {
                process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "echo " + text + " | xclip -selection clipboard"});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String paste() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Process process;
            String result;

            if (os.contains("win")) {
                process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "powershell -command Get-Clipboard"});
            } else if (os.contains("mac")) {
                process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "pbpaste"});
            } else {
                process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "xclip -selection clipboard -o"});
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            result = builder.toString().trim();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}