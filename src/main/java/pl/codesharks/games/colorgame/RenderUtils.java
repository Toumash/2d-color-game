package pl.codesharks.games.colorgame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

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

    public static void drawImageAtCenter(Graphics g, Image image, int x, int y) {
        g.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
    }

    public static BufferedImage scale(BufferedImage before, float scaleX, float scaleY) {
        int w = (int) (before.getWidth() * scaleX);
        int h = (int) (before.getHeight() * scaleY);
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scaleX, scaleY);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, after);
        return after;
    }
}
