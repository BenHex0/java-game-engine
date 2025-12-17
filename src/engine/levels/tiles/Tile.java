package engine.levels.tiles;

import engine.graphics.*;

public class Tile {
    public Sprite sprite;

    //// world tiles
    ////////////////////////////////////////////////////////////////////////////////////////
    public static Tile grass0 = new Grass_0_Tile(Sprite.grass0);
    public static Tile grass1 = new Grass_0_Tile(Sprite.grass1);
    public static Tile grass2 = new Grass_0_Tile(Sprite.grass2);
    public static Tile grass3 = new Grass_0_Tile(Sprite.grass3);
    public static Tile grass4 = new Grass_0_Tile(Sprite.grass4);
    public static Tile grass5 = new Grass_0_Tile(Sprite.grass5);
    public static Tile grass6 = new Grass_0_Tile(Sprite.grass6);
    public static Tile grass7 = new Grass_0_Tile(Sprite.grass7);
    public static Tile grass8 = new Grass_0_Tile(Sprite.grass8);
    ////////////////////////////////////////////////////////////////////////////////////////
    
    public static Tile voidTile = new VoidTile(Sprite.voidSprite);

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    // override it
    public void render(int x, int y, Renderer renderer) {
    }

    public boolean solid() {
        return false;
    }
}
