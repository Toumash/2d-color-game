package pl.codesharks.games.colorgame;

import java.awt.*;

public final class ResourceLoader {
    static ResourceLoader rl = new ResourceLoader();

    public static Image getImage(String filename){
        return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource("/images/" + filename));
    }
}
