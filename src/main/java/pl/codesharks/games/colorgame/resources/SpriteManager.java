package pl.codesharks.games.colorgame.resources;

import pl.codesharks.games.colorgame.anim.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * SINGLETON
 *
 * @author Toumash
 */
public final class SpriteManager {
    public static final String SPRITES_DIR = "/images/";
    private static SpriteManager _instance = new SpriteManager();
    private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    public static SpriteManager getInstance() {
        return _instance;
    }

    protected static Image getImage(String filename) {
        return Toolkit.getDefaultToolkit().getImage(_instance.getClass().getResource("/images/" + filename));
    }

    protected static Image getImage(URL url) {
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    protected static BufferedImage getBufferedImage(URL url) throws IOException {
        return ImageIO.read(url);
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param image The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bImage;
    }

    /**
     * Creates a optimized version of the BufferedImage for local
     * graphics accelerator based on systems default best configuration
     *
     * @param image image to be optimized
     * @return faster to render image
     */
    protected static BufferedImage toCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if (image.getColorModel().equals(gfx_config.getColorModel())) return image;
        BufferedImage optimalImage = gfx_config.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
        Graphics2D g2d = (Graphics2D) optimalImage.getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return optimalImage;
    }

    public static URL createUrl(String filename) {
        return _instance.getClass().getResource(SPRITES_DIR + filename);
    }

    /**
     * Retrieve a sprite from the store
     *
     * @param ref The reference to the image to use for the sprite
     * @return A sprite instance containing an accelerate image of the request reference
     */
    public Sprite loadSprite(String ref) {
        // if it is in the cache
        if (sprites.get(ref) != null) {
            return sprites.get(ref);
        }

        BufferedImage sourceImage;

        try {
            URL url = createUrl(ref);

            if (url == null) {
                fail("Can't find ref: " + ref);
            }

            sourceImage = getBufferedImage(url);
        } catch (NullPointerException e) {
            fail("Failed to load: " + ref + " " + e.getMessage());
            return null;
        } catch (IOException e) {
            fail("Failed to load: " + ref + " " + e.getMessage());
            return null;
        }

        // optimize image for accelerated graphics card
        sourceImage = toCompatibleImage(sourceImage);

        Sprite sprite = new Sprite(sourceImage);
        sprites.put(ref, sprite);

        return sprite;
    }

    public SpriteSheet loadSpriteSheet(String ref, int tileSizeX, int tileSizeY) {
        return loadSprite(ref).toSpriteSheet(tileSizeX, tileSizeY);
    }

    /**
     * Utility method to handle resource loading failure
     *
     * @param message The message to display on failure
     */
    private void fail(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
