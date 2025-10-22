package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;


public class Sprite implements Drawable {
    private BufferedImage image;
    public int width, height;
    private int x, y;
    private int scale;

    public Sprite() {
        image = null;
        scale = 0;
    }

    public Sprite(String pathName) {
        try {
            image = ImageIO.read(new File(pathName));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void addImage(String pathName) {
        try {
            image = ImageIO.read(new File(pathName));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (image == null)
            return;

        if (scale > 0) {
            g2d.drawImage(image, x, y, width * scale, height * scale, null); // original size
        } else {
            g2d.drawImage(image, x, y, null); // original size
        }
    }

}
