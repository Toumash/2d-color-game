package pl.codesharks.games.colorgame;

/**
 * SINGLETON
 *
 * @author Toumash
 */
public final class GameData {
    public static final int HP_DEAD = -99;
    private static final GameData _instance = new GameData();
    boolean dead = false;
    /**
     * need to be 1
     */
    private int score = 1;
    private int level = 1;
    private int hp = 100;
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
    }

    public void setDead(boolean state) {
        this.dead = state;
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
            if (!dead) {
                level++;
            }
        }
        if (hp == 0) {
            hp = HP_DEAD;
        }
        lastScoreTime = System.nanoTime();
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

    public void subtractHp(int diff) {
        hp -= diff;
    }

    public static final class CHANGED {
        public static final int LEVEL = 0;
        public static final int HEALTH = 1;
        public static final int SCORE = 2;
        public static final int DEATH = 3;
    }
}
