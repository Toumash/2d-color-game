package pl.codesharks.games.colorgame.anim;

import java.awt.image.BufferedImage;

public class Frame {

    private BufferedImage frame;
    private float duration;

    public Frame(BufferedImage frame, float duration) {
        this.frame = frame;
        this.duration = duration;
    }

    public BufferedImage getFrame() {
        return frame;
    }

    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

}
