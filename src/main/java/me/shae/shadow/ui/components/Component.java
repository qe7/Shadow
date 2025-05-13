package me.shae.shadow.ui.components;

public interface Component {

    void render(int mouseX, int mouseY, float delta);

    boolean keyPressed(char c, int i);

    boolean mouseClicked(double mouseX, double mouseY, int clickedButton);

    boolean mouseReleased(double mouseX, double mouseY, int button);
}
