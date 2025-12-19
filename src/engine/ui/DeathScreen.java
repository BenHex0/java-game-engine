package engine.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import engine.input.InputHandler;
import engine.Engine;

public class DeathScreen extends UI {

    public DeathScreen(int screenWidth, int screenHeight, InputHandler input) {
        super(screenWidth, screenHeight, input);
    }

    @Override
    public void update() {
        if (input.isKeyPressed(InputHandler.Key.ENTER)) {
            Engine.currentLevel.restartLevel();
        }
    }

    @Override
    public void render(Graphics g) {

        String text = "DEAD";
        int fontSize = 100;

        // Background
        g.setColor(new Color(25, 25, 35));
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Font
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g.setFont(font);
        g.setColor(Color.RED);

        // Measure text
        FontMetrics fm = g.getFontMetrics();

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        // Center position
        int x = (screenWidth - textWidth) / 2;
        int y = (screenHeight - textHeight) / 2 + fm.getAscent();

        // Draw
        g.drawString(text, x, y);
    }

}
