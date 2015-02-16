package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.anim.Animation;
import pl.codesharks.games.colorgame.resources.ColorLib;
import pl.codesharks.games.colorgame.GameEngine;
import pl.codesharks.games.colorgame.Tag;

import java.awt.*;


public class AnimatedObject extends GameObject {

    public static final int MAX_SPEED_X = 35;
    public static final int MAX_SPEED_Y = 35;
    public final int WIDTH = 32;
    public final int HEIGHT = 32;
    private Animation animation;


    public AnimatedObject(int x, int y, Tag tag, Animation animation) {
        super(x, y, tag);
        this.animation = animation;
        animation.start();
    }

    @Override
    public void update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;
        x = GameEngine.clamp((int) x, 0, (float) (GameEngine.WIDTH - WIDTH));
        y = GameEngine.clamp((int) y, 0, (float) (GameEngine.HEIGHT - HEIGHT));
        animation.update();
    }

    @Override
    public void render(Graphics g, int renderType) {
        g.setColor(ColorLib.PLAYER);

        if (renderType == RENDER_TYPE_DEFAULT) {
            g.drawImage(animation.getSprite(), (int) x, (int) y, null);
        } else if (renderType == RENDER_TYPE_BOUNDS) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(getBounds());
        }
    }

    @Override
    public void start() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

}
