package pl.codesharks.games.colorgame.resources;

import pl.codesharks.games.colorgame.*;
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
                } else if (level == 3) {
                    gameObjectManager.addObject(new FastEnemy(r.nextInt(GameEngine.WIDTH), r.nextInt(GameEngine.HEIGHT - 50), ID.FastEnemy));
                } else if (level == 4) {
                    gameObjectManager.addObject(new SmartEnemy(GameEngine.WIDTH - 2 * SmartEnemy.WIDTH, GameEngine.HEIGHT - 2 * SmartEnemy.HEIGHT, ID.SmartEnemy, gameObjectManager));
                }
                break;
            case GameData.CHANGED.DEATH:
                SpriteManager sm = SpriteManager.getInstance();
                FloatingObject fo = new FloatingObject(new SpriteObject(250, 250, ID.Neutral, sm.loadSprite("razem.png").image), 0, 50);
                GameObjectManager.getInstance().addObject(fo);
                break;
        }
    }
}
