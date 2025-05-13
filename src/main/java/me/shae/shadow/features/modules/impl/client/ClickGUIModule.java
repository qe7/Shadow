package me.shae.shadow.features.modules.impl.client;

import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.api.ModuleInfo;
import me.shae.shadow.ui.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClickGUI", description = "Opens a clickable interface to manage modules.", category = ModuleCategory.CLIENT)
public final class ClickGUIModule extends ModuleBase {

    private ClickGUIScreen clickGuiScreen;

    public ClickGUIModule() {
        this.setKeyBind(Keyboard.KEY_RSHIFT);

        this.setEnabled(false);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (mc.thePlayer == null) {
            this.setEnabled(false);
            return;
        }

        if (clickGuiScreen == null) {
            clickGuiScreen = new ClickGUIScreen();
        }

        mc.displayGuiScreen(clickGuiScreen);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (mc.currentScreen instanceof ClickGUIScreen) {
            mc.displayGuiScreen(null);
        }
    }
}
