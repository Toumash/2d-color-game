package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.ColorLib;
import pl.codesharks.games.colorgame.GameEngine;
import pl.codesharks.games.colorgame.GameObjectManager;
import pl.codesharks.games.colorgame.HUD;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class Player extends GameObject {
    public static final int MAX_SPEED_X = 35;
    public static final int MAX_SPEED_Y = 35;
    public final int WIDTH = 32;
    public final int HEIGHT = 32;

    public final int MAX_TRAIL_AMOUNT = 10;
    public final int TRAIL_STEP_X = 2;
    public final int TRAIL_STEP_Y = 2;
    public Queue<Trail> trails = new ArrayDeque<Trail>();
    float lastTrailX = x;
    float lastTrailY = y;
    private GameObjectManager gameObjectManager;

    public Player(int x, int y, ID id, GameObjectManager gameObjectManager) {
        super(x, y, id);
        this.gameObjectManager = gameObjectManager;
    }

    @Override
    public void update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;
        x = GameEngine.clamp((int) x, 0, (float) (GameEngine.WIDTH - WIDTH));
        y = GameEngine.clamp((int) y, 0, (float) (GameEngine.HEIGHT - HEIGHT));

        if (Math.abs(lastTrailX - x) >= TRAIL_STEP_X || Math.abs(lastTrailY - y) >= TRAIL_STEP_Y) {
            if (trails.size() < MAX_TRAIL_AMOUNT) {
                trails.add(new Trail((int) x, (int) y, ID.Trail, ColorLib.PLAYER, WIDTH, HEIGHT, 0.1f, gameObjectManager));
            } else {
                Trail tmp = trails.poll();
                tmp.reset(x, y);
                trails.add(tmp);
            }
            // gameObjectManager.addObject(new Trail((int) x, (int) y, ID.Trail, color, WIDTH, HEIGHT, 0.1f, gameObjectManager));
            lastTrailX = x;
            lastTrailY = y;
        }
        checkCollisions();

        for (Trail t : trails) {
            t.update(deltaTime);
        }
    }

    @Override
    public void render(Graphics g, int renderType) {
        g.setColor(ColorLib.PLAYER);

        if (renderType == RENDER_TYPE_DEFAULT) {
            g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
        } else if (renderType == RENDER_TYPE_BOUNDS) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(getBounds());
        }

        for (Trail t : trails) {
            t.render(g, renderType);
        }
    }

    private void checkCollisions() {
        GameObject obj;
        for (int i = 0, length = gameObjectManager.getSize(); i < length; i++) {
            obj = gameObjectManager.objects.get(i);
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
