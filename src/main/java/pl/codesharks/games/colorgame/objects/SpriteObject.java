package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.Tag;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class SpriteObject extends GameObject {
    private BufferedImage image;

    public SpriteObject(float x, float y, Tag tag, BufferedImage image) {
        super(x, y, tag);
        this.image = image;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(Graphics g, int renderType) {
        g.drawImage(image, (int) x, (int) y, null);
    }

    @Override
    public void start() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y);
    }
}
