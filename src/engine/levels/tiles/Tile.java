package engine.levels.tiles;

import engine.graphics.*;

public class Tile {
    public Sprite sprite;


    /// ** last version **///
    ////////////////////////////////////////////////////////////////////////////////////////
    // Exit
    public static Tile hut = new Exit(Sprite.hut);
    public static Tile hole = new Exit(Sprite.hole);
    // Solid
    public static Tile water0 = new Water(Sprite.water0);
    public static Tile water1 = new Water(Sprite.water1);
    public static Tile tree0 = new Solid(Sprite.tree0);
    public static Tile tree1 = new Solid(Sprite.tree1);
    public static Tile wall = new Solid(Sprite.wall);

    public static Tile grassFlat = new Walkable(Sprite.grassFlat);
    public static Tile grassDetail = new Walkable(Sprite.grassDetail);
    public static Tile Dirt =  new Walkable(Sprite.Dirt);
    
    public static Tile grassWaterLeft = new GrassWaterEdge(Sprite.grassWaterLeft);
    public static Tile grassWaterRight = new GrassWaterEdge(Sprite.grassWaterRight);
    public static Tile grassWaterUp = new GrassWaterEdge(Sprite.grassWaterUp);
    public static Tile grassWaterDown = new GrassWaterEdge(Sprite.grassWaterDown);
    public static Tile grassWaterUpLeft = new GrassWaterEdge(Sprite.grassWaterUpLeft);
    public static Tile grassWaterUpRight = new GrassWaterEdge(Sprite.grassWaterUpRight);
    public static Tile grassWaterDownLeft = new GrassWaterEdge(Sprite.grassWaterDownLeft);
    public static Tile grassWaterDownRight = new GrassWaterEdge(Sprite.grassWaterDownRight);


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
