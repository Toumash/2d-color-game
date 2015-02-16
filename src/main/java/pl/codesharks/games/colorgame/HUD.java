package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.resources.ColorLib;
import pl.codesharks.games.colorgame.resources.FontLib;
import pl.codesharks.games.colorgame.resources.SoundEngine;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public class HUD {
    private static int greenValue = 255;
    GameData gameData = GameData.getInstance();
    boolean deathNotified = false;
    boolean dead = false;
    private int score = 0;
    private int level = 1;
    private int hp = 100;

    public HUD() {
    }

    public void update() {

        if (!dead) {
            score = gameData.getScore();
            level = gameData.getLevel();

            hp = gameData.getHp();
            GameEngine.clamp(hp, 0, 100);

            if (gameData.getHp() <= 0) {
                dead = true;
                SoundEngine se = new SoundEngine(GameEngine.class.getResourceAsStream("/music/game_over.wav"), false);
                se.start();
            }
        }

    }

    public void render(Graphics g, long fps, double renderTime) {
        g.setColor(ColorLib.HUD_GRAY);

        if (!dead) {
            g.setColor(ColorLib.HEALTH_BAR_BACKGROUND);
            g.fillRect(15, 15, 200, 32);

            g.setColor(ColorLib.HEALTH_BAR_HEALTH);
            g.fillRect(15, 15, hp * 2, 32);

            g.setColor(Color.WHITE);
            g.drawRect(15, 15, 200, 32);
        } else {
            Font f = g.getFont();

            g.setFont(FontLib.END_GAME);
            RenderUtils.drawStringAtCenter(g, "YOU LOST", 0, GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2);
            RenderUtils.drawStringAtCenter(g, "Score: " + score, 0, GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2 + 50);

            g.setFont(f);
        }

        g.drawString("Score: " + score, 10, GameEngine.HEIGHT - 64);
        g.drawString("Level: " + level, 10, GameEngine.HEIGHT - 32);
        g.drawString("FPS: " + fps, 10, 70);
        g.drawString(String.format("Render time: %3.2f ms", renderTime), 10, 60);
    }
}
