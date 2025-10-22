package entities;

import engine.Camera;
import engine.InputHandler;
import engine.Position;
import graphics.Sprite;

public abstract class Entity {
    public Position position;
    public Sprite sprite;
    public InputHandler input;
    public Camera camera;

    Entity() {
        position = new Position();
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public Position gePosition() {
        return position;
    }

    public void move(int x, int y) {
        position.x += x;
        position.y += y;
    }

    public void start() {
    }

    public void update() {
    }

    public void addSpriteComponent(Sprite sp) {
        this.sprite = sp;
        if (sprite != null) {
            sprite.setPosition(position.x, position.y);
        }
    }

    public void addInputHandlerComponent() {
        input = new InputHandler();
    }

    public void addCameraComponent() {
        camera = new Camera(0, 0);
    }
}