package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.resources.ColorLib;
import pl.codesharks.games.colorgame.resources.FontLib;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public class HUD {
    public static int HEALTH = 100;

    private static int greenValue = 255;
    private int score = 0;
    private int level = 1;

    public void update() {
        HEALTH = GameEngine.clamp(HEALTH, 0, 100);
        score++;
    }

    public void render(Graphics g, long fps, double renderTime) {
        g.setColor(ColorLib.HUD_GRAY);

        if (HEALTH > 0) {
            g.setColor(ColorLib.HEALTH_BAR_BACKGROUND);
            g.fillRect(15, 15, 200, 32);

            g.setColor(ColorLib.HEALTH_BAR_HEALTH);
            g.fillRect(15, 15, HEALTH * 2, 32);

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
/*        System.out.println("FPS: " + fps);
        System.out.println("Render Time: " + renderTime);*/
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
