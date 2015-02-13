package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public final class GameObjectManager {

    public ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private int size = 0;

    public void tick(float deltaTime) {
        for (int i = 0, length = size; i < length; i++) {
            objects.get(i).update(deltaTime);
        }
    }

    public void render(Graphics g) {
        for (int i = 0, length = size; i < length; i++) {
            objects.get(i).render(g, GameObject.RENDER_TYPE_DEFAULT);
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
}
