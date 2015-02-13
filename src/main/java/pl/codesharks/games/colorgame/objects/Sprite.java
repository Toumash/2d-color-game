package pl.codesharks.games.colorgame.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Sprite {
    //TODO::CHANGE PUBLIC TO PRIVATE ONCE COMPLETED OBJECTS->Sprites Transform
    public BufferedImage image;

    public Sprite(BufferedImage image) {
        this.image = image;
    }

    public Sprite(Image image) {
        this(SpriteManager.toBufferedImage(image));
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }
}
