package pl.codesharks.games.colorgame.objects;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import pl.codesharks.games.colorgame.ID;
import pl.codesharks.games.colorgame.resources.GameObjectManager;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Queue;


public class SmartEnemy extends GameObject {

    public static final Color color = Color.GREEN;
    public static final int HEIGHT = 16;
    public static final int WIDTH = 16;
    public static final int MAX_SPEED_X = 25;
    public static final int MAX_SPEED_Y = 25;
    public static final int MAX_TRAIL_AMOUNT = 10;
    public final int TRAIL_STEP_X = 5;
    public final int TRAIL_STEP_Y = 5;
    public volatile Queue<Trail> trails = new ArrayDeque<Trail>();
    float lastTrailX = x;
    float lastTrailY = y;
    private GameObject player;

    public SmartEnemy(int x, int y, ID id, GameObjectManager gameObjectManager) {
        super(x, y, id);

        player = GameObjectManager.getInstance().getPlayerObject();
    }

    @Override
    public void update(float deltaTime) {
        Vector2D directionVector = new Vector2D(player.getX() - x, player.getY() - y);
        directionVector = directionVector.normalize();
        x += directionVector.getX() * deltaTime * MAX_SPEED_X;
        y += directionVector.getY() * deltaTime * MAX_SPEED_Y;

        if (Math.abs(lastTrailX - x) >= TRAIL_STEP_X || Math.abs(lastTrailY - y) >= TRAIL_STEP_Y) {
            if (trails.size() < MAX_TRAIL_AMOUNT) {
                trails.add(new Trail((int) x, (int) y, ID.Trail, color, WIDTH, HEIGHT, 0.1f));
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
        for (Trail t : trails) {
            t.render(g, renderType);
        }

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        switch (renderType) {
            case RENDER_TYPE_DEFAULT:
                g.fillRect((int) x, (int) y, WIDTH, HEIGHT);
                break;
            case RENDER_TYPE_BOUNDS:
                g2d.draw(getBounds());
                break;
        }

    }

    @Override
    public void start() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }
}
