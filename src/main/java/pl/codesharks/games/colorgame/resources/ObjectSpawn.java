package pl.codesharks.games.colorgame.resources;

import pl.codesharks.games.colorgame.GameData;
import pl.codesharks.games.colorgame.GameEngine;
import pl.codesharks.games.colorgame.GameObserver;
import pl.codesharks.games.colorgame.ID;
import pl.codesharks.games.colorgame.objects.*;

import java.util.Random;


public class ObjectSpawn implements GameObserver {
    private GameObjectManager gameObjectManager = GameObjectManager.getInstance();
    private Random r = new Random();

    public ObjectSpawn() {
    }

    public void update() {

    }

    @Override
    public void gameDataChanged(int changedData) {
        switch (changedData) {
            case GameData.CHANGED.LEVEL:
                int level = GameData.getInstance().getLevel();
                if (level == 1) {
                    gameObjectManager.addObject(new BasicEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), ID.BasicEnemy));
                } else if (level == 2) {
                    gameObjectManager.addObject(new BasicEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), ID.BasicEnemy));
                } else if (level == 3) {
                    gameObjectManager.addObject(new FastEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), ID.FastEnemy));
                } else if (level == 4) {
                    gameObjectManager.addObject(new SmartEnemy(GameEngine.WIDTH - 2 * SmartEnemy.WIDTH, GameEngine.HEIGHT - 2 * SmartEnemy.HEIGHT, ID.SmartEnemy, gameObjectManager));
                } else if (level == 5) {
                    gameObjectManager.addObject(new SmartEnemy(GameEngine.WIDTH - 2 * SmartEnemy.WIDTH, GameEngine.HEIGHT - 2 * SmartEnemy.HEIGHT, ID.SmartEnemy, gameObjectManager));
                } else if (level > 5) {
                    gameObjectManager.addObject(new FastEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), ID.FastEnemy));
                }
                break;
            case GameData.CHANGED.DEATH:
                break;
        }
    }
}
