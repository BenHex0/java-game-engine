package game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import engine.ui.UI;
import engine.Engine;
import engine.input.InputHandler;

public class LevelFinished extends UI {

    public LevelFinished(int screenWidth, int screenHeight, InputHandler input) {
        super(screenWidth, screenHeight, input);
    }

    @Override
    public void update() {
        if (input.isKeyPressed(InputHandler.Key.ENTER)) {
            Engine.currentLevel.restartLevel();
            Engine.current_state = Engine.gameplay_state;
        }
    }

    @Override
    public void render(Graphics g) {

        String text = "You Win";
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

        // Background
        g.setColor(new Color(25, 25, 35));
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Font
        g.setFont(font);
        g.setColor(Color.CYAN);


        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        // Center position
        int x = (screenWidth - textWidth) / 2;
        int y = (screenHeight - textHeight) / 2 + fm.getAscent();
        g.drawString(text, x, y);

        // Scores
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        int startY = (screenHeight / 2) + textHeight;
        g.setColor(new Color(200, 200, 80));
        drawCenteredString(g, "score: +50", screenWidth, startY);

        g.setFont(new Font("Serif", Font.ITALIC, 16));
        g.setColor(new Color(200, 200, 200));
        String hint = "Press Enter to restart";
        int hx = (screenWidth - g.getFontMetrics().stringWidth(hint)) / 2;
        g.drawString(hint, hx, screenHeight - 50);
    }

    private void drawCenteredString(Graphics g, String text, int width, int y) {
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (width - textWidth) / 2, y);
    }
}
