package engine.levels.tiles;

import engine.graphics.*;


public class VoidTile extends Tile {

    public VoidTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(int x, int y, Renderer renderer) {
        renderer.renderTile(x << 4, y << 4, Tile.voidTile);
    }
}
