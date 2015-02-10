package pl.codesharks.games.colorgame;

import java.awt.*;

@SuppressWarnings("UnusedDeclaration")
public class HUD {

    public static int HEALTH = 100;
    private static int greenValue = 255;

    private int score = 0;
    private int level = 1;

    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 100);
        greenValue = HEALTH * 2;
        greenValue = Game.clamp(greenValue, 90, 255);

        score++;
    }

    public void render(Graphics g,int fps) {
        g.setColor(Color.GRAY);
        g.fillRect(15, 15, 200, 32);

        g.setColor(new Color(75, greenValue, 0));
        g.fillRect(15, 15, HEALTH * 2, 32);

        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 32);


        g.drawString("Score: " + score, 10, Game.HEIGHT - 64);
        g.drawString("Level: " + level, 10, Game.HEIGHT - 32);
        g.drawString("FPS: " + fps, 10, 20);
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
