package pl.codesharks.games.colorgame;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends java.awt.Canvas {

    private static final long serialVersionUID = 8283124686075794579L;

    private JFrame mJFrame;
    private boolean fullscreen;
    private GameEngine gameEngine;

    public GameScreen(GameEngine gameEngine, String title, boolean requestFullscreen) {
        this.fullscreen = requestFullscreen;
        this.gameEngine = gameEngine;

        System.setProperty("sun.java2d.d3d", "true");
        System.setProperty("sun.java2d.noddraw", "false");
        System.setProperty("sun.java2d.accthreshold", "1");


        mJFrame = new JFrame(title);
        mJFrame.setIgnoreRepaint(true);
        mJFrame.setUndecorated(true);
        mJFrame.setPreferredSize(new Dimension(gameEngine.getWidth(), gameEngine.getHeight()));
        mJFrame.setMaximumSize(new Dimension(gameEngine.getWidth(), gameEngine.getHeight()));
        mJFrame.setMinimumSize(new Dimension(gameEngine.getWidth(), gameEngine.getHeight()));
        mJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mJFrame.setResizable(false);
        mJFrame.setLocationRelativeTo(null);


        mJFrame.add(gameEngine);

        setFullscreen(requestFullscreen);
        //mJFrame.pack(); DONT CALL IT. DESTROYS WHOLE WINDOW ( NO WINDOW )
        mJFrame.setVisible(true);
    }


    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * Method allows changing whether this gameScreen is displayed in fullscreen or
     * windowed mode.
     *
     * @param fullscreen true = change to fullscreen,
     *                   false = change to windowed
     */
    public void setFullscreen(boolean fullscreen) {
        //getInstance a reference to the device.
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode dispMode = device.getDisplayMode();
        //save the old display mode before changing it.
        DisplayMode dispModeOld = device.getDisplayMode();

        if (this.fullscreen != fullscreen) { //are we actually changing modes.
            //change modes.
            this.fullscreen = fullscreen;
            // toggle fullscreen mode
            this.gameEngine.stop();
            if (!fullscreen) {
                //change to windowed mode.
                //set the display mode back to the what it was when
                //the program was launched.
                device.setDisplayMode(dispModeOld);
                //hide the frame so we can change it.
                setVisible(false);
                //remove the frame from being displayable.
                mJFrame.dispose();
                //put the borders back on the frame.
                mJFrame.setUndecorated(false);
                //needed to unset this gameScreen as the fullscreen gameScreen.
                device.setFullScreenWindow(null);
                //recenter gameScreen
                mJFrame.setLocationRelativeTo(null);
                mJFrame.setResizable(true);

                //reset the display mode to what it was before
                //we changed it.
                setVisible(true);
            } else { //change to fullscreen.
                //hide everything
                setVisible(false);
                //remove the frame from being displayable.
                mJFrame.dispose();
                //remove borders around the frame
                mJFrame.setUndecorated(true);
                //make the gameScreen fullscreen.
                device.setFullScreenWindow(mJFrame);
                //attempt to change the screen resolution.
                device.setDisplayMode(dispMode);
                mJFrame.setResizable(false);
                mJFrame.setAlwaysOnTop(false);
                //show the frame
                setVisible(true);
            }
            this.gameEngine.startGame();
            //make sure that the screen is refreshed.
            repaint();
        }
    }
}
