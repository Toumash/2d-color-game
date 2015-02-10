package pl.codesharks.games.colorgame;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject {
    public static final int VELOCITY_MAX_X = 10;
    public static final int VELOCITY_MAX_Y = 10;

    @SuppressWarnings("UnusedDeclaration")
    Random r = new Random();

    Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        x = Game.clamp((int) x, 0, Game.WIDTH - 32);
        y = Game.clamp((int) y, 0, Game.HEIGHT - 64);

        handler.addObject(new Trail((int) x, (int) y, ID.Trail, Color.white, 32, 32, 0.1f, handler));

        collison();
    }

    @Override
    public void render(Graphics g, int drawType) {
        g.setColor(Color.WHITE);

        if (drawType == GameObject.RENDER_DEFAULT) {
            g.fillRect((int) x, (int) y, 32, 32);
        } else if (drawType == GameObject.RENDER_BOUNDS) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(getBounds());
        }
    }

    private void collison() {
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
        return new Rectangle((int) x, (int) y, 32, 32);
    }

}
