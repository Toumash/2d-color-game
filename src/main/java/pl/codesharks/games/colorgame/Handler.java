package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.objects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> objects = new LinkedList<GameObject>();

    public void tick(float deltaTime) {
        GameObject tempObject;
        for (int i = 0; i < objects.size(); i++) {
            tempObject = objects.get(i);
            tempObject.update(deltaTime);
        }
    }

    public void render(Graphics g) {
        GameObject tempObject;
        for (int i = 0; i < objects.size(); i++) {
            tempObject = objects.get(i);
            tempObject.render(g, GameObject.RENDER_TYPE_DEFAULT);
        }

    }
    public void addObject(GameObject object){
        this.objects.add(object);
    }
    public void removeObject(GameObject object){
        this.objects.remove(object);
    }
}
