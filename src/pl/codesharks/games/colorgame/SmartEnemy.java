package pl.codesharks.games.colorgame;

import java.awt.*;


public class SmartEnemy extends GameObject {

    public static final Color color = Color.GREEN;
    public static final int HEIGHT = 16;
    public static final int WIDTH = 16;
    public static final int MAX_SPEED_X = 30;
    public static final int MAX_SPEED_Y = 30;
    public final int TRAIL_STEP_X = 5;
    public final int TRAIL_STEP_Y = 5;
    float lastTrailX = x;
    float lastTrailY = y;
    private Handler handler;
    private GameObject player;

    public SmartEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        for (int i = 0; i < handler.objects.size(); i++) {
            if (handler.objects.get(i).getId() == ID.Player) {
                player = handler.objects.get(i);
                break;
            }
        }
    }

    @Override
    public void update(float deltaTime) {

        float diffX = (float) (x - player.getX() - (Player.WIDTH / 2));
        float diffY = (float) (y - player.getY() - (Player.HEIGHT / 2));

        float distance = (float) Math.sqrt(
                Math.pow(x - player.getX(), 2)
                        + Math.pow(y - player.getY(), 2)
        );


        velX = (float) ((-1.0 / distance) * diffX) * MAX_SPEED_X * deltaTime;

        velY = (float) ((-1.0 / distance) * diffY) * MAX_SPEED_Y * deltaTime;

        x += velX;
        y += velY;

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
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }
}
