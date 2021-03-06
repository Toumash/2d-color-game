package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.ID;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public abstract class GameObject implements Cloneable {
    public static final int RENDER_TYPE_DEFAULT = 0;
    public static final int RENDER_TYPE_BOUNDS = 1;

    protected float x, y;
    protected ID id;
    protected float velX, velY;
    protected boolean enabled = true;

    public GameObject(float x, float y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void update(float deltaTime);

    public abstract void render(Graphics g, int renderType);

    public abstract void start();

    public abstract Rectangle getBounds();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enable) {
        this.enabled = enable;
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

    @Override
    public GameObject clone() throws CloneNotSupportedException {
        return (GameObject) super.clone();
    }
}

