package me.shae.shadow.features.modules.impl.client;

import lombok.Getter;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.api.ModuleInfo;
import me.shae.shadow.features.modules.api.settings.impl.ColorSetting;

import java.awt.*;

@ModuleInfo(
        name = "Color",
        category = ModuleCategory.CLIENT
)
public final class ColorModule extends ModuleBase {

    @Getter
    private static final ColorSetting colorSetting = new ColorSetting("Color", "Client Color", new Color(68, 117, 251, 255));

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean listening() {
        return false;
    }
}
