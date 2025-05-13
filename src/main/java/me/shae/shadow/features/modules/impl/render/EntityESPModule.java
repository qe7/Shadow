package me.shae.shadow.features.modules.impl.render;

import me.shae.shadow.events.api.Listener;
import me.shae.shadow.events.api.SubscribeEvent;
import me.shae.shadow.events.impl.render.WorldEvent;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.api.ModuleInfo;
import me.shae.shadow.utils.RenderUtil;
import net.minecraft.src.Entity;
import net.minecraft.src.RenderManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleInfo(
        name = "EntityESP",
        description = "Renders a box at an entities coordinates.",
        category = ModuleCategory.RENDER
)
public final class EntityESPModule extends ModuleBase {

    @SubscribeEvent
    private final Listener<WorldEvent> onWorld = event -> {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity == mc.thePlayer) {
                continue;
            }

            final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
            final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY;
            final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ;

            GL11.glPushMatrix();
            RenderUtil.drawEntityESP(x, y, z, entity.width / 2, entity.height, new Color(100, 100, 100), 0.1f);
            GL11.glPopMatrix();
        }
    };
}
