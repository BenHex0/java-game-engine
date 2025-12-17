package engine.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class UI {

    private Rectangle startBtn;
    private Rectangle optionsBtn;
    private Rectangle exitBtn;

    private int mouseX, mouseY;
    private boolean mousePressed;

    public UI(int screenWidth, int screenHeight) {
        int btnWidth = 220;
        int btnHeight = 50;
        int x = (screenWidth - btnWidth) / 2;
        int y = screenHeight / 2;
        
        startBtn = new Rectangle(x, y, btnWidth, btnHeight);
        optionsBtn = new Rectangle(x, y + 70, btnWidth, btnHeight);
        exitBtn = new Rectangle(x, y + 140, btnWidth, btnHeight);
    }

    public void update() {
        // click handling
        if (mousePressed) {
            if (startBtn.contains(mouseX, mouseY)) {
                System.out.println("Start Game");
            } else if (optionsBtn.contains(mouseX, mouseY)) {
                System.out.println("Options");
            } else if (exitBtn.contains(mouseX, mouseY)) {
                System.exit(0);
            }
            mousePressed = false;
        }
    }

    public void render(Graphics g) {
        // Background
        g.setColor(new Color(25, 25, 35));
        g.fillRect(0, 0, 10000, 10000);

        // Title
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.setColor(Color.WHITE);
        g.drawString("MY GAME", centerX(g, "MY GAME") - 100, 150);

        // Buttons
        drawButton(g, startBtn, "START");
        drawButton(g, optionsBtn, "OPTIONS");
        drawButton(g, exitBtn, "EXIT");
    }

    private void drawButton(Graphics g, Rectangle btn, String text) {
        boolean hover = btn.contains(mouseX, mouseY);

        g.setColor(hover ? new Color(70, 130, 180) : new Color(50, 90, 140));
        g.fillRect(btn.x, btn.y, btn.width, btn.height);

        g.setColor(Color.WHITE);
        g.drawRect(btn.x, btn.y, btn.width, btn.height);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(
                text,
                btn.x + (btn.width - g.getFontMetrics().stringWidth(text)) / 2,
                btn.y + 32);
    }

    private int centerX(Graphics g, String text) {
        return (1000 - g.getFontMetrics().stringWidth(text)) / 2;
    }

    // Call this from your MouseListener
    public void onMousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mousePressed = true;
    }

    // Call this from your MouseMotionListener
    public void onMouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
