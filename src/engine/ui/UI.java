package engine.ui;

import java.awt.Graphics;
import engine.input.InputHandler;

public abstract class UI {

    protected int screenWidth;
    protected int screenHeight;
    protected InputHandler input;

    public UI(int screenWidth, int screenHeight, InputHandler input) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.input = input;
    }

    // Update UI
    public abstract void update();

    // Draw UI
    public abstract void render(Graphics g);
}
