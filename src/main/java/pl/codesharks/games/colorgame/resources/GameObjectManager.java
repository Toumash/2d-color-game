package pl.codesharks.games.colorgame.resources;

import pl.codesharks.games.colorgame.Drawable;
import pl.codesharks.games.colorgame.objects.GameObject;
import pl.codesharks.games.colorgame.objects.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * SINGLETON
 *
 * @author Toumash
 */
public final class GameObjectManager implements Drawable {

    private static GameObjectManager _instance = new GameObjectManager();
    public ArrayList<GameObject> objects = new ArrayList<>(20);
    private int size = 0;

    private GameObjectManager() {
    }

    public static GameObjectManager getInstance() {
        return _instance;
    }

    public void update(float deltaTime) {
        for (int i = 0, length = size; i < length; i++) {
            objects.get(i).update(deltaTime);
        }
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void render(Graphics g, int renderType) {
        for (int i = 0, length = size; i < length; i++) {
            GameObject tmp = objects.get(i);
            if (tmp.isEnabled()) {
                tmp.render(g, renderType);
            }
        }
    }

    public void start() {
        for (int i = 0, length = size; i < length; i++) {
            objects.get(i).start();
        }
    }

    public int getSize() {
        return objects.size();
    }

    public void addObject(GameObject object) {
        size++;
        this.objects.add(object);
    }

    public void removeObject(GameObject object) {
        if (objects.remove(object)) {
            size--;
        }
    }

    public Player getPlayerObject() {
        Player player = null;
        for (int i = 0, length = getSize(); i < length; i++) {
            if (objects.get(i) instanceof Player) {
                player = (Player) objects.get(i);
                break;
            }
        }
        return player;
    }

    public void removeAll() {
        objects.clear();
    }
}
