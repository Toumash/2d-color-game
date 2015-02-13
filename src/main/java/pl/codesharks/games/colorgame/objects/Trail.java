package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.ID;

import java.awt.*;

public class Trail extends GameObject implements Cloneable {

    private final Color color;
    private final int height, width;
    /**
     * 0.001 - 0.1
     */
    public float step;
    public volatile float alpha = 1f;
    private ID id;

    public Trail(int x, int y, ID id, Color color, int width, int height, float step) {
        super(x, y, id);
        this.id = id;
        this.color = color;
        this.width = width;
        this.height = height;
        this.step = step;

    }

    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
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
    public Trail clone() throws CloneNotSupportedException {
        return (Trail) super.clone();
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
