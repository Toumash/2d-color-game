package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.Tag;
import pl.codesharks.games.colorgame.Renderable;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public abstract class GameObject implements Cloneable,Renderable {
    public static final int RENDER_TYPE_DEFAULT = 0;
    public static final int RENDER_TYPE_BOUNDS = 1;

    protected float x, y;
    protected Tag tag;
    protected float velX, velY;
    protected boolean enabled = true;

    public GameObject(float x, float y, Tag tag) {
        this.x = x;
        this.y = y;
        this.tag = tag;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
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

