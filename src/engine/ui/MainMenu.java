package engine.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.Engine;
import engine.input.InputHandler;

public class MainMenu extends UI {

    public MainMenu(int screenWidth, int screenHeight, InputHandler input) {
        super(screenWidth, screenHeight, input);
    }

    @Override
    public void update() {
        if (input.isKeyPressed(InputHandler.Key.ENTER)) {
            System.out.println("Go");
            Engine.gameState = Engine.gameplay;
        }
    }

    @Override
    public void render(Graphics g) {
        // Background
        g.setColor(new Color(25, 25, 35));
        g.fillRect(0, 0, 10000, 10000);

        // Title
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        g.drawString("MY GAME", 150, 150);
    }
}
