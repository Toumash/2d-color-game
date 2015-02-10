package pl.codesharks.games.colorgame;

import java.awt.*;


public class SmartEnemy extends GameObject {

    public static final Color color = Color.GREEN;
    private Handler handler;
    private GameObject player;

    public SmartEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        for (int i = 0; i < handler.objects.size(); i++) {
            if (handler.objects.get(i).getId() == ID.Player) player = handler.objects.get(i);
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        float diffX = (float) (x - player.getX() - (player.getBounds().getWidth() / 2));
        float diffY = (float) (y - player.getY() - (player.getBounds().getHeight() / 2));

        float distance = (float) Math.sqrt(
                Math.pow(x - player.getX(), 2)
                        + Math.pow(y - player.getY(), 2)
        );

        velX = (float) ((-1.0 / distance) * diffX);
        velY = (float) ((-1.0 / distance) * diffY);


        handler.addObject(new Trail((int) x, (int) y, ID.Trail, Color.RED, 16, 16, 0.1f, handler));
    }

    @Override
    public void render(Graphics g, int drawType) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);

        switch (drawType) {
            case GameObject.RENDER_DEFAULT:
                g.fillRect((int) x, (int) y, 16, 16);
                break;
            case GameObject.RENDER_BOUNDS:
                g2d.draw(getBounds());
                break;

        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }
}
