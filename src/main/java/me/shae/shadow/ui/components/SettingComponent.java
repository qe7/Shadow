package me.shae.shadow.ui.components;

import lombok.Getter;
import me.shae.shadow.features.modules.api.settings.Setting;

@Getter
public abstract class SettingComponent implements ChildComponent {

    private final Setting<?> setting;

    private final int width, height;

    public SettingComponent(Setting<?> setting, int width, int height) {
        this.setting = setting;
        this.width = width;
        this.height = height;
    }
}
