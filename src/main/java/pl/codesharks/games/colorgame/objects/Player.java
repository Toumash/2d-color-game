package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.GameData;
import pl.codesharks.games.colorgame.GameEngine;
import pl.codesharks.games.colorgame.Tag;
import pl.codesharks.games.colorgame.resources.ColorLib;
import pl.codesharks.games.colorgame.resources.GameObjectManager;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class Player extends GameObject {
    public static final int MAX_SPEED_X = 35;
    public static final int MAX_SPEED_Y = 35;
    public final int WIDTH = 32;
    public final int HEIGHT = 32;

    public final int MAX_TRAIL_AMOUNT = 5;
    public final int TRAIL_STEP_X = 8;
    public final int TRAIL_STEP_Y = 8;
    public Queue<Trail> trailParts = new ArrayDeque<Trail>();
    float lastTrailX = x;
    float lastTrailY = y;
    private GameObjectManager gameObjectManager;

    public Player(int x, int y, Tag tag) {
        super(x, y, tag);
        this.gameObjectManager = GameObjectManager.getInstance();
    }

    @Override
    public void update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;
        x = GameEngine.clamp((int) x, 0, (float) (GameEngine.WIDTH - WIDTH));
        y = GameEngine.clamp((int) y, 0, (float) (GameEngine.HEIGHT - HEIGHT));

        if (Math.abs(lastTrailX - x) >= TRAIL_STEP_X || Math.abs(lastTrailY - y) >= TRAIL_STEP_Y) {
            if (trailParts.size() < MAX_TRAIL_AMOUNT) {
                trailParts.add(new Trail((int) x, (int) y, ColorLib.PLAYER, WIDTH, HEIGHT, 0.1f));
            } else {
                Trail tmp = trailParts.poll();
                tmp.reset(x, y);
                trailParts.add(tmp);
            }
            lastTrailX = x;
            lastTrailY = y;
        }
        checkCollisions();

        for (Trail t : trailParts) {
            t.update(deltaTime);
        }
    }

    @Override
    public void render(Graphics g, int renderType) {
        g.setColor(ColorLib.PLAYER);

        for (Trail t : trailParts) {
            t.render(g, renderType);
        }

        if (renderType == RENDER_TYPE_DEFAULT) {
            g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
            g.setColor(ColorLib.PLAYER_OUTLINE);
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(getBounds());
        } else if (renderType == RENDER_TYPE_BOUNDS) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(getBounds());
        }
    }

    @Override
    public void start() {

    }

    private void checkCollisions() {
        GameObject obj;
        for (int i = 0, length = gameObjectManager.getSize(); i < length; i++) {
            obj = gameObjectManager.objects.get(i);
            if (obj.getTag() == Tag.BasicEnemy || obj.getTag() == Tag.FastEnemy || obj.getTag() == Tag.SmartEnemy) {
                if (getBounds().intersects(obj.getBounds())) {
                    GameData.getInstance().subtractHp(5);
                }
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

}
