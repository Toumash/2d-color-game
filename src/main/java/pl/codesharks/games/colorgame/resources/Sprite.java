package pl.codesharks.games.colorgame.resources;

import pl.codesharks.games.colorgame.anim.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
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

    public SpriteSheet toSpriteSheet(int tileSizeX, int tileSizeY) {
        return new SpriteSheet(this.image, tileSizeX, tileSizeY);
    }

}
