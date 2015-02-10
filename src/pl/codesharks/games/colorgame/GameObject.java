package pl.codesharks.games.colorgame;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public abstract class GameObject {
    public static final int  RENDER_DEFAULT =0;
    public static final int  RENDER_BOUNDS =1;

    protected float x, y;
    protected ID id;
    protected float velX, velY;

    public GameObject(float x, float y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public abstract void tick();

    public abstract void render(Graphics g, int drawType);

    public abstract Rectangle getBounds();

}

