package engine.levels;

import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

import engine.entities.Entity;
import engine.graphics.*;
import engine.input.InputHandler;
import engine.levels.tiles.*;

public class Level {
    protected int worldWidth, worldHeight;
    protected int[] tilesInt;
    protected int[] tiles;

    protected InputHandler input;

    // entities
    public List<Entity> entities = new ArrayList<Entity>();

    public Level(int width, int height, InputHandler input) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.input = input;
        tilesInt = new int[width * height];
        tiles = new int[width * height];
        generateLevel();
    }

    public Level(String path, InputHandler input) {
        this.input = input;
        loadLevel(path);
        System.out.println("Generating level...");
    }

    private Random random = new Random();

    protected void generateLevel() {
        for (int i = 0; i < worldWidth * worldHeight; i++) {
            tiles[i] = random.nextInt(8);
        }
    }

    protected void loadLevel(String path) {
        try {
            java.io.File file = new java.io.File(path);
            BufferedImage image = ImageIO.read(file);
            worldWidth = image.getWidth();
            worldHeight = image.getHeight();
            tiles = new int[worldWidth * worldHeight];
            image.getRGB(0, 0, worldWidth, worldHeight, tiles, 0, worldWidth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // override it
    public void currentLevelUpdate() {}

    public void update() {
        for (Entity e : entities) {
            e.update();
        }

        currentLevelUpdate();
    }

    public void render(Renderer renderer) {
        renderTileMap(renderer);

        for (Entity e : entities) {
            e.render(renderer);
        }
    }

    private void renderTileMap(Renderer renderer) {
        int camX = (int) renderer.camera.getxOffset();
        int camY = (int) renderer.camera.getyOffset();

        int x0 = camX >> 4;
        int y0 = camY >> 4;
        int x1 = (camX + renderer.screenWidth + 16) >> 4;
        int y1 = (camY + renderer.screenHeight + 16) >> 4;

        x0 = Math.max(0, x0);
        y0 = Math.max(0, y0);
        x1 = Math.min(worldWidth, x1);
        y1 = Math.min(worldHeight, y1);

        // System.out.println(x1 + " " + y1);

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).render(x, y, renderer);
            }
        }
    }

    public void add(Entity e) {
        e.init(this);
        entities.add(e);
    }

    // public void addCurrentLevel(Level level) {
    // this.currentLevel = level;
    // }

    // grass0 = green => 0xff1CC809
    // grass1 = green + yello => 0xffF4E80B
    // tree = brown => 0xff703405

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= worldWidth || y >= worldHeight)
            return Tile.voidTile;
        if (tiles[x + y * worldWidth] == 0xffff0000)
            return Tile.hut;
        if (tiles[x + y * worldWidth] == 0xff2784f5)
            return Tile.water0;
        if (tiles[x + y * worldWidth] == 0xff6fb2e1)
            return Tile.water1;
        if (tiles[x + y * worldWidth] == 0xff362920)
            return Tile.tree0;
        if (tiles[x + y * worldWidth] == 0xff56483d)
            return Tile.tree1;
        if (tiles[x + y * worldWidth] == 0xff67a55e)
            return Tile.grassFlat;
        if (tiles[x + y * worldWidth] == 0xff5d9556)
            return Tile.grassDetail;
        if (tiles[x + y * worldWidth] == 0xff5d9556)
            return Tile.grassWaterLeft;
        if (tiles[x + y * worldWidth] == 0xff387882)
            return Tile.grassWaterRight;
        if (tiles[x + y * worldWidth] == 0xff50a0ad)
            return Tile.grassWaterUp;
        if (tiles[x + y * worldWidth] == 0xff3e6268)
            return Tile.grassWaterDown;
        if (tiles[x + y * worldWidth] == 0xff99c2bd)
            return Tile.grassWaterUpLeft;
        if (tiles[x + y * worldWidth] == 0xff38beac)
            return Tile.grassWaterUpRight;
        if (tiles[x + y * worldWidth] == 0xff7d8f8c)
            return Tile.grassWaterDownLeft;
        if (tiles[x + y * worldWidth] == 0xff102b27)
            return Tile.grassWaterDownRight;
        return Tile.voidTile;
    }
}