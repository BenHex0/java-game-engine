package graphics;

import engine.Position;

public class Tile {
    public Position position;
    public Sprite sprite;

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    public boolean solid() {
        return false;
    }
}
