package me.shae.shadow.ui;

import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.impl.client.ClickGUIModule;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.ModuleManager;
import me.shae.shadow.ui.components.impl.PanelComponent;
import net.minecraft.src.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public final class ClickGUIScreen extends GuiScreen {

    private final List<PanelComponent> panels = new ArrayList<>();

    public ClickGUIScreen() {
        int x = 4, y = 4;
        int width = 120, height = 14;

        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            panels.add(new PanelComponent(moduleCategory, width, height, x, y));
            x += width + 4;
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);

        for (PanelComponent panelComponent : panels) {
            panelComponent.render(i, j, f);
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        for (PanelComponent panelComponent : panels) {
            if (panelComponent.keyPressed(c, i)) {
                return;
            }
        }

        super.keyTyped(c, i);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        for (PanelComponent panelComponent : panels) {
            if (panelComponent.mouseClicked(i, j, k)) {
                return;
            }
        }

        super.mouseClicked(i, j, k);
    }

    @Override
    protected void mouseReleased(int i, int j, int k) {
        for (PanelComponent panel : panels) {
            if (panel.mouseReleased(i, j, k)) {
                return;
            }
        }

        super.mouseReleased(i, j, k);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        ManagerFactory.get(ModuleManager.class).get(ClickGUIModule.class).setEnabled(false);
    }
}
