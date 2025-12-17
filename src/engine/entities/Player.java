package engine.entities;

import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.input.InputHandler;
import engine.utilities.Vector2i;

public class Player extends Entity {

    private InputHandler input;
    private int xAxis, yAxis;
    private int animate = 0;
    private boolean walking = true;
    private double speed = 3.5;

    public Player(double x, double y, InputHandler inputHandler) {
        this.x = x;
        this.y = y;
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
            x += (Math.signum(xAxis) * speed);
        }

        // Move Y
        if (yAxis != 0) {
            y += ((Math.signum(yAxis) * speed) * -1);
        }
    }

    @Override
    public void render(Renderer renderer) {
        System.out.println("x: " + (int)x + ", y: " + (int)y);
        renderer.renderSprite((int) x, (int) y, sprite, true);
    }
}
