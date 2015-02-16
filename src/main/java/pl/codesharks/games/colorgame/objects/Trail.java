package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.Tag;

import java.awt.*;

public class Trail extends GameObject {

    private final Color color;
    private final int height, width;
    /**
     * 0.001 - 0.1
     */
    public float step;
    public float alpha = 1f;
    int alphaCompositeType = AlphaComposite.SRC_OVER;

    public Trail(int x, int y, Color color, int width, int height, float step) {
        super(x, y, Tag.Neutral);
        this.color = color;
        this.width = width;
        this.height = height;
        this.step = step;

    }

    private AlphaComposite makeTransparent(float alpha) {
        return (AlphaComposite.getInstance(alphaCompositeType, alpha));
    }

    @Override
    public void update(float deltaTime) {
        if (alpha > 0) {
            alpha -= step;
        }
        if (alpha < 0) {
            alpha = 0;
        }
    }

    @Override
    public void render(Graphics g, int renderType) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(makeTransparent(alpha));
        g.setColor(color);

        switch (renderType) {
            case RENDER_TYPE_DEFAULT:
                g.fillRect((int) x, (int) y, width, height);
                break;
            case RENDER_TYPE_BOUNDS:
                g.drawRect((int) x, (int) y, width, height);
                break;
        }
        g2d.setComposite(makeTransparent(1));
    }

    @Override
    public void start() {

    }

    public void reset(float newX, float newY) {
        this.step = 0.1f;
        this.alpha = 1f;
        this.setX(newX);
        this.setY(newY);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
