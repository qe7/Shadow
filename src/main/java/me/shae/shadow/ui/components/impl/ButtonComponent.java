package me.shae.shadow.ui.components.impl;

import lombok.Getter;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.settings.Setting;
import me.shae.shadow.features.modules.api.settings.impl.*;
import me.shae.shadow.features.modules.impl.client.ColorModule;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.ModuleManager;
import me.shae.shadow.ui.components.ChildComponent;
import me.shae.shadow.ui.components.SettingComponent;
import me.shae.shadow.utils.FontAdaptor;
import net.minecraft.src.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ButtonComponent implements ChildComponent {

    private final List<ChildComponent> components = new ArrayList<>();

    @Getter
    private final ModuleBase module;

    private final int width, height;

    private float positionX, positionY;

    private float totalHeight;

    private boolean open = false, hovering;

    public ButtonComponent(ModuleBase module, int width, int height) {
        this.module = module;
        this.width = width;
        this.height = height;

        if (!Objects.equals(module.getDescription(), "")) {
            components.add(new DescriptionComponent(module, width, 12));
        }

        for (Setting<?> setting : ManagerFactory.get(ModuleManager.class).getSettingsByModule(module)) {
            switch (setting) {
                case BooleanSetting booleanSetting -> components.add(new BooleanComponent(booleanSetting, width, 12));
                case EnumSetting<?> enumSetting -> components.add(new EnumComponent(enumSetting, width, 12));
                case DoubleSetting doubleSetting -> components.add(new DoubleComponent(doubleSetting, width, 12));
                case IntSetting intSetting -> components.add(new IntComponent(intSetting, width, 12));
                case ModeSetting modeSetting -> components.add(new ModeComponent(modeSetting, width, 12));
                case StringSetting stringSetting -> components.add(new StringComponent(stringSetting, width, 12));
                case ColorSetting colorSetting -> components.add(new ColorComponent(colorSetting, width, 12));
                case DescriptionSetting descriptionSetting ->
                        components.add(new DescriptionSettingComponent(descriptionSetting, width, 12));
                case GroupBooleanSetting groupBooleanSetting ->
                        components.add(new GroupBooleanComponent(groupBooleanSetting, width, 12));
                case GroupSetting groupSetting -> components.add(new GroupComponent(groupSetting, width, 12));
                default -> {
                }
            }
        }

        components.add(new KeyBindComponent(module, width, 12));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, float x, float y) {
        positionX = x;
        positionY = y;

        this.hovering = isHovered(mouseX, mouseY);

        final Color enabledColor = this.hovering ? ColorModule.getColorSetting().getValue().brighter() : ColorModule.getColorSetting().getValue();
        final Color disabledColor = this.hovering ? new Color(58, 56, 56, 150) : new Color(0, 0, 0, 150);

        Gui.drawRect(positionX, positionY, positionX + width, positionY + height, module.isEnabled() ? enabledColor.getRGB() : disabledColor.getRGB());

        FontAdaptor.renderString(module.getName(), positionX + 4, positionY + (((float) height / 2) - (FontAdaptor.getStringHeight(module.getName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2)), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());
        FontAdaptor.renderString(open ? "." : "...", positionX + width - FontAdaptor.getStringWidth(open ? "." : "...", CustomFontModule.clickGuiCustomFont.getValue()) - 4, positionY + (((float) height / 2) - (FontAdaptor.getStringHeight(module.getName(), CustomFontModule.clickGuiCustomFont.getValue()) / 2)), Color.WHITE, CustomFontModule.clickGuiCustomFont.getValue());

        this.totalHeight = height;

        if (!open) {
            return;
        }

        float offset = totalHeight;
        for (ChildComponent childComponent : components) {
            if (childComponent instanceof SettingComponent settingComponent) {
                if (settingComponent.getSetting().shouldHide()) {
                    continue;
                }
            }
            childComponent.render(mouseX, mouseY, delta, positionX, positionY + offset);
            totalHeight += childComponent.getTotalHeight();
            offset += childComponent.getTotalHeight();
        }
    }

    @Override
    public boolean keyPressed(char c, int i) {
        if (open) {
            for (ChildComponent childComponent : components) {
                if (childComponent instanceof SettingComponent settingComponent) {
                    if (settingComponent.getSetting().shouldHide()) {
                        continue;
                    }
                }
                if (childComponent.keyPressed(c, i)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int clickedButton) {
        if (open) {
            for (ChildComponent childComponent : components) {
                if (childComponent instanceof SettingComponent settingComponent) {
                    if (settingComponent.getSetting().shouldHide()) {
                        continue;
                    }
                }
                if (childComponent.mouseClicked(mouseX, mouseY, clickedButton)) {
                    return true;
                }
            }
        }

        if (hovering) {
            if (clickedButton == 0) {
                module.setEnabled(!module.isEnabled());
                return true;
            } else if (clickedButton == 1) {
                open = !open;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (open) {
            for (ChildComponent childComponent : components) {
                if (childComponent instanceof SettingComponent settingComponent) {
                    if (settingComponent.getSetting().shouldHide()) {
                        continue;
                    }
                }
                if (childComponent.mouseReleased(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= positionX && mouseX <= positionX + width && mouseY >= positionY && mouseY < positionY + height;
    }

    @Override
    public float getTotalHeight() {
        return this.totalHeight;
    }
}
