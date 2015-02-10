package pl.codesharks.games.colorgame;

import java.awt.*;

public class Trail extends GameObject {

    private final Color color;
    private final int height, width;
    private float alpha = 1f;
    private Handler handler;
    private ID id;
    /**
     * 0.001 - 0.1
     */
    private float life;

    public Trail(int x, int y, ID id, Color color, int width, int height, float life, Handler handler) {
        super(x, y, id);
        this.id = id;
        this.color = color;
        this.width = width;
        this.height = height;
        this.life = life;

        this.handler = handler;
    }

    private AlphaComposite makeTransparent(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    @Override
    public void tick() {
        if (alpha > life) {
            alpha -= life;
        } else {
            handler.removeObject(this);
        }
    }

    @Override
    public void render(Graphics g, int drawType) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(makeTransparent(alpha));
        g.setColor(color);
        g.fillRect((int)x, (int)y, width, height);


        g2d.setComposite(makeTransparent(1));
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
