package engine.entities;

import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.input.InputHandler;
import engine.utilities.Position;

public class Player extends Entity {

    private InputHandler input;
    private int xAxis, yAxis;
    private int animate = 0;
    private boolean walking = true;;
    private double speed = 2.5;

    public Player(Position position, InputHandler inputHandler) {
        this.position = position;
        this.input = inputHandler;
        sprite = Sprite.player;
    }

    public void update() {
        xAxis = 0;
        yAxis = 0;
        control();
        move(xAxis, yAxis, speed);
    }

    void control() {
        if (input.isKeyPressed(InputHandler.Key.UP)) {
            System.out.println("W");
            // move up
            yAxis = 1;
        }
        if (input.isKeyPressed(InputHandler.Key.DOWN)) {
            System.out.println("S");
            // move down
            yAxis = -1;
        }
        if (input.isKeyPressed(InputHandler.Key.LEFT)) {
            System.out.println("A");
            // move left
            xAxis = -1;
        }
        if (input.isKeyPressed(InputHandler.Key.RIGHT)) {
            System.out.println("D");
            // move right
            xAxis = 1;
        }

    }

    void move(int xAxis, int yAxis, double speed) {

        if (xAxis > 0)
            dir = Direction.RIGHT;
        else if (xAxis < 0)
            dir = Direction.LEFT;
        else if (yAxis > 0)
            dir = Direction.DOWN;
        else if (yAxis < 0)
            dir = Direction.UP;

        // Move X
        if (xAxis != 0) {
            position.x += Math.signum(xAxis) * speed;
        }

        // Move Y
        if (yAxis != 0) {
            position.y += (Math.signum(yAxis) * speed) * -1;
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderSprite((int)position.x, (int)position.y, sprite, true);
    }
}
