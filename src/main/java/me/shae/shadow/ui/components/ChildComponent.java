package me.shae.shadow.ui.components;

public interface ChildComponent {

    void render(int mouseX, int mouseY, float delta, float x, float y);

    boolean keyPressed(char c, int i);

    boolean mouseClicked(double mouseX, double mouseY, int clickedButton);

    boolean mouseReleased(double mouseX, double mouseY, int button);

    boolean isHovered(double mouseX, double mouseY);

    float getTotalHeight();
}
