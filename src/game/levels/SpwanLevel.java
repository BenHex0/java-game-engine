package game.levels;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import engine.entities.Entity;
import engine.input.InputHandler;
import engine.levels.Level;
import game.entities.*;

public class SpwanLevel extends Level {

    Player player;
    Enemy enemy;

    public SpwanLevel(int width, int height, InputHandler input) {
        super(width, height, input);
        start();
    }

    public SpwanLevel(String path) {
        super(path);
        start();
    }

    protected void loadLevel(String path) {
        try {
            java.io.File file = new java.io.File(path);
            BufferedImage image = ImageIO.read(file);
            width = image.getWidth();
            height = image.getHeight();
            tiles = new int[width * height];
            image.getRGB(0, 0, width, height, tiles, 0, width);
        } catch (Exception e) {
            e.printStackTrace();
        }

        start();
    }

    void start() {
        player = new Player(30, 15, input);
        enemy = new Enemy(10, 10);
        add(player);
        add(enemy);
    }

    @Override
    public void currentLevelUpdate() {
        kill(player, enemy);
    }

    boolean isColliding(Entity a, Entity b) {
        return a.getX() < b.getX() + b.getSprite().getWidth() &&
                a.getX() + a.getSprite().getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getSprite().getHeight() &&
                a.getY() + a.getSprite().getHeight() > b.getY();
    }

    void kill(Entity e1, Entity e2) {
        if (isColliding(e1, e2)) {
            System.out.println("Dead!");
            // player.die = true;
        }
    }

}
