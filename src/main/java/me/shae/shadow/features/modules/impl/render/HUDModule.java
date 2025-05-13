package me.shae.shadow.features.modules.impl.render;

import me.shae.shadow.Shadow;
import me.shae.shadow.events.api.Listener;
import me.shae.shadow.events.api.SubscribeEvent;
import me.shae.shadow.events.impl.render.ScreenEvent;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.api.ModuleInfo;
import me.shae.shadow.features.modules.api.settings.impl.BooleanSetting;
import me.shae.shadow.features.modules.impl.client.CustomFontModule;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.ModuleManager;
import me.shae.shadow.utils.FontAdaptor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@ModuleInfo(name = "HUD", description = "Displays information related to the client.", category = ModuleCategory.RENDER)
public final class HUDModule extends ModuleBase {

    private final BooleanSetting displayWatermark = new BooleanSetting("displayWatermark", "Display Watermark", true);
    private final BooleanSetting displayArrayList = new BooleanSetting("displayArrayList", "Display Module List", true);
    private final BooleanSetting reverseArrayList = new BooleanSetting("reverseArrayList", "Reverse Module List", false);
    private final BooleanSetting bottomArrayList = new BooleanSetting("bottomArrayList", "Bottom Module List", false);

    @SubscribeEvent
    private final Listener<ScreenEvent> onScreen = event -> {
        final List<ModuleBase> moduleBaseList = new ArrayList<>(ManagerFactory.get(ModuleManager.class).getEnabledModules());

        if (displayWatermark.getValue()) {
            FontAdaptor.renderString(String.format("Shadow %s", Shadow.getInstance().getVersion()), 2, 2, Color.WHITE, CustomFontModule.hudCustomFont.getValue());
        }

        if (displayArrayList.getValue()) {
            moduleBaseList.sort(Comparator.comparingDouble(o -> reverseArrayList.getValue() ? getStringWidth(o) : -getStringWidth(o)));

            int offset = bottomArrayList.getValue() ? (int) (event.scaledResolution().getScaledHeight() - FontAdaptor.getStringHeight("bottomArrayList", CustomFontModule.hudCustomFont.getValue()) - 2) : 2;
            for (ModuleBase moduleBase : moduleBaseList) {
                String display = moduleBase.getName();

                if (!Objects.equals(moduleBase.getSuffix(), "")) {
                    display += " §7[§f" + moduleBase.getSuffix() + "§7]";
                }

                FontAdaptor.renderString(display, event.scaledResolution().getScaledWidth() - FontAdaptor.getStringWidth(display, CustomFontModule.hudCustomFont.getValue()) - 2, offset, moduleBase.getCategory().getColor(), CustomFontModule.hudCustomFont.getValue());
                if (bottomArrayList.getValue()) {
                    offset -= (int) FontAdaptor.getStringHeight(display, CustomFontModule.hudCustomFont.getValue());
                } else {
                    offset += (int) FontAdaptor.getStringHeight(display, CustomFontModule.hudCustomFont.getValue());
                }
            }
        }
    };

    private static float getStringWidth(ModuleBase o) {
        return FontAdaptor.getStringWidth(o.getName() + (!Objects.equals(o.getSuffix(), "") ? " [" + o.getSuffix() + "]" : ""), CustomFontModule.hudCustomFont.getValue());
    }
}
