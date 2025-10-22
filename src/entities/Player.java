package entities;

import engine.InputHandler;
import graphics.Sprite;

public class Player extends Entity {
    int speed;
    Sprite sprite;

    public Player() {
        sprite = new Sprite();
        setDefaultValues();
    }

    public void setDefaultValues() {
        setPosition(0, 0);
        speed = 4;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void start() {
        sprite.addImage("assets/player/boy_up_1.png");
        addSpriteComponent(sprite);
        addInputHandlerComponent();
        addCameraComponent();
    }
    
    @Override
    public void update() {
        
        if (input.isKeyDown(InputHandler.Key.UP)) {
            move(0, -speed);
        }
        if (input.isKeyDown(InputHandler.Key.DOWN)) {
            move(0, speed);
        }
        if (input.isKeyDown(InputHandler.Key.LEFT)) {
            move(-speed, 0);
        }
        if (input.isKeyDown(InputHandler.Key.RIGHT)) {
            move(speed, 0);
        }
        camera.cameraTarget(this);
        System.out.println("Player X = " + position.x + " Y= " + position.y);
        System.out.println("Camera X = " + camera.x + " Y= " + camera.y);
    }
}
