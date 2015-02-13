package pl.codesharks.games.colorgame.objects;

import pl.codesharks.games.colorgame.GameObjectManager;

import java.awt.*;


public class FloatingImage extends GameObject {


    public static final Color color = Color.CYAN;
    public static final int MAX_SPEED_X = 35;
    public static final int MAX_SPEED_Y = 35;
    public static final int OBJ_WIDTH = 32;
    public static final int OBJ_HEIGHT = 32;
    public static final int MAX_X = 0;
    public static final int MAX_Y = 150;
    float startHeight; // height of the object when the script starts
    float speed = 20;
    float angle = -90;
    float toDegrees = (float) (Math.PI / 180);
    private GameObjectManager gameObjectManager;
    private Image mImage;


    public FloatingImage(int x, int y, ID id, Image image, GameObjectManager gameObjectManager) {
        super(x, y, id);
        this.gameObjectManager = gameObjectManager;
        startHeight = y;
        mImage = image;
    }

    public void update(float deltaTime) {

        angle += speed * deltaTime;
        if (angle > 270) angle -= 360;
        // System.out.println(MAX_Y * Math.sin(angle * toDegrees));
        y = (float) (startHeight + MAX_Y * (Math.sin(angle * toDegrees)) / 2.0);
    }

    @Override
    public void render(Graphics g, int renderType) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);

        switch (renderType) {
            case GameObject.RENDER_TYPE_DEFAULT:
                // g.fillRect((int) x, (int) y, 16, 16);
                g.drawImage(mImage, (int) x, (int) y, null);
                break;
            case GameObject.RENDER_TYPE_BOUNDS:
                g2d.draw(getBounds());
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }
}
