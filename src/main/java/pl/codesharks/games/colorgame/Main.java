package pl.codesharks.games.colorgame;

import pl.codesharks.games.colorgame.loggging.MyLogger;

import java.io.IOException;

/**
 * pl.codesharks.games.colorgame
 * Created by Tomasz on 2015-03-08.
 */
public class Main {
        public static void main(String[] args) {
            try {
                MyLogger.setup();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Problems with creating the log files");
            }
            try {
                GameEngine ge = new  GameEngine();
                ge.init();
            } catch (final Exception e) {
                e.printStackTrace();
                GameEngine.LOGGER.severe(e.getMessage());
            }
            MyLogger.close();
        }
}
