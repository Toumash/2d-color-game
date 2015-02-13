package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.anim.Animation;
import pl.codesharks.games.colorgame.anim.SpriteSheet;
import pl.codesharks.games.colorgame.objects.AnimatedObject;
import pl.codesharks.games.colorgame.objects.BasicEnemy;
import pl.codesharks.games.colorgame.objects.Player;
import pl.codesharks.games.colorgame.resources.GameObjectManager;
import pl.codesharks.games.colorgame.resources.ObjectSpawn;
import pl.codesharks.games.colorgame.resources.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameEngine extends Canvas implements Runnable {

    public static final int WIDTH = 1024, HEIGHT = (int) (WIDTH / (1.6));//(WIDTH * 9) / 10;
    public static final boolean PREFERENCE_FULLSCREEN = false;

    public static final int FPS_LIMIT = 45;
    /**
     * Limits the FPS_LIMIT variable to the max serious value
     * Don't use greater, it will blow your computer
     */
    public static final int FPS_LIMIT_OVERALL = 400;
    public static final Color BACKGROUND_COLOR = Color.decode("#283339");
    public static final Color SCREEN_BACKGROUND_COLOR = BACKGROUND_COLOR;
    public static final int DEBUG_HUD_REFRESH_INTERVAL = 1000;
    static final double SLEEP_TIME_OPTIMAL = 1000 / (double) FPS_LIMIT;
    static final double SLEEP_TIME_MIN = 1000 / (double) FPS_LIMIT_OVERALL;
    private static final long serialVersionUID = 5739050383772454388L;
    private final Object lock = new Object();
    public GameObjectManager gameObjectManager;
    BufferStrategy bufferStrategy = null;
    Graphics g;
    @SuppressWarnings("FieldCanBeLocal")
    private GameScreen gameScreen;
    private HUD hud;
    private ObjectSpawn objectSpawn;
    private Thread thread;
    private GameData gameData;
    private volatile boolean running = false;
    private long FPS = 0;
    private double averageRenderMs = 0;
    private int frameCounter = 0;
    private boolean paused = false;

    public GameEngine() {
        setSize(WIDTH, HEIGHT);
        gameData = GameData.getInstance();

        gameObjectManager = GameObjectManager.getInstance();
        this.addKeyListener(new KeyInput(this));

        hud = new HUD();
        objectSpawn = new ObjectSpawn();

        gameData.attach(hud);
        gameData.attach(objectSpawn);

        gameScreen = new GameScreen(GameEngine.this, "Cool Game!", PREFERENCE_FULLSCREEN);

        cacheSprites();
        loadGameObjects();

        bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(2);
            bufferStrategy = this.getBufferStrategy();
        }

        startGame();
    }

    public static void showInfoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new GameEngine();
    }

    /**
     * Limites the var to the min,max
     *
     * @param var value that you want to limit
     * @param min minimal var value
     * @param max maximal var value
     * @return min < value < max
     */
    public static int clamp(int var, int min, int max) {
        int out = var;

        if (var >= max) out = max;
        else if (var <= min) out = min;

        return out;
    }

    /**
     * Limites the var to the min,max
     *
     * @param var value that you want to limit
     * @param min minimal var value
     * @param max maximal var value
     * @return min < value < max
     */
    public static float clamp(float var, float min, float max) {
        float out = var;

        if (var > max) out = max;
        else if (var < min) out = min;

        return out;
    }

    public boolean isPaused() {
        synchronized (lock) {
            return paused;
        }
    }

    public void setPaused(boolean pause) {
        synchronized (lock) {
            this.paused = pause;
        }
    }

    /**
     * Loads all images to memory
     */
    private void loadGameObjects() {
        try {
            SpriteManager sManager = SpriteManager.getInstance();
            {
                SpriteSheet hs = sManager.loadSpriteSheet("hearts.png", 112, 107);

                BufferedImage[] heartRes = new BufferedImage[14];
                for (int i = 0, arrayIndex = 0; i < 2; i++) {
                    for (int j = 0; j < 7; j++, arrayIndex++) {
                        heartRes[arrayIndex] = hs.getSprite(j, i);
                    }
                }
                Animation anim = new Animation(heartRes, 20);
                gameObjectManager.addObject(new AnimatedObject(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2, ID.Neutral, anim));
            }


            Random r = new Random();
            for (int i = 0; i < 2; i++) {
                gameObjectManager.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy));
            }

            gameObjectManager.addObject(new Player(200, 200, ID.Player));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("failed to load resources: " + e.getMessage());
            System.exit(1);
        }
    }

    public void cacheSprites() {
        SpriteManager sm = SpriteManager.getInstance();
        String[] sprites = {"razem.png", "hearts.png", "cool.png"};

        for (String sprite : sprites) {
            sm.loadSprite(sprite);
        }
    }

    /**
     * The main game loop. This loop is running during all game
     * play as is responsible for the following activities:
     * <p/>
     * - Working out the speed of the game loop to update moves
     * - Moving the game entities
     * - Drawing the screen contents (entities, text)
     * - Updating game events
     * - Checking Input
     * <p/>
     */
    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        //The last time at which we recorded the frame rate
        double msToUpdateFPSHUD = 0;
        g = bufferStrategy.getDrawGraphics();

        // supplied for computing and displaying average of 1s render time
        double sumOfLastIRenderTime = 0;

        while (running) {
            long renderStartTime = System.nanoTime();
            double deltaMs = (renderStartTime - lastLoopTime) / 1000000.0d;
            lastLoopTime = renderStartTime;


            msToUpdateFPSHUD += deltaMs;
            frameCounter++;

            boolean pause;
            synchronized (lock) {
                pause = isPaused();
            }
            if (!pause) {
                update((float) (deltaMs / 100.0f));
            }
            render(deltaMs);

            long renderEndTime = System.nanoTime();
            sumOfLastIRenderTime += (renderEndTime - renderStartTime) / 1000000.0d;
            if (msToUpdateFPSHUD >= DEBUG_HUD_REFRESH_INTERVAL) {
                FPS = frameCounter;
                averageRenderMs = sumOfLastIRenderTime / frameCounter; //(renderEndTime - renderStartTime) / 1000000.0d;
                sumOfLastIRenderTime = 0;
                msToUpdateFPSHUD = 0;
                frameCounter = 0;
            }
            try {
                Thread.sleep((long) Math.max(0, Math.max(SLEEP_TIME_OPTIMAL - ((renderEndTime - lastLoopTime) / 10000000.0d), SLEEP_TIME_MIN)));
            } catch (InterruptedException ignored) {
            }

        }
    }

    public void goFullScreen(boolean fullscreen) {
        this.gameScreen.setFullscreen(fullscreen);
    }

    public boolean isFullscreen() {
        return gameScreen.isFullscreen();
    }

    public void switchFullscreen() {
        goFullScreen(!isFullscreen());
    }

    private void render(double deltaMs) {
        Graphics2D g2d = (Graphics2D) g;
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //BACKGROUND
        g.setColor(SCREEN_BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        gameObjectManager.render(g);
        hud.render(g, FPS, averageRenderMs);

        // g.dispose();
        bufferStrategy.show();

    }

    private void update(float deltaTime) {
        gameData.update();
        gameObjectManager.update(deltaTime);
        hud.update();
        objectSpawn.update();
    }

    public void startGame() {
        thread = new Thread(this);
        thread.start();
        synchronized (lock) {
            running = true;
        }
    }

    public void stop() {
        synchronized (lock) {
            this.running = false;
        }
        try {
            thread.join(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread = null;
    }
}
