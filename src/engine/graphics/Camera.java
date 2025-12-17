package engine.graphics;

import engine.utilities.Position;

public class Camera {
    private double xOffset, yOffset;
    private int screenWidth, screenHeight;

    public Camera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public int getxOffset() {
        return (int) Math.round(xOffset);
    }

    public int getyOffset() {
        return (int) Math.round(yOffset);
    }

    public void cameraTarget(Position target, int width, int height) {
        xOffset = target.x - screenWidth / 2 + width / 2;
        yOffset = target.y - screenHeight / 2 + height / 2;
    }
}
