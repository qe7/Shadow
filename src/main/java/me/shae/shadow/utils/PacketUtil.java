package me.shae.shadow.utils;

import net.minecraft.src.Packet;

public final class PacketUtil implements IMinecraft {

    private PacketUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void sendPacket(final Packet packet) {
        if (mc.theWorld != null && mc.theWorld.multiplayerWorld) {
            mc.getSendQueue().addToSendQueue(packet);
        }
    }
}
