package pl.codesharks.games.colorgame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1024, HEIGHT = (int) (WIDTH / (1.6));//(WIDTH * 9) / 10;
    public static final boolean PREFERENCE_FULLSCREEN = false;

    public static final int FPS_LIMIT = 45;
    /**
     * Limits the FPS_LIMIT variable to the max serious value
     * Don't use greater, it will blow your computer
     */
    public static final int FPS_LIMIT_OVERALL = 400;
    static final double SLEEP_TIME_OPTIMAL = 1000 / (double) FPS_LIMIT;
    static final double SLEEP_TIME_MIN = 1000 / (double) FPS_LIMIT_OVERALL;

    private static final long serialVersionUID = 5739050383772454388L;

    private final Object lock = new Object();
    Handler handler;
    BufferStrategy bufferStrategy = null;
    Graphics buffer;
    private Image bgImg = null;
    private Image imageImg = null;
    @SuppressWarnings("FieldCanBeLocal")
    private GameScreen gameScreen;
    private HUD hud;
    private Spawn spawn;
    private Thread thread;
    private volatile boolean running = false;
    private long FPS = 0;
    private double mRenderTime = 0;
    private int frameCounter = 0;

    public Game() {
        //GRAPHICS
        bgImg = ResourceLoader.getImage("background.jpg");

        if (bgImg == null) {
            showInfoBox("Image is null", "NULL POINTER!!!");
        }
        imageImg = ResourceLoader.getImage("cool.png");
        if (imageImg == null) {
            showInfoBox("image is null", "NPE");
        }

        //HANDLERS
        handler = new Handler();
        this.addKeyListener(new KeyInput(this));

        //HUD
        hud = new HUD();
        //SPAWNER
        spawn = new Spawn(handler, hud);

        //WINDOW
        gameScreen = new GameScreen(WIDTH, HEIGHT, "Cool Game!", Game.this, PREFERENCE_FULLSCREEN);

        //OBJECTS
        Random r = new Random();

        for (int i = 0; i < 2; i++) {
            handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy, handler));
        }

        handler.addObject(new Player(200, 200, ID.Player, handler));
        handler.addObject(new FloatingImage(250, 250, ID.Player, imageImg, handler));


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
        new Game();
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

        if (var >= max) out = max;
        else if (var <= min) out = min;

        return out;
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
        buffer = bufferStrategy.getDrawGraphics();

        while (running) {
            long renderStartTime = System.nanoTime();
            double deltaMs = (renderStartTime - lastLoopTime) / 1000000.0d;
            lastLoopTime = renderStartTime;

            msToUpdateFPSHUD += deltaMs;
            frameCounter++;

            tick((float) (deltaMs / 100.0f));
            render(deltaMs);

            long renderEndTime = System.nanoTime();
            if (msToUpdateFPSHUD >= 1000) {
                FPS = frameCounter;
                mRenderTime = (renderEndTime - renderStartTime) / 1000000.0d;
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
        if (running) {
            buffer.clearRect(0, 0, WIDTH, HEIGHT);
            //BACKGROUND
            buffer.setColor(Color.BLACK);
            buffer.fillRect(0, 0, WIDTH, HEIGHT);

            buffer.drawImage(bgImg, WIDTH / 2 - (bgImg.getWidth(null) / 2), HEIGHT / 2 - (bgImg.getHeight(null) / 2), null);

            handler.render(buffer);
            hud.render(buffer, FPS, mRenderTime);

            // buffer.dispose();
            bufferStrategy.show();
        }
    }

    private void tick(float deltaTime) {
        handler.tick(deltaTime);
        hud.tick();
        spawn.tick();
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
            thread.stop();
        }
    }
}
