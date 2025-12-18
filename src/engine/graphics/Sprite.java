package engine.graphics;

import engine.utilities.Vector2i;

public class Sprite {
    private int x, y;
    private int width, height;
    public final int SIZE;
    public int pixels[];
    public SpriteSheet sheet;
    public Vector2i spritPivot;

    public static Sprite player = new Sprite(16, 16, 0, 1, SpriteSheet.playerSheet);
    // public static Sprite player = new Sprite(16, 16, 0, 0,
    // SpriteSheet.playerIdleSheet);

    public static Sprite voidSprite = new Sprite(16, 16, 0X2784F5);

    // world tiles
    ////////////////////////////////////////////////////////////////////////////////////////
    public static Sprite grass0 = new Sprite(16, 4, 4, SpriteSheet.tiles);
    public static Sprite grass1 = new Sprite(16, 5, 4, SpriteSheet.tiles);
    public static Sprite grass2 = new Sprite(16, 6, 2, SpriteSheet.tiles);
    public static Sprite grass3 = new Sprite(16, 4, 5, SpriteSheet.tiles);
    public static Sprite grass4 = new Sprite(16, 5, 5, SpriteSheet.tiles);
    public static Sprite grass5 = new Sprite(16, 6, 5, SpriteSheet.tiles);
    public static Sprite grass6 = new Sprite(16, 4, 6, SpriteSheet.tiles);
    public static Sprite grass7 = new Sprite(16, 5, 6, SpriteSheet.tiles);
    public static Sprite grass8 = new Sprite(16, 6, 6, SpriteSheet.tiles);
    ////////////////////////////////////////////////////////////////////////////////////////

    // world edge tiles
    ////////////////////////////////////////////////////////////////////////////////////////
    public static Sprite upEdge0 = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite upEdge1 = new Sprite(16, 2, 0, SpriteSheet.tiles);
    public static Sprite upEdge2 = new Sprite(16, 3, 0, SpriteSheet.tiles);
    public static Sprite upEdge3 = new Sprite(16, 4, 0, SpriteSheet.tiles);
    public static Sprite upEdge4 = new Sprite(16, 5, 0, SpriteSheet.tiles);

    // public static Sprite edge5 = new Sprite(16, 6, 5, SpriteSheet.tiles);
    // public static Sprite edge6 = new Sprite(16, 4, 6, SpriteSheet.tiles);
    // public static Sprite edge7 = new Sprite(16, 5, 6, SpriteSheet.tiles);
    // public static Sprite edge8 = new Sprite(16, 6, 6, SpriteSheet.tiles);
    ////////////////////////////////////////////////////////////////////////////////////////

    protected Sprite(SpriteSheet sheet, int width, int height) {
        if (width == height)
            SIZE = width;
        else
            SIZE = -1;
        this.width = width;
        this.height = height;
        this.sheet = sheet;

    }

    public Sprite(int[] pixels, int width, int height) {
        if (width == height)
            SIZE = width;
        else
        SIZE = -1;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public Sprite(int width, int height, int color) {
        SIZE = width * height;
        this.width = width;
        this.height = height;
        pixels = new int[SIZE];
        setColor(color);
    }

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        this.sheet = sheet;
        this.x = x * size;
        this.y = y * size;
        load();
    }

    public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
        SIZE = width * height;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        this.sheet = sheet;
        this.x = x * width;
        this.y = y * height;
        load();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2i getSpritPivot() {
        return new Vector2i(width / 2, height / 2);
    }

    private void load() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
            }
        }
    }

    private void setColor(int color) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = color;
        }
    }
}
