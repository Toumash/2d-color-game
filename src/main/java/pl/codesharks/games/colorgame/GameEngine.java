package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.objects.BasicEnemy;
import pl.codesharks.games.colorgame.objects.GameObject;
import pl.codesharks.games.colorgame.objects.Player;
import pl.codesharks.games.colorgame.resources.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.logging.Logger;

public class GameEngine extends Canvas implements Runnable {
    public static final int WIDTH = 1024, HEIGHT = (int) (WIDTH / (1.6));//(WIDTH * 9) / 10;
    public static final boolean PREFERENCE_FULLSCREEN = false;
    public static final int FPS_LIMIT = 60;
    /**
     * Limits the FPS_LIMIT variable to the max serious value
     * Don't use greater, it will blow your computer
     */
    public static final int FPS_LIMIT_OVERALL = 400;
    public static final int DEBUG_HUD_REFRESH_INTERVAL = 1000;
    static final double SLEEP_TIME_OPTIMAL = 1000 / (double) FPS_LIMIT;
    static final double SLEEP_TIME_MIN = 1000 / (double) FPS_LIMIT_OVERALL;
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final long serialVersionUID = 5739050383772454388L;
    private final Object lock = new Object();
    public GameObjectManager gameObjectManager;
    private BufferStrategy bufferStrategy = null;
    private Graphics g;
    @SuppressWarnings("FieldCanBeLocal")
    private GameScreen gameScreen;
    private HUD hud;
    private ObjectSpawn objectSpawn;
    private Thread gameThread;
    private SoundEngine se;
    private GameData gameData;
    private volatile boolean running = false;
    private long FPS = 0;
    private double averageRenderMs = 0;
    private int frameCounter = 0;
    private boolean paused = false;

    public GameEngine() {
        setSize(WIDTH, HEIGHT);
    }

    public void init(){
        gameData = GameData.getInstance();

        gameObjectManager = GameObjectManager.getInstance();
        this.addKeyListener(new KeyInput(this));

        hud = new HUD();
        objectSpawn = new ObjectSpawn();

        gameScreen = new GameScreen(GameEngine.this, "Cool Game!", PREFERENCE_FULLSCREEN);

        cacheSprites();
        loadGameObjects();

        bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(2);
            bufferStrategy = this.getBufferStrategy();
        }

        startGame();

        se = new SoundEngine(getClass().getResourceAsStream("/music/background.wav"), true);
        se.start();
    }

    public static void showInfoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
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

    /**
     * Loads all images to memory
     */
    public static void loadGameObjects() {
        GameObjectManager gom = GameObjectManager.getInstance();
        try {
            Random r = new Random();
            for (int i = 0; i < 2; i++) {
                gom.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), Tag.BasicEnemy));
            }

            gom.addObject(new Player(200, 200, Tag.Player));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("failed to load resources: " + e.getMessage());
            System.exit(1);
        }
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

    public void cacheSprites() {
        SpriteManager sm = SpriteManager.getInstance();
        String[] sprites = {};

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
        gameObjectManager.start();

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
                msToUpdateFPSHUD -= DEBUG_HUD_REFRESH_INTERVAL;
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
        g.setColor(ColorLib.SCREEN_BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        gameObjectManager.render(g, GameObject.RENDER_TYPE_DEFAULT);
        hud.render(g, FPS, averageRenderMs);

        // g.dispose();
        bufferStrategy.show();

    }

    private void update(float deltaTime) {
        // GAME LOGIC FIRST!!
        gameData.update();

        objectSpawn.update();
        gameObjectManager.update(deltaTime);

        hud.update();
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
        synchronized (lock) {
            running = true;
        }
    }

    public void stop() {
        synchronized (lock) {
            this.running = false;
        }
        try {
            gameThread.join(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameThread = null;
        se.stop();
    }

}
