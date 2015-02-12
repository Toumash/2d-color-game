package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.Game;
import pl.codesharks.games.colorgame.HUD;
import pl.codesharks.games.colorgame.Handler;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject {
    public static final Color color = Color.YELLOW;
    public static final int MAX_SPEED_X = 35;
    public static final int MAX_SPEED_Y = 35;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    public final int TRAIL_STEP_X = 2;
    public final int TRAIL_STEP_Y = 2;
    Random r = new Random();
    float lastTrailX = x;
    float lastTrailY = y;
    private Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;
        x = Game.clamp((int) x, 0, (float) (Game.WIDTH - WIDTH));
        y = Game.clamp((int) y, 0, (float) (Game.HEIGHT - HEIGHT));

        if (Math.abs(lastTrailX - x) >= TRAIL_STEP_X || Math.abs(lastTrailY - y) >= TRAIL_STEP_Y) {
            handler.addObject(new Trail((int) x, (int) y, ID.Trail, color, WIDTH, HEIGHT, 0.1f, handler));
            lastTrailX = x;
            lastTrailY = y;
        }
        checkCollisions();
    }

    @Override
    public void render(Graphics g, int renderType) {
        g.setColor(color);

        if (renderType == RENDER_TYPE_DEFAULT) {
            g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
        } else if (renderType == RENDER_TYPE_BOUNDS) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(getBounds());
        }
    }

    private void checkCollisions() {
        GameObject obj;
        for (int i = 0, length = handler.objects.size(); i < length; i++) {
            obj = handler.objects.get(i);
            if (obj.getId() == ID.BasicEnemy || obj.getId() == ID.FastEnemy || obj.getId() == ID.SmartEnemy) {
                if (getBounds().intersects(obj.getBounds())) {
                    HUD.HEALTH -= 2;
                }
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

}
