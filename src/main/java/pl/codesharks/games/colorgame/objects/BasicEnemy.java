package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.GameEngine;
import pl.codesharks.games.colorgame.Tag;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Queue;


public class BasicEnemy extends GameObject {
    public static final Color color = Color.decode("#FC954A");//Color.RED;
    public static final int MAX_SPEED_X = 20;
    public static final int MAX_SPEED_Y = 20;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    public static final int MAX_TRAIL_AMOUNT = 10;
    public final int TRAIL_STEP_X = 5;
    public final int TRAIL_STEP_Y = 5;
    public volatile Queue<Trail> trails = new ArrayDeque<Trail>();
    float lastTrailX = x;
    float lastTrailY = y;

    public BasicEnemy(int x, int y, Tag tag) {
        super(x, y, tag);
        velX = MAX_SPEED_X;
        velY = MAX_SPEED_Y;
    }

    @Override
    public void update(float deltaTime) {
        x += velX * deltaTime;
        y += velY * deltaTime;

        if (x <= 0 || x >= GameEngine.WIDTH - WIDTH) velX *= -1;
        if (y <= 0 || y >= GameEngine.HEIGHT - HEIGHT) velY *= -1;

        if (Math.abs(lastTrailX - x) >= TRAIL_STEP_X || Math.abs(lastTrailY - y) >= TRAIL_STEP_Y) {
            if (trails.size() < MAX_TRAIL_AMOUNT) {
                trails.add(new Trail((int) x, (int) y, color, WIDTH, HEIGHT, 0.1f));
            } else {
                Trail tmp = trails.poll();
                tmp.reset(x, y);
                trails.add(tmp);
            }
            lastTrailX = x;
            lastTrailY = y;
        }

        for (Trail t : trails) {
            t.update(deltaTime);
        }
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
        for (Trail t : trails) {
            t.render(g, renderType);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }
}
