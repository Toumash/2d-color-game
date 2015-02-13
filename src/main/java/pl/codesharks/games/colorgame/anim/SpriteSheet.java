package pl.codesharks.games.colorgame.anim;

import pl.codesharks.games.colorgame.resources.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * pl.codesharks.games.colorgame.objects
 * Created by Tomasz on 2015-02-13.
 */
public class SpriteSheet extends Sprite {
    private int tileSize_X;
    private int tileSize_Y;

    public SpriteSheet(BufferedImage image, int tileSize_X,int tileSize_Y) {
        super(image);
        this.tileSize_X = tileSize_X;
        this.tileSize_Y = tileSize_Y;
    }

    public SpriteSheet(Image image, int tileSize_X,int tileSize_Y) {
        super(image);
        this.tileSize_X = tileSize_X;
        this.tileSize_Y = tileSize_Y;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) {

        return image.getSubimage(xGrid * tileSize_X, yGrid * tileSize_Y, tileSize_X, tileSize_Y);
    }
}
