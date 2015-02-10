package pl.codesharks.games.colorgame;

import javax.swing.*;
import java.awt.*;

public class Window extends java.awt.Canvas {

    private static final long serialVersionUID = 8283124686075794579L;


    public Window(int width, int height, String title, Game game) {

        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));

        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();

    }


}
