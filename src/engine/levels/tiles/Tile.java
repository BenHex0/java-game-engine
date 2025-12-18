package engine.levels.tiles;

import engine.graphics.*;

public class Tile {
    public Sprite sprite;


    /// ** last version **///
    ////////////////////////////////////////////////////////////////////////////////////////
    public static Tile hut = new Grass_0_Tile(Sprite.hut);
    public static Tile water0 = new Grass_0_Tile(Sprite.water0);
    public static Tile water1 = new Grass_0_Tile(Sprite.water1);
    public static Tile tree0 = new Grass_0_Tile(Sprite.tree0);
    public static Tile tree1 = new Grass_0_Tile(Sprite.tree1);

    public static Tile grassFlat = new Grass_0_Tile(Sprite.grassFlat);
    public static Tile grassDetail = new Grass_0_Tile(Sprite.grassDetail);
    
    public static Tile grassWaterLeft = new Grass_0_Tile(Sprite.grassWaterLeft);
    public static Tile grassWaterRight = new Grass_0_Tile(Sprite.grassWaterRight);
    public static Tile grassWaterUp = new Grass_0_Tile(Sprite.grassWaterUp);
    public static Tile grassWaterDown = new Grass_0_Tile(Sprite.grassWaterDown);
    public static Tile grassWaterUpLeft = new Grass_0_Tile(Sprite.grassWaterUpLeft);
    public static Tile grassWaterUpRight = new Grass_0_Tile(Sprite.grassWaterUpRight);
    public static Tile grassWaterDownLeft = new Grass_0_Tile(Sprite.grassWaterDownLeft);
    public static Tile grassWaterDownRight = new Grass_0_Tile(Sprite.grassWaterDownRight);
    ////////////////////////////////////////////////////////////////////////////////////////

    //// world tiles
    ////////////////////////////////////////////////////////////////////////////////////////
    // public static Tile grass0 = new Grass_0_Tile(Sprite.grass0);
    // public static Tile grass1 = new Grass_0_Tile(Sprite.grass1);
    // public static Tile grass2 = new Grass_0_Tile(Sprite.grass2);
    // public static Tile grass3 = new Grass_0_Tile(Sprite.grass3);
    // public static Tile grass4 = new Grass_0_Tile(Sprite.grass4);
    // public static Tile grass5 = new Grass_0_Tile(Sprite.grass5);
    // public static Tile grass6 = new Grass_0_Tile(Sprite.grass6);
    // public static Tile grass7 = new Grass_0_Tile(Sprite.grass7);
    // public static Tile grass8 = new Grass_0_Tile(Sprite.grass8);
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
