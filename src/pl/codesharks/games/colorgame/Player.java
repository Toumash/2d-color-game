package pl.codesharks.games.colorgame;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject {
    public static final int VELOCITY_MAX_X = 35;
    public static final int VELOCITY_MAX_Y = 35;
    public static final int OBJ_WIDTH = 32;
    public static final int OBJ_HEIGHT = 32;

    Random r = new Random();

    private Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;
        x = Game.clamp((int) x, 0, (float) (Game.WIDTH - OBJ_WIDTH));
        y = Game.clamp((int) y, 0, (float) (Game.HEIGHT - OBJ_HEIGHT));

        handler.addObject(new Trail((int) x, (int) y, ID.Trail, Color.white, 32, 32, 0.2f, handler));

        checkCollisions();
    }

    @Override
    public void render(Graphics g, int renderType) {
        g.setColor(Color.WHITE);

        if (renderType == GameObject.RENDER_TYPE_DEFAULT) {
            g.fillRect((int) x, (int) y, OBJ_WIDTH, OBJ_HEIGHT);
        } else if (renderType == GameObject.RENDER_TYPE_BOUNDS) {
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
        return new Rectangle((int) x, (int) y, OBJ_WIDTH, OBJ_HEIGHT);
    }

}
