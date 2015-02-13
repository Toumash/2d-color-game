package pl.codesharks.games.colorgame.anim;

import com.sun.javaws.exceptions.InvalidArgumentException;
import pl.codesharks.games.colorgame.anim.Frame;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Animation {

    private float frameDelayMs;
    private int currentFrame;
    /**
     * Animation direction
     */
    private boolean isForward;
    private int totalFrames;

    private boolean stopped;

    private List<Frame> frames = new ArrayList<Frame>();
    /**
     * Last time when new frame was shown, used to determine how much frames to move forward/backward
     */
    private long lastFrameTime;

    public Animation(BufferedImage[] frames, int fps) throws InvalidArgumentException {
        this.stopped = true;
        this.frameDelayMs = (float) 1000.0f / fps;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelayMs);
        }

        this.currentFrame = 0;
        this.isForward = true;
        this.totalFrames = this.frames.size();

    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        lastFrameTime = System.nanoTime();
    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
        lastFrameTime = System.nanoTime();
    }

    public void reset() {
        this.stopped = true;
        this.currentFrame = 0;
    }

    private void addFrame(BufferedImage frame, float duration) throws InvalidArgumentException {
        if (duration <= 0f) {
            throw new InvalidArgumentException(new String[]{"Invalid duration: " + duration});
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    public void update() {
        if (!stopped) {
            float timeDiff = (System.nanoTime() - lastFrameTime) / 1000000.f;
            if (timeDiff > frameDelayMs) {
                lastFrameTime = System.nanoTime();
                if (isForward) {
                    currentFrame += timeDiff / frameDelayMs;
                    while (currentFrame > totalFrames - 1) {
                        currentFrame -= totalFrames - 1;
                    }
                } else {
                    currentFrame -= timeDiff / frameDelayMs;
                    while (currentFrame < 0) {
                        currentFrame += totalFrames + 1;
                    }
                }
            }/*
            if (frameCount > frameDelayMs) {
                frameCount = 0;
                currentFrame += animationDirection;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                } else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }

            }*/
        }

    }

}
