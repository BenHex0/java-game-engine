package game.entities;

import engine.entities.Entity;
import engine.graphics.Animation;
import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;
import engine.input.InputHandler;
import engine.sound.Sound;

public class Player extends Entity {

    private InputHandler input;
    private Sound soundEffect;
    private int xAxis, yAxis;
    private Animation anim_down = new Animation(SpriteSheet.playerAnimDown, 16, 16, 2);
    private Animation anim_up = new Animation(SpriteSheet.playerAnimUp, 16, 16, 2);
    private Animation anim_left = new Animation(SpriteSheet.playerAnimLeft, 16, 16, 2);
    private Animation anim_right = new Animation(SpriteSheet.playerAnimRight, 16, 16, 2);
    private Animation anim = anim_down;
    private boolean walking = false;
    private double speed = 3.5;
    private boolean once = true;
    public boolean die = false;

    public Player(double x, double y, InputHandler inputHandler) {
        this.x = x;
        this.y = y;
        this.input = inputHandler;
        sprite = Sprite.player;
        soundEffect = new Sound();
        soundEffect.setFile(1);
    }

    public void update() {
        xAxis = 0;
        yAxis = 0;
        if (!die) {
            control();
            animation();
            move(xAxis, yAxis, speed);
        } else if (once) {
            soundEffect.play();
            once = false;
        }
    }

    void control() {
        if (input.isKeyDown(InputHandler.Key.UP)) {
            // System.out.println("W");
            // move up
            yAxis = 1;
        }
        if (input.isKeyDown(InputHandler.Key.DOWN)) {
            // System.out.println("S");
            // move down
            yAxis = -1;
        }
        if (input.isKeyDown(InputHandler.Key.LEFT)) {
            // System.out.println("A");
            // move left
            xAxis = -1;
        }
        if (input.isKeyDown(InputHandler.Key.RIGHT)) {
            // System.out.println("D");
            // move right
            xAxis = 1;
        }

    }

    void move(int xAxis, int yAxis, double speed) {

        if (xAxis > 0) {
            dir = Direction.RIGHT;
            walking = true;
        } else if (xAxis < 0) {
            dir = Direction.LEFT;
            walking = true;
        } else if (yAxis < 0) {
            dir = Direction.DOWN;
            walking = true;
        } else if (yAxis > 0) {
            dir = Direction.UP;
            walking = true;
        } else {
            walking = false;
        }

        // Move X
        if (xAxis != 0) {
            double stepX = Math.signum(xAxis) * speed;
            if (!collision(stepX, 0)) {
                this.x += stepX;
            }
        }

        // Move Y
        if (yAxis != 0) {
            double stepY = (Math.signum(yAxis) * speed) * -1;
            if (!collision(0, stepY)) {
                this.y += stepY;
            }
        }
    }

    void animation() {

        if (walking) {
            anim.update();
        } else {
            anim.resetAnimation();
        }

        if (dir == Direction.DOWN) {
            anim = anim_down;
        } else if (dir == Direction.UP) {
            anim = anim_up;
        } else if (dir == Direction.LEFT) {
            anim = anim_left;
        } else if (dir == Direction.RIGHT) {
            anim = anim_right;
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (!die) {
            sprite = anim.getSprite();
            // renderer.camera.cameraTarget(x, y, sprite.getWidth(), sprite.getHeight());
            int drawX = (int) Math.round(x - renderer.camera.getxOffset());
            int drawY = (int) Math.round(y - renderer.camera.getyOffset());
            renderer.renderSprite(drawX, drawY, sprite, false);
        }
    }
}
