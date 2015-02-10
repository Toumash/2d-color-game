package pl.codesharks.games.colorgame;

import java.awt.*;


public class BasicEnemy extends GameObject {
    public static final Color color = Color.RED;
    private Handler handler;


    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        velX = 5;
        velY = 5;
        this.handler = handler;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
        if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;

        handler.addObject(new Trail((int)x, (int)y, ID.Trail, Color.RED, 16, 16, 0.1f, handler));
    }

    @Override
    public void render(Graphics g, int drawType) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);

        switch (drawType) {
            case GameObject.RENDER_DEFAULT:
                g.fillRect((int)x, (int)y, 16, 16);
                break;
            case GameObject.RENDER_BOUNDS:
                g2d.draw(getBounds());
                break;

        }


    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }
}
