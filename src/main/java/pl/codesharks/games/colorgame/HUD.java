package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.resources.ColorLib;
import pl.codesharks.games.colorgame.resources.FontLib;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public class HUD implements GameObserver {
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
        /*score = gameData.getScore();
        level = gameData.getLevel();
        hp = gameData.getHealth();*/

    }

    public void notifyPlayerDeath() {
        dead = true;
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
            RenderUtils.drawStringAtCenter(g, "YOU LOST : C", 0, GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2);
            g.setFont(f);
        }

        g.drawString("Score: " + score, 10, GameEngine.HEIGHT - 64);
        g.drawString("Level: " + level, 10, GameEngine.HEIGHT - 32);
        g.drawString("FPS: " + fps, 10, 70);
        g.drawString(String.format("Render time: %3.2f ms", renderTime), 10, 60);
    }


    @Override
    public void gameDataChanged(int changedData) {
        switch (changedData){
            case GameData.CHANGED.DEATH:
                dead=true;
                break;
            case GameData.CHANGED.LEVEL:
                level = gameData.getLevel();
                break;
            case GameData.CHANGED.SCORE:
                score = gameData.getScore();
                break;
            case GameData.CHANGED.HEALTH:
                hp= gameData.getHp();
                hp = GameEngine.clamp(hp, 0, 100);
                break;
        }
    }
}
