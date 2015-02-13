package pl.codesharks.games.colorgame;

import java.awt.*;

/**
 * pl.codesharks.games.colorgame
 * Created by Tomasz on 2015-02-12.
 */
public class RenderUtils {
    public static void drawStringAtCenter(Graphics g, String s, int width, int XPos, int YPos) {
        Graphics2D g2d = (Graphics2D) g;
        int stringLen = (int)
                g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width / 2 - stringLen / 2;
        g2d.drawString(s, start + XPos, YPos);
    }

}
