package engine.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import engine.levels.tiles.*;

public class Renderer {
    public int screenWidth, screenHeight;
    public int[] screenBuffer;
    public BufferedImage frame;
    public Camera camera;

    public Renderer(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        screenBuffer = new int[width * height];
        frame = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        screenBuffer = ((DataBufferInt) frame.getRaster().getDataBuffer()).getData();
        camera = new Camera(width, height);
    }

    public void clear() {
        for (int i = 0; i < screenBuffer.length; i++) {
            screenBuffer[i] = 0;
        }
    }

    public void renderSprite(int xPosition, int yPosition, Sprite sprite, boolean fixed) {
        if (fixed) {
            xPosition -= camera.getxOffset();
            yPosition -= camera.getyOffset();
        }

        for (int y = 0; y < sprite.getHeight(); y++) {
            int yAbsolute = y + yPosition;
            for (int x = 0; x < sprite.getWidth(); x++) {
                int xAbsolute = x + xPosition;

                if (xAbsolute < -sprite.getWidth() || xAbsolute >= screenWidth || yAbsolute < 0
                        || yAbsolute >= screenHeight)
                    break;
                if (xAbsolute < 0)
                    xAbsolute = 0;
                int col = sprite.pixels[x + y * sprite.getWidth()];
                if (col != 0x00000000)
                    screenBuffer[xAbsolute + yAbsolute * screenWidth] = col;

            }
        }
    }

    public void renderTile(int xPosition, int yPosition, Tile tile) {
        xPosition -= camera.getxOffset();
        yPosition -= camera.getyOffset();

        Sprite sprite = tile.sprite;

        for (int y = 0; y < sprite.getHeight(); y++) {
            int yAbsolute = y + yPosition;

            if (yAbsolute < 0 || yAbsolute >= screenHeight)
                continue;

            for (int x = 0; x < sprite.getWidth(); x++) {
                int xAbsolute = x + xPosition;

                if (xAbsolute < 0 || xAbsolute >= screenWidth)
                    continue;

                int index = x + y * sprite.getWidth();
                int color = sprite.pixels[index];

                screenBuffer[xAbsolute + yAbsolute * screenWidth] = color;
            }
        }
    }

    public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
        if (fixed) {
            xp -= camera.getxOffset();
            yp -= camera.getyOffset();
        }

        for (int x = xp; x < xp + width; x++) {
            if (x < 0 || x >= this.screenWidth || yp >= this.screenHeight)
                continue;
            if (yp > 0)
                screenBuffer[x + yp * this.screenWidth] = color;
            if (yp + height >= this.screenHeight)
                continue;
            if (yp + height > 0)
                screenBuffer[x + (yp + height) * this.screenWidth] = color;
        }

        for (int y = yp; y <= yp + height; y++) {
            if (xp >= this.screenWidth || y < 0 || y >= this.screenHeight)
                continue;
            if (xp > 0)
                screenBuffer[xp + y * this.screenWidth] = color;
            if (xp + width >= this.screenWidth)
                continue;
            if (xp + width > 0)
                screenBuffer[(xp + width) + y * this.screenWidth] = color;
        }
    }

    public void renderPixel(int x, int y, int color) {
        x -= camera.getxOffset();
        y -= camera.getyOffset();

        if ((x > 0 && y > 0) && (x < screenWidth && y < screenHeight))
            screenBuffer[x + y * screenWidth] = color;
    }
}
