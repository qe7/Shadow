package me.shae.shadow.utils;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class RenderUtil {

    private RenderUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Draws an outlined bounding box
     *
     * @param aa The AxisAlignedBB to draw
     */
    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
    }

    /**
     * Draws a bounding box
     *
     * @param aa The AxisAlignedBB to draw
     */
    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
    }

    /**
     * Draws a solid bounding box
     *
     * @param x         The x position of the bounding box
     * @param y         The y position of the bounding box
     * @param z         The z position of the bounding box
     * @param color     The color of the bounding box
     * @param lineWidth The width of the bounding box outline
     */
    public static void drawBlockESP(double x, double y, double z, Color color, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 0.25f);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 0.8f);
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    /**
     * Draws an entity ESP
     *
     * @param x         The x position of the entity
     * @param y         The y position of the entity
     * @param z         The z position of the entity
     * @param width     The width of the entity
     * @param height    The height of the entity
     * @param color     The color of the entity
     * @param lineWidth The width of the entity outline
     */
    public static void drawEntityESP(double x, double y, double z, double width, double height, Color color, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 0.25f);
        drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 0.8f);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    /**
     * Draws a tracer line
     *
     * @param x         The x position of the tracer line
     * @param y         The y position of the tracer line
     * @param z         The z position of the tracer line
     * @param color     The color of the tracer line
     * @param lineWidth The width of the tracer line
     */
    public static void drawTracerLine(double x, double y, double z, Color color, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND); // 3042
        GL11.glEnable(GL11.GL_LINE_SMOOTH); // 2848
        GL11.glDisable(GL11.GL_DEPTH_TEST); // 2929
        GL11.glDisable(GL11.GL_TEXTURE_2D); // 3553
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND); // 3042
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, 1.0F);
        GL11.glBegin(2);
        GL11.glVertex3d(0, 0, 0);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
