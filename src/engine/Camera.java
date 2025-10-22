package engine;

import entities.Entity;

public class Camera {
    public int x, y; // top-left position in the world
    public double zoom = 1.0; // optional, for scaling
    private int screenWidth;
    private int screenHeight;

    public Camera(int screenWidth, int screenHeight) {
        this.x = 0;
        this.y = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    // Convert world coordinates to screen coordinates
    public int worldToScreenX(int worldX) {
        return (int) ((worldX - x) * zoom);
    }

    public int worldToScreenY(int worldY) {
        return (int) ((worldY - y) * zoom);
    }

    public void cameraTarget(Entity target) {
        x = target.position.x - (screenWidth / 2) + target.sprite.width;
        y = target.position.y - (screenHeight / 2) + target.sprite.height;
    }
}
