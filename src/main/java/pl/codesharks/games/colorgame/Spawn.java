package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.objects.BasicEnemy;
import pl.codesharks.games.colorgame.objects.FastEnemy;
import pl.codesharks.games.colorgame.objects.ID;
import pl.codesharks.games.colorgame.objects.SmartEnemy;

import java.util.Random;

/**
 * pl.codesharks.game.colorgame
 * Created by Tomasz on 2015-02-10.
 */
public class Spawn {
    private Handler handler;
    private HUD hud;
    private Random r = new Random();

    private int scoreKeep = 0;

    public Spawn(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick() {
        scoreKeep++;
        if (scoreKeep >= 50) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);
            if (hud.getLevel() == 2) {
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
            } else if (hud.getLevel() == 3) {
                handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
            }else if (hud.getLevel() == 4) {
                handler.addObject(new SmartEnemy(Game.WIDTH -  2*SmartEnemy.WIDTH, Game.HEIGHT - 2*SmartEnemy.HEIGHT, ID.SmartEnemy, handler));
            }
        }
    }

}
