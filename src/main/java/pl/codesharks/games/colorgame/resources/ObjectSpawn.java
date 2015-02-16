package pl.codesharks.games.colorgame.resources;

import pl.codesharks.games.colorgame.GameData;
import pl.codesharks.games.colorgame.GameEngine;
import pl.codesharks.games.colorgame.Tag;
import pl.codesharks.games.colorgame.objects.BasicEnemy;
import pl.codesharks.games.colorgame.objects.FastEnemy;
import pl.codesharks.games.colorgame.objects.SmartEnemy;

import java.util.Random;


public class ObjectSpawn {
    protected int level = 0;
    private GameObjectManager gameObjectManager = GameObjectManager.getInstance();
    private GameData gameData = GameData.getInstance();
    private Random r = new Random();

    public ObjectSpawn() {
    }

    public void update() {
        int level = gameData.getLevel();
        if (this.level < level) {
            if (level == 1) {
                gameObjectManager.addObject(new BasicEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), Tag.BasicEnemy));
            } else if (level == 2) {
                gameObjectManager.addObject(new BasicEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), Tag.BasicEnemy));
            } else if (level == 3) {
                gameObjectManager.addObject(new FastEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), Tag.FastEnemy));
            } else if (level == 4) {
                gameObjectManager.addObject(new SmartEnemy(GameEngine.WIDTH - 2 * SmartEnemy.WIDTH, GameEngine.HEIGHT - 2 * SmartEnemy.HEIGHT, Tag.SmartEnemy, gameObjectManager));
            } else if (level == 5) {
                gameObjectManager.addObject(new SmartEnemy(GameEngine.WIDTH - 2 * SmartEnemy.WIDTH, GameEngine.HEIGHT - 2 * SmartEnemy.HEIGHT, Tag.SmartEnemy, gameObjectManager));
            } else if (level > 5) {
                gameObjectManager.addObject(new FastEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), Tag.FastEnemy));
            }
            this.level = level;
        }
    }

}
