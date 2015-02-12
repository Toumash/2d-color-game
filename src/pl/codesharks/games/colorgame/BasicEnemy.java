package pl.codesharks.games.colorgame;

import java.awt.*;


public class BasicEnemy extends GameObject {
    public static final Color color = Color.RED;
    public static final int MAX_SPEED_X = 20;
    public static final int MAX_SPEED_Y = 20;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private Handler handler;

    float lastTrailX = x;
    float lastTrailY = y;
    public final int TRAIL_STEP_X = 5;
    public final int TRAIL_STEP_Y = 5;

    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        velX = MAX_SPEED_X;
        velY = MAX_SPEED_Y;
        this.handler = handler;
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
        handler.addObject(new Trail((int) x, (int) y, ID.Trail, Color.RED, WIDTH, HEIGHT, 0.1f, handler));
    }

    @Override
    public void render(Graphics g, int renderType) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);

        switch (renderType) {
            case GameObject.RENDER_TYPE_DEFAULT:
                g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
                break;
            case GameObject.RENDER_TYPE_BOUNDS:
                g2d.draw(getBounds());
                break;

        }


    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }
}
