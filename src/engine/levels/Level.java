package engine.levels;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import engine.entities.Enemy;
import engine.entities.Entity;
import engine.entities.Player;
import engine.graphics.*;
import engine.input.InputHandler;
import engine.levels.tiles.*;


public class Level {
    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;

    InputHandler input;

    // entities
    Player player;
    Enemy enemy;

    public Level(int width, int height, InputHandler input) {
        this.width = width;
        this.height = height;
        this.input = input;
        tilesInt = new int[width * height];
        tiles = new int[width * height];
        generateLevel();
    }

    public Level(String path) {
        loadLevel(path);
        System.out.println("Generating level...");
    }

    private Random random = new Random();

    protected void generateLevel() {
        for (int i = 0; i < width * height; i++) {
            tiles[i] = random.nextInt(8);
        }
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
    }

    public void update() {
        enemy.update();
    }

    public void render(int xScroll, int yScroll, Renderer renderer) {
        renderTileMap(xScroll, yScroll, renderer);
        enemy.render(renderer);
    }

    private void renderTileMap(int xScroll, int yScroll, Renderer renderer) {
        renderer.camera.setOffset(xScroll, yScroll);

        int x0 = renderer.camera.getxOffset() >> 4;
        int x1 = (renderer.camera.getxOffset() + renderer.screenWidth + 16) >> 4;
        int y0 = renderer.camera.getyOffset() >> 4;
        int y1 = (renderer.camera.getyOffset() + renderer.screenHeight + 16) >> 4;

        if (x0 < 0)
            x0 = 0;
        if (y0 < 0)
            y0 = 0;
        if (x1 > width)
            x1 = width;
        if (y1 > height)
            y1 = height;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).render(x, y, renderer);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void add(Entity e) {
        e.init(this);
        if (e instanceof Player) {
            player = (Player) e;
        }
        else if (e instanceof Enemy) {
            enemy = (Enemy) e;
        }
    }

    // grass0 = green => 0xff1CC809
    // grass1 = green + yello => 0xffF4E80B
    // tree = brown => 0xff703405

    Tile[] grassTiles = {
            Tile.grass0,
            Tile.grass1,
            Tile.grass2,
            Tile.grass3,
            Tile.grass4,
            Tile.grass5,
            Tile.grass6,
            Tile.grass7,
            Tile.grass8
    };

    public Tile getTile(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.voidTile;
        if (tiles[x + y * width] == 0)
            return grassTiles[0];
        if (tiles[x + y * width] == 1)
            return grassTiles[1];
        if (tiles[x + y * width] == 2)
            return grassTiles[2];
        if (tiles[x + y * width] == 3)
            return grassTiles[3];
        if (tiles[x + y * width] == 4)
            return grassTiles[4];
        if (tiles[x + y * width] == 5)
            return grassTiles[5];
        if (tiles[x + y * width] == 6)
            return grassTiles[6];
        if (tiles[x + y * width] == 7)
            return grassTiles[7];
        if (tiles[x + y * width] == 8)
            return grassTiles[8];
        // if (tiles[x + y * width] == 0xff1CC809)
        // return Tile.grass0;
        // if (tiles[x + y * width] == 0xffF4E80B)
        // return Tile.grass1;
        // if (tiles[x + y * width] == 0xff703405)
        // return Tile.tree;
        return Tile.voidTile;
    }
}