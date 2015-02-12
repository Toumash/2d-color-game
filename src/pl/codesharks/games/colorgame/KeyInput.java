package pl.codesharks.games.colorgame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class KeyInput extends KeyAdapter {

    public static final int KEY_UP = 0;
    public static final int KEY_DOWN = 1;
    public static final int KEY_LEFT = 2;
    public static final int KEY_RIGHT = 3;
    private Handler handler;
    private Game game;
    private boolean[] keyDown = new boolean[4];


    public KeyInput(Game game) {
        this.game = game;
        this.handler = game.handler;

        Arrays.fill(keyDown, false);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_F11) {
            game.switchFullscreen();
            return;
        }

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-Player.VELOCITY_MAX_Y);
                    keyDown[KEY_UP] = true;
                } else if (key == KeyEvent.VK_S) {
                    tempObject.setVelY(Player.VELOCITY_MAX_Y);
                    keyDown[KEY_DOWN] = true;
                } else if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-Player.VELOCITY_MAX_X);
                    keyDown[KEY_LEFT] = true;
                } else if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(Player.VELOCITY_MAX_X);
                    keyDown[KEY_RIGHT] = true;
                }
                break;
            }

        }
        if (key == KeyEvent.VK_ESCAPE) System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0, length = handler.objects.size(); i < length; i++) {
            GameObject tempObject = handler.objects.get(i);

            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_W) keyDown[KEY_UP] = false;
                else if (key == KeyEvent.VK_S) keyDown[KEY_DOWN] = false;//tempObject.setVelY(0);
                else if (key == KeyEvent.VK_A) keyDown[KEY_LEFT] = false;//tempObject.setVelX(0);
                else if (key == KeyEvent.VK_D) keyDown[KEY_RIGHT] = false;//tempObject.setVelX(0);

                //vertical movement
                if (!keyDown[KEY_UP] && !keyDown[KEY_DOWN]) tempObject.setVelY(0);
                else if (keyDown[KEY_UP] && keyDown[KEY_DOWN]) tempObject.setVelY(0);

                //horizontal movement
                if (!keyDown[KEY_LEFT] && !keyDown[KEY_RIGHT]) tempObject.setVelX(0);
                else if (keyDown[KEY_LEFT] && keyDown[KEY_RIGHT]) tempObject.setVelX(0);
                break;
            }
        }

    }

}
