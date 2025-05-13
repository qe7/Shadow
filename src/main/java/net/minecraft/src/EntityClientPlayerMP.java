package net.minecraft.src;

import me.shae.shadow.events.api.TypeEvent;
import me.shae.shadow.events.impl.player.MotionEvent;
import me.shae.shadow.events.impl.player.TickEvent;
import me.shae.shadow.managers.api.ManagerFactory;
import me.shae.shadow.managers.impl.EventManager;
import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP {

    public EntityClientPlayerMP(Minecraft minecraft, World world, Session session, NetClientHandler netclienthandler) {
        super(minecraft, world, session, 0);
        field_9380_bx = 0;
        field_21093_bH = false;
        field_9382_bF = false;
        wasSneaking = false;
        field_12242_bI = 0;
        sendQueue = netclienthandler;
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    public void heal(int i) {
    }

    public void onUpdate() {
        if (!worldObj.blockExists(MathHelper.floor_double(posX), 64, MathHelper.floor_double(posZ))) {
            return;
        } else {
            super.onUpdate();
            func_4056_N();
            return;
        }
    }

    public void func_4056_N() {
        if (field_9380_bx++ == 20) {
            sendInventoryChanged();
            field_9380_bx = 0;
        }
        boolean flag = isSneaking();
        if (flag != wasSneaking) {
            if (flag) {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
            } else {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
            }
            wasSneaking = flag;
        }

        MotionEvent motionEvent = new MotionEvent(TypeEvent.Type.PRE, posX, posY, boundingBox.minY, posZ, rotationYaw, rotationPitch, onGround);
        ManagerFactory.get(EventManager.class).publishEvent(motionEvent);

        double d = motionEvent.getX() - oldPosX;
        double d1 = motionEvent.getMinY() - field_9378_bz;
        double d2 = motionEvent.getY() - oldPosY;
        double d3 = motionEvent.getZ() - oldPosZ;
        double d4 = motionEvent.getYaw() - oldRotationYaw;
        double d5 = motionEvent.getPitch() - oldRotationPitch;

        boolean flag1 = d1 != 0.0D || d2 != 0.0D || d != 0.0D || d3 != 0.0D;
        boolean flag2 = d4 != 0.0D || d5 != 0.0D;

        if (ridingEntity != null) {
            if (flag2) {
                sendQueue.addToSendQueue(new Packet11PlayerPosition(motionX, -999D, -999D, motionZ, onGround));
            } else {
                sendQueue.addToSendQueue(new Packet13PlayerLookMove(motionX, -999D, -999D, motionZ, rotationYaw, rotationPitch, onGround));
            }
            flag1 = false;
        } else if (flag1 && flag2) {
            sendQueue.addToSendQueue(new Packet13PlayerLookMove(motionEvent.getX(), motionEvent.getMinY(), motionEvent.getY(), motionEvent.getZ(), motionEvent.getYaw(), motionEvent.getPitch(), motionEvent.isOnGround()));
            field_12242_bI = 0;
        } else if (flag1) {
            sendQueue.addToSendQueue(new Packet11PlayerPosition(motionEvent.getX(), motionEvent.getMinY(), motionEvent.getY(), motionEvent.getZ(), motionEvent.isOnGround()));
            field_12242_bI = 0;
        } else if (flag2) {
            sendQueue.addToSendQueue(new Packet12PlayerLook(motionEvent.getYaw(), motionEvent.getPitch(), motionEvent.isOnGround()));
            field_12242_bI = 0;
        } else {
            sendQueue.addToSendQueue(new Packet10Flying(motionEvent.isOnGround()));
            if (field_9382_bF != motionEvent.isOnGround() || field_12242_bI > 200) {
                field_12242_bI = 0;
            } else {
                field_12242_bI++;
            }
        }

        field_9382_bF = motionEvent.isOnGround();
        if (flag1) {
            oldPosX = posX;
            field_9378_bz = boundingBox.minY;
            oldPosY = posY;
            oldPosZ = posZ;
        }

        if (flag2) {
            oldRotationYaw = motionEvent.getYaw();
            oldRotationPitch = motionEvent.getPitch();
        }
    }

    public void dropCurrentItem() {
        sendQueue.addToSendQueue(new Packet14BlockDig(4, 0, 0, 0, 0));
    }

    private void sendInventoryChanged() {
    }

    protected void joinEntityItemWithWorld(EntityItem entityitem) {
    }

    public void sendChatMessage(String s) {
        sendQueue.addToSendQueue(new Packet3Chat(s));
    }

    public void swingItem() {
        super.swingItem();
        sendQueue.addToSendQueue(new Packet18Animation(this, 1));
    }

    public void respawnPlayer() {
        sendInventoryChanged();
        sendQueue.addToSendQueue(new Packet9Respawn((byte) dimension));
    }

    protected void damageEntity(int i) {
        health -= i;
    }

    public void closeScreen() {
        sendQueue.addToSendQueue(new Packet101CloseWindow(craftingInventory.windowId));
        inventory.setItemStack(null);
        super.closeScreen();
    }

    public void setHealth(int i) {
        if (field_21093_bH) {
            super.setHealth(i);
        } else {
            health = i;
            field_21093_bH = true;
        }
    }

    public void addStat(StatBase statbase, int i) {
        if (statbase == null) {
            return;
        }
        if (statbase.field_27088_g) {
            super.addStat(statbase, i);
        }
    }

    public void func_27027_b(StatBase statbase, int i) {
        if (statbase == null) {
            return;
        }
        if (!statbase.field_27088_g) {
            super.addStat(statbase, i);
        }
    }

    public NetClientHandler sendQueue;
    private int field_9380_bx;
    private boolean field_21093_bH;
    private double oldPosX;
    private double field_9378_bz;
    private double oldPosY;
    private double oldPosZ;
    private float oldRotationYaw;
    private float oldRotationPitch;
    private boolean field_9382_bF;
    private boolean wasSneaking;
    private int field_12242_bI;
}
