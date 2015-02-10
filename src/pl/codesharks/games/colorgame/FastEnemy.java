package pl.codesharks.games.colorgame;

import java.awt.*;


public class FastEnemy extends GameObject {
public static final Color color = Color.CYAN;
    private Handler handler;


    public FastEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        velX = 2;
        velY = 9;
        this.handler = handler;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
        if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;

        handler.addObject(new Trail((int)x,(int)y,ID.Trail,color,16,16,0.1f,handler));
    }

    @Override
    public void render(Graphics g, int drawType) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);

        switch (drawType) {
            case RENDER_DEFAULT:
                g.fillRect((int)x, (int)y, 16, 16);
                break;
            case RENDER_BOUNDS:
                g2d.draw(getBounds());
                break;

        }


    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }
}
