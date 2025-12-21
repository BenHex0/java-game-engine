package game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

import engine.input.InputHandler;
import engine.ui.UI;

public class EndScreen extends UI {

    public EndScreen(int screenWidth, int screenHeight, InputHandler input) {
        super(screenWidth, screenHeight, input);
    }

    @Override
    public void update() {
        if (input.isKeyPressed(InputHandler.Key.ENTER)) {
            System.exit(0);
        }
    }

    @Override
    public void render(Graphics g) {

        String text = "You Escaped from the Monster!";

        // Background
        g.setColor(new Color(25, 25, 35));
        g.fillRect(0, 0, screenWidth, screenHeight);

        // ---- Dynamic font sizing ----
        int maxTextWidth = (int) (screenWidth * 0.9);
        int fontSize = screenHeight / 4;

        Font font;
        FontMetrics fm;

        do {
            font = new Font("Arial", Font.BOLD, fontSize);
            g.setFont(font);
            fm = g.getFontMetrics();
            fontSize--;
        } while (fm.stringWidth(text) > maxTextWidth && fontSize > 12);

        g.setColor(Color.GREEN);

        // Measure text
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        // Center text
        int x = (screenWidth - textWidth) / 2;
        int y = (screenHeight - textHeight) / 2 + fm.getAscent();

        g.drawString(text, x, y);

        
        Font hintFont = new Font("Serif", Font.ITALIC, 16);
        g.setFont(hintFont);
        g.setColor(new Color(200, 200, 200));

        String hint = "Press Enter";
        int hx = (screenWidth - g.getFontMetrics().stringWidth(hint)) / 2;

        g.drawString(hint, hx, screenHeight - 50);
    }

}
