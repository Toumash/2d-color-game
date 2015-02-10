package pl.codesharks.games.colorgame;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> objects = new LinkedList<GameObject>();

    public void tick() {
        GameObject tempObject;
        for (GameObject object : objects) {
            tempObject = object;
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        GameObject tempObject;
        for (GameObject object : objects) {
            tempObject = object;
            tempObject.render(g, GameObject.RENDER_DEFAULT);
        }

    }
    public void addObject(GameObject object){
        this.objects.add(object);
    }
    public void removeObject(GameObject object){
        this.objects.remove(object);
    }
}
