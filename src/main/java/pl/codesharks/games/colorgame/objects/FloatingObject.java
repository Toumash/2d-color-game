package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.ID;

import java.awt.*;


public class FloatingObject extends GameObject {
    public static final Color color = Color.CYAN;
    public int MAX_SPEED_X = 35;
    public int MAX_SPEED_Y = 35;
    public int MAX_X = 0;
    public int MAX_Y = 150;
    float startHeight;
    float speed = 20;
    float angle = -90;
    float toDegrees = (float) (Math.PI / 180);
    private GameObject gameObject;


    public FloatingObject(int x, int y, ID id, GameObject gameObject) {
        super(x, y, id);
        startHeight = y;
        this.gameObject = gameObject;
    }

    public void update(float deltaTime) {

        angle += speed * deltaTime;
        if (angle > 270) angle -= 360;
        // System.out.println(MAX_Y * Math.sin(angle * toDegrees));
      //  y = (float) (startHeight + MAX_Y * (Math.sin(angle * toDegrees)) / 2.0);
        gameObject.setY((float) (startHeight + MAX_Y * (Math.sin(angle * toDegrees)) / 2.0));
        gameObject.update(deltaTime);
    }

    @Override
    public void render(Graphics g, int renderType) {
        gameObject.render(g, renderType);
    }

    @Override
    public Rectangle getBounds() {
        return gameObject.getBounds();
    }
}
