package engine.entities;

import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.levels.Level;
import engine.utilities.Vector2i;

public abstract class Entity {
    // position in pixels
    protected double x, y;
    protected Level level;
    protected Sprite sprite;
    public Vector2i pviot;

    protected enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected Direction dir;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2i getPviot() {
        int xPivot = sprite.getSpritPivot().getX() + (int) x;
        int yPivot = sprite.getSpritPivot().getY() + (int) y;
        return new Vector2i(xPivot, yPivot);
    }

    public abstract void update();

    public void render(Renderer renderer) {

    }

    public void init(Level level) {
        this.level = level;
    }
}
