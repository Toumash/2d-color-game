package pl.codesharks.games.colorgame;

import java.util.Random;

/**
 * pl.codesharks.game.colorgame
 * Created by Tomasz on 2015-02-10.
 */
public class Spawn {
    private GameObjectManager gameObjectManager;
    private HUD hud;
    private Random r = new Random();

    private int scoreKeep = 0;

    public Spawn(GameObjectManager gameObjectManager, HUD hud) {
        this.gameObjectManager = gameObjectManager;
        this.hud = hud;
    }

    public void tick() {
        scoreKeep++;
        if (scoreKeep >= 50) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);
        /*    if (hud.getLevel() == 2) {
                gameObjectManager.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, gameObjectManager));
            } else if (hud.getLevel() == 3) {
                gameObjectManager.addObject(new FastEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, gameObjectManager));
            }else if (hud.getLevel() == 4) {
                gameObjectManager.addObject(new SmartEnemy(Game.WIDTH -  2*SmartEnemy.WIDTH, Game.HEIGHT - 2*SmartEnemy.HEIGHT, ID.SmartEnemy, gameObjectManager));
            }*/
        }
    }

}
