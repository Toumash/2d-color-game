package pl.codesharks.games.colorgame;

import java.awt.*;

public interface Drawable {

    void render(Graphics g, int renderType);

    void start();

    void update(float delta);

    Rectangle getBounds();
}
