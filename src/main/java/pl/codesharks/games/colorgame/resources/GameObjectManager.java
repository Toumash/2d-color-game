package pl.codesharks.games.colorgame.resources;

import pl.codesharks.games.colorgame.objects.GameObject;
import pl.codesharks.games.colorgame.objects.Player;

import java.awt.*;
import java.util.ArrayList;

/**
 * SINGLETON
 *
 * @author Toumash
 */
public final class GameObjectManager {

    private static GameObjectManager _instance = new GameObjectManager();
    public ArrayList<GameObject> objects = new ArrayList<GameObject>();
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

    public void render(Graphics g) {
        for (int i = 0, length = size; i < length; i++) {
            GameObject tmp = objects.get(i);
            if (tmp.isEnabled()) {
                tmp.render(g, GameObject.RENDER_TYPE_DEFAULT);
            }
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
}
