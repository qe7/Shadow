package me.shae.shadow.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Direction {
    ZPOS(0, 0, 1, 2, 0.0F), XNEG(-1, 0, 0, 5, 90.0F), ZNEG(0, 0, -1, 3, 180.0F), XPOS(1, 0, 0, 4, 270.0F), NULL(0, 0, 0, 0, 0.0F);

    public final int offX;
    public final int offY;
    public final int offZ;
    public final int hitSide;
    public final float yaw;

    public String toString() {
        return switch (this) {
            case ZPOS -> "Z+";
            case XNEG -> "X-";
            case ZNEG -> "Z-";
            case XPOS -> "X+";
            default -> "NULL";
        };
    }

    public Direction getOpposite() {
        return switch (this) {
            case ZPOS -> ZNEG;
            case XNEG -> XPOS;
            case ZNEG -> ZPOS;
            case XPOS -> XNEG;
            default -> NULL;
        };
    }
}
