package me.shae.shadow.events.impl.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.shae.shadow.events.api.TypeEvent;

@Getter
@Setter
@AllArgsConstructor
public final class MotionEvent implements TypeEvent {

    private final Type type;

    private double x, y, minY, z;
    private float yaw, pitch;
    private boolean onGround;

    @Override
    public Type type() {
        return type;
    }
}
