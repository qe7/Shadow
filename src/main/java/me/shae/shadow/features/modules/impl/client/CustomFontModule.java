package me.shae.shadow.features.modules.impl.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.shae.shadow.events.api.Listener;
import me.shae.shadow.events.api.SubscribeEvent;
import me.shae.shadow.events.impl.render.ScreenEvent;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.api.ModuleInfo;
import me.shae.shadow.features.modules.api.settings.impl.BooleanSetting;
import me.shae.shadow.features.modules.api.settings.impl.EnumSetting;
import me.shae.shadow.features.modules.api.settings.impl.IntSetting;
import me.shae.shadow.features.modules.api.settings.impl.interfaces.IEnumSetting;
import me.shae.shadow.font.FontType;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.FontManager;
import me.shae.shadow.utils.FontAdaptor;

@ModuleInfo(
        name = "CustomFont",
        category = ModuleCategory.CLIENT
)
public final class CustomFontModule extends ModuleBase {

    public static EnumSetting<FontSettingEnum> fontSetting = new EnumSetting<>("fontSetting", "Font", FontSettingEnum.TAHOMA);

    public static IntSetting fontSize = new IntSetting("fontSize", "Font Size", 18, 10, 25, 1);

    public static BooleanSetting hudCustomFont = new BooleanSetting("hudCustomFont", "HUD", false);
    public static BooleanSetting clickGuiCustomFont = new BooleanSetting("clickGuiCustomFont", "ClickGUI", true);

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean listening() {
        return true;
    }

    @SubscribeEvent
    private final Listener<ScreenEvent> onScreen = event -> FontAdaptor.setTtfRenderer(ManagerFactory.get(FontManager.class).getFontRenderer(CustomFontModule.fontSetting.getValue().getFontType(), CustomFontModule.fontSize.getValue()));

    @AllArgsConstructor
    public enum FontSettingEnum implements IEnumSetting {
        TAHOMA("Tahoma", FontType.TAHOMA),
        SF_UI_PRO("SF-UI-Pro", FontType.SF_UI),
        OPEN_SANS("OpenSans", FontType.OPEN_SANS),
        ROBOTO("Roboto", FontType.ROBOTO);

        final String name;
        @Getter
        final FontType fontType;

        @Override
        public String getName() {
            return this.name;
        }
    }
}
