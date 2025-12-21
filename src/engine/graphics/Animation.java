package engine.graphics;

public class Animation extends Sprite {

    private int frame = 0;
    private Sprite sprite;
    private int rate = 7;
    private int length;
    private int timer = 0;

    private boolean firstFrame = true;

    public Animation(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;

        sprite = sheet.getSprites()[0];

        if (length > sheet.getSprites().length) {
            System.err.println("Error! length of animation is too long");
        }
    }

    public void update() {

        if (firstFrame) {
            firstFrame = false;
            sprite = sheet.getSprites()[1];
            return;
        }

        timer++;

        if (timer >= rate) {
            timer = 0;
            frame = (frame + 1) % length;
            sprite = sheet.getSprites()[frame];
        }
    }

    public void resetAnimation() {
        firstFrame = true;
        sprite = sheet.getSprites()[0];
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setFrameRate(int rate) {
        this.rate = rate;
    }

    public void setFrame(int index) {
        if (index < 0 || index >= sheet.getSprites().length) {
            System.err.println("Index out of bounds in " + this);
            return;
        }
        sprite = sheet.getSprites()[index];
    }
}
