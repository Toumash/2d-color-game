package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.objects.GameObject;
import pl.codesharks.games.colorgame.objects.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class KeyInput extends KeyAdapter {

    public static final int KEY_UP = 0;
    public static final int KEY_DOWN = 1;
    public static final int KEY_LEFT = 2;
    public static final int KEY_RIGHT = 3;
    private GameObjectManager gameObjectManager;
    private GameEngine gameEngine;
    private boolean[] keyDown = new boolean[4];


    public KeyInput(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gameObjectManager = gameEngine.gameObjectManager;

        Arrays.fill(keyDown, false);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_F11) {
            gameEngine.switchFullscreen();
            return;
        }else if(key == KeyEvent.VK_F1){
            gameEngine.setPaused(!gameEngine.isPaused());
        }

        for (int i = 0, length = gameObjectManager.getSize(); i < length; i++) {
            GameObject object = gameObjectManager.objects.get(i);

            if (object instanceof Player) {
                Player player = (Player) object;
                if (key == KeyEvent.VK_W) {
                    object.setVelY(-Player.MAX_SPEED_Y);
                    keyDown[KEY_UP] = true;
                } else if (key == KeyEvent.VK_S) {
                    object.setVelY(Player.MAX_SPEED_Y);
                    keyDown[KEY_DOWN] = true;
                } else if (key == KeyEvent.VK_A) {
                    object.setVelX(-Player.MAX_SPEED_X);
                    keyDown[KEY_LEFT] = true;
                } else if (key == KeyEvent.VK_D) {
                    object.setVelX(Player.MAX_SPEED_X);
                    keyDown[KEY_RIGHT] = true;
                }
                break;
            }

        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.out.println("Application quit by Escape Button");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0, length = gameObjectManager.getSize(); i < length; i++) {
            GameObject object = gameObjectManager.objects.get(i);

            if (object instanceof Player) {
                if (key == KeyEvent.VK_W) keyDown[KEY_UP] = false;
                else if (key == KeyEvent.VK_S) keyDown[KEY_DOWN] = false;//object.setVelY(0);
                else if (key == KeyEvent.VK_A) keyDown[KEY_LEFT] = false;//object.setVelX(0);
                else if (key == KeyEvent.VK_D) keyDown[KEY_RIGHT] = false;//object.setVelX(0);

                //vertical movement
                if (!keyDown[KEY_UP] && !keyDown[KEY_DOWN]) object.setVelY(0);
                else if (keyDown[KEY_UP] && keyDown[KEY_DOWN]) object.setVelY(0);

                //horizontal movement
                if (!keyDown[KEY_LEFT] && !keyDown[KEY_RIGHT]) object.setVelX(0);
                else if (keyDown[KEY_LEFT] && keyDown[KEY_RIGHT]) object.setVelX(0);
                break;
            }
        }

    }

}
