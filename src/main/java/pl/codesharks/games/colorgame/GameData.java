package pl.codesharks.games.colorgame;

import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON
 *
 * @author Toumash
 */
public final class GameData {
    public static final int HP_DEAD = -99;
    private static final GameData _instance = new GameData();
    boolean dead = false;
    private int score = 0;
    private int level = 1;
    private int hp = 100;
    private List<GameObserver> observers = new ArrayList<GameObserver>();
    private int scoreKeep = 0;
    private long lastScoreTime;

    private GameData() {
    }

    public static GameData getInstance() {
        return _instance;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        notifyAllObservers(CHANGED.HEALTH);
    }

    public void setDead(boolean state) {
        this.dead = state;
        notifyAllObservers(CHANGED.SCORE);
    }

    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(int changedGameData) {
        for (GameObserver observer : observers) {
            observer.gameDataChanged(changedGameData);
        }
    }

    public void start() {
        lastScoreTime = System.nanoTime();
    }

    public void update() {
        if (!dead) {
            setScore(++score);
        }
        //TODO: write this int FPS-independent way (delta Time)
        scoreKeep++;
        if (scoreKeep >= 500) {
            scoreKeep = 0;
            if (hp != 0) {
                level++;
                notifyAllObservers(CHANGED.LEVEL);
            }
        }
        if (hp == 0) {
            notifyAllObservers(CHANGED.DEATH);
            hp = HP_DEAD;
        }
        lastScoreTime = System.nanoTime();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        notifyAllObservers(CHANGED.SCORE);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        notifyAllObservers(CHANGED.LEVEL);
    }

    public void subtractHp(int diff) {
        hp -= diff;
        notifyAllObservers(CHANGED.HEALTH);
    }

    public static final class CHANGED {
        public static final int LEVEL = 0;
        public static final int HEALTH = 1;
        public static final int SCORE = 2;
        public static final int DEATH = 3;
    }
}
