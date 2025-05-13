package me.shae.shadow.ui.components.impl;

import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.impl.client.ColorModule;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.font.FontType;
import me.shae.shadow.font.renderer.TTFRenderer;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.FontManager;
import me.shae.shadow.managers.impl.ModuleManager;
import me.shae.shadow.ui.components.Component;
import me.shae.shadow.utils.FontAdaptor;
import net.minecraft.src.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public final class PanelComponent implements Component {

    private final List<ButtonComponent> buttons = new ArrayList<>();

    private final ModuleCategory moduleCategory;

    private final int width, height;

    private float positionX, positionY, dragDifferenceX, dragDifferenceY, totalHeight;

    private boolean dragging, hovering, open = true;

    public PanelComponent(ModuleCategory moduleCategory, int width, int height, int positionX, int positionY) {
        this.moduleCategory = moduleCategory;
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;

        List<ModuleBase> moduleBaseList = new ArrayList<>(ManagerFactory.get(ModuleManager.class).getModulesByCategory(moduleCategory));

        moduleBaseList.sort(Comparator.comparingDouble(o -> -FontAdaptor.getStringWidth(o.getName(), CustomFontModule.clickGuiCustomFont.getValue())));

        for (ModuleBase module : moduleBaseList) {
            buttons.add(new ButtonComponent(module, width - 1, 13));
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.hovering = isHovered(mouseX, mouseY);

        if (dragging) {
            positionX = mouseX - dragDifferenceX;
            positionY = mouseY - dragDifferenceY;
        }

        final Color enabledColor = this.hovering ? ColorModule.getColorSetting().getValue().brighter() : ColorModule.getColorSetting().getValue();

        Gui.drawRect(positionX, positionY, positionX + width, positionY + totalHeight, new Color(0, 0, 0, 150).getRGB());
        Gui.drawRect(positionX, positionY, positionX + width, positionY + height, enabledColor.getRGB());
        FontAdaptor.renderString(moduleCategory.getName(), positionX + ((width / 2) - (FontAdaptor.getStringWidth(moduleCategory.getName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2)), positionY + ((height / 2) - (8 / 2)), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());

        this.totalHeight = height;

        if (!open) {
            return;
        }
        totalHeight += 0.5f;

        float offset = totalHeight;
        for (ButtonComponent buttonComponent : buttons) {
            buttonComponent.render(mouseX, mouseY, delta, positionX + 0.5f, positionY + offset);
            totalHeight += buttonComponent.getTotalHeight() + 0.5f;
            offset += buttonComponent.getTotalHeight() + 0.5f;
        }
    }

    @Override
    public boolean keyPressed(char c, int i) {
        if (open) {
            for (ButtonComponent button : buttons) {
                if (button.keyPressed(c, i)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (open) {
            for (ButtonComponent button : buttons) {
                if (button.mouseClicked(mouseX, mouseY, clickedButton)) {
                    return true;
                }
            }
        }

        if (this.hovering) {
            if (clickedButton == 0) {
                this.dragDifferenceX = (int) (mouseX - positionX);
                this.dragDifferenceY = (int) (mouseY - positionY);
                this.dragging = true;
                return true;
            } else if (clickedButton == 1) {
                this.open = !this.open;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (open) {
            for (ButtonComponent button1 : buttons) {
                if (button1.mouseReleased(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }

        if (button == 0 && this.dragging) {
            this.dragging = false;
            return true;
        }

        return false;
    }

    private boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= positionX && mouseX <= positionX + width && mouseY >= positionY && mouseY < positionY + height;
    }
}
