package pl.codesharks.games.colorgame.objects;

import java.awt.*;


public class FloatingObject extends GameObject{
    public int maxDiffX = 0;
    public int maxDiffY = 150;
    float startHeight;
    float speed = 20;
    float angle = -90;
    float toDegrees = (float) (Math.PI / 180);
    private GameObject gameObject;


    public FloatingObject(GameObject gameObject, int maxDiffX, int maxDiffY) {
        super(gameObject.getX(), gameObject.getY(), gameObject.tag);
        startHeight = gameObject.getY();
        this.gameObject = gameObject;
        this.maxDiffX = maxDiffX;
        this.maxDiffY = maxDiffY;
    }

    public void update(float deltaTime) {
        angle += speed * deltaTime;
        if (angle > 270) angle -= 360;
        gameObject.setY((float) (startHeight + maxDiffY * (Math.sin(angle * toDegrees)) / 2.0));
        gameObject.update(deltaTime);
    }

    @Override
    public void render(Graphics g, int renderType) {
        gameObject.render(g, renderType);
    }

    @Override
    public void start() {

    }

    @Override
    public Rectangle getBounds() {
        return gameObject.getBounds();
    }
}
