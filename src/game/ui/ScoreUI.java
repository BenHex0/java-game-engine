package game.ui;

import engine.Engine;
import engine.input.InputHandler;
import engine.ui.UI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ScoreUI extends UI {

    // Database database;

    public ScoreUI(int screenWidth, int screenHeight, InputHandler input) {
        super(screenWidth, screenHeight, input);
        // database = new Database();
    }

    // int getScores() {
    //     return database.getTotalScore();
    // }

    @Override
    public void update() {

        if (input.isKeyPressed(InputHandler.Key.ESCAPE)) {
            Engine.setCurrentUI(Engine.mainMenuScreen);
        }
    }

    @Override
    public void render(Graphics g) {

        // Background
        g.setColor(new Color(20, 20, 30));
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Title
        g.setFont(new Font("Serif", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        drawCenteredString(g, "SCORES", screenWidth, 80);

        // Scores
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        int startY = 150;
        g.setColor(new Color(200, 200, 80));
        // drawCenteredString(g, String.valueOf(getScores()), screenWidth, startY);


        // Footer
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.LIGHT_GRAY);
        drawCenteredString(g, "ESC - Back", screenWidth, screenHeight - 30);
    }


    private void drawCenteredString(Graphics g, String text, int width, int y) {
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (width - textWidth) / 2, y);
    }
}
