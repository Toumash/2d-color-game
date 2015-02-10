package pl.codesharks.games.colorgame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    private static final long serialVersionUID = 5739050383772454388L;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private Spawn spawner;

    private int FPS = 0;


    public Game() {
        //HANDLERS
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        //WINDOW
        new Window(WIDTH, HEIGHT, "Cool Game!", this);

        //HUD
        hud = new HUD();
        //SPAWNER
        spawner = new Spawn(handler, hud);

        //OBJECTS
        Random r = new Random();

        for (int i = 0; i < 2; i++) {
            handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy, handler));
        }


        handler.addObject(new Player(200, 200, ID.Player, handler));

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

    @Override
    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;

            }
            if (running) {
                render();
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                FPS = frames;
                frames = 0;

            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);


        handler.render(g);
        hud.render(g, FPS);


        g.dispose();
        bs.show();
    }

    private void tick() {
        handler.tick();
        hud.tick();
        spawner.tick();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {

            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
