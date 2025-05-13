package me.shae.shadow.features.modules.impl.movement;

import me.shae.shadow.events.api.Listener;
import me.shae.shadow.events.api.SubscribeEvent;
import me.shae.shadow.events.impl.player.MotionEvent;
import me.shae.shadow.events.impl.player.TickEvent;
import me.shae.shadow.features.modules.api.ModuleBase;
import me.shae.shadow.features.modules.api.ModuleCategory;
import me.shae.shadow.features.modules.api.ModuleInfo;
import me.shae.shadow.features.modules.api.settings.impl.BooleanSetting;
import me.shae.shadow.features.modules.api.settings.impl.DoubleSetting;
import me.shae.shadow.utils.MovementUtil;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Flight",
        description = "Fly like superman.",
        category = ModuleCategory.MOVEMENT
)
public final class FlightModule extends ModuleBase {

    private final BooleanSetting customSpeed = new BooleanSetting("customSpeed", "Custom Speed", true);

    private final DoubleSetting horizontalSpeed = new DoubleSetting("horizontalSpeed", "Horizontal Speed", 0.5D, 0.1D, 1.0D, 0.01D);
    private final DoubleSetting verticalSpeed = new DoubleSetting("verticalSpeed", "Verical Speed", 0.25D, 0.1D, 1.0D, 0.01D);

    public FlightModule() {
        this.horizontalSpeed.supplyIf(customSpeed::getValue);
        this.verticalSpeed.supplyIf(customSpeed::getValue);
    }

    @SubscribeEvent
    private final Listener<TickEvent> onTick = event -> {
        mc.thePlayer.onGround = true;
        float motionY = 0;

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.keyCode)) {
            motionY += (float) (customSpeed.getValue() ? verticalSpeed.getValue() : 0F);
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode)) {
            motionY -= (float) (customSpeed.getValue() ? verticalSpeed.getValue() : 0F);
        }

        mc.thePlayer.motionY = motionY;
        if (customSpeed.getValue()) {
            MovementUtil.setSpeed(horizontalSpeed.getValue());
        }
    };

    @SubscribeEvent
    private final Listener<MotionEvent> onMotion = event -> event.setOnGround(true);
}
