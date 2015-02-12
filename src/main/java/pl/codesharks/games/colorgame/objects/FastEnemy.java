package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.Game;
import pl.codesharks.games.colorgame.Handler;

import java.awt.*;


public class FastEnemy extends GameObject {
public static final Color color = Color.CYAN;
    public static final int MAX_SPEED_X = 7;
    public static final int MAX_SPEED_Y =  40;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private Handler handler;

    float lastTrailX = x;
    float lastTrailY = y;
    public final int TRAIL_STEP_X = 3;
    public final int TRAIL_STEP_Y = 3;


    public FastEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velX  = MAX_SPEED_X;
        velY = MAX_SPEED_Y;
    }

    @Override
    public void update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;

        if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
        if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;

        if (Math.abs(lastTrailX - x) >= TRAIL_STEP_X || Math.abs(lastTrailY - y) >= TRAIL_STEP_Y) {
            handler.addObject(new Trail((int) x, (int) y, ID.Trail, color, WIDTH, HEIGHT, 0.1f, handler));
            lastTrailX = x;
            lastTrailY = y;
        }
    }

    @Override
    public void render(Graphics g, int renderType) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);

        switch (renderType) {
            case RENDER_TYPE_DEFAULT:
                g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
                break;
            case RENDER_TYPE_BOUNDS:
                g2d.draw(getBounds());
                break;

        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
    }
}
