package engine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics2D;
import entities.Entity;


public class Renderer extends JPanel {
    // screen settings
    final double aspectRatio = 16.0 / 9.0;
    final int screenWidth = 1280;
    final int screenHeight = (int) (screenWidth / aspectRatio);
    final int scale = 3;

    // Camera
    private Camera camera = new Camera(screenWidth, screenHeight);

    // rendering data
    private ArrayList<Entity> entities = new ArrayList<>();;

    public Renderer() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public Renderer(ArrayList<Entity> entities) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        for (Entity e : entities) {
            entities.add(e);
        }
    }

    public void setCamera(Camera cam) {
        this.camera = cam;
    }

    public void addObjects(ArrayList<Entity> entit) {
        for (Entity e : entit) {
            entities.add(e);
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2d = (Graphics2D) graphics;
        // draw every game object here
        for (Entity e : entities) {
            if (e.sprite != null) {
                // world -> screen coordinates
                int screenX = (int) ((e.position.x - camera.x));
                int screenY = (int) ((e.position.y - camera.y));

                e.sprite.setPosition(screenX, screenY);
                e.sprite.setScale(scale);
                e.sprite.draw(graphics2d);
            }
        }
    }
}
