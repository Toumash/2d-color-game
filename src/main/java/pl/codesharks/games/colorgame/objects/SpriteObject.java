package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteObject extends GameObject {
    private int HEIGHT;
    private int WIDTH;
    private BufferedImage image;

    public SpriteObject(float x, float y, ID id, BufferedImage image) {
        super(x, y, id);
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
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y);
    }
}
