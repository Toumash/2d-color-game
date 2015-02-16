package pl.codesharks.games.colorgame;

import java.awt.*;

public interface Renderable {

    public void render(Graphics g, int renderType);

    public void start();

    public void update(float delta);

    public Rectangle getBounds();
}
