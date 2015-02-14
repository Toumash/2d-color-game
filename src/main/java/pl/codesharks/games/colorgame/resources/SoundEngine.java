package pl.codesharks.games.colorgame.resources;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Amit
 */
public class SoundEngine {
    Thread soundThread;
    Clip clip;
    private String filename;

    public SoundEngine(final String name, final boolean loop) {
        this.filename = name;
        soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioInputStream as1 = null;
                try {
                    as1 = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(name)));

                    AudioFormat af = as1.getFormat();

                    clip = AudioSystem.getClip();
                    DataLine.Info info = new DataLine.Info(Clip.class, af);

                    Line line1 = AudioSystem.getLine(info);

                    if (!line1.isOpen()) {
                        clip.open(as1);
                        if (loop) {
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                        clip.start();
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public SoundEngine(final InputStream in,final boolean loop) {
        soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioInputStream as1 = null;
                try {
                    as1 = AudioSystem.getAudioInputStream(new BufferedInputStream(in));

                    AudioFormat af = as1.getFormat();

                    clip = AudioSystem.getClip();
                    DataLine.Info info = new DataLine.Info(Clip.class, af);

                    Line line1 = AudioSystem.getLine(info);

                    if (!line1.isOpen()) {
                        clip.open(as1);
                        if (loop) {
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                        clip.start();
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void start() {
        soundThread.start();
    }

    public void stop() {
        soundThread.start();
    }
}
