package external;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Utility Class used to read audio in the {@link audio} directory.
 * @see AudioReader#forName(String)
 * @see AudioReader#playSound(String)
 */
public class AudioReader {
    /** Used to keep track of how many times these resources have been accessed. */
    private static final ArrayList<URL> resources = new ArrayList<>();
    /** how many times has a resource failed to load */
    private static int fails = 0;

    /**
     * Get an audio clip from its name. This method searches for audio files found inside the {@link audio} directory.
     * @param name the filename, without the path: {@code foo.wav}, {@code bar.wav}
     * @return a {@link Clip} corresponding to the audio clip.
     */
    public static Clip forName(String name) {
        URL binary = ImageReader.class.getResource("/audio/" + name);
        new LogMessage(String.format("Attempting to load audio '%s'", name), LogMessage.INFO);
        if (binary != null) {
            if (resources.size() < 250) {
                resources.add(binary);
                new LogMessage(String.format("Successfully loaded '%s' (total %d, %d failed)", name, resources.size(), fails), LogMessage.INFO);
            } else {
                new LogMessage(String.format("Successfully loaded '%s' (Over 250 calls, reduced to save memory)", name), LogMessage.INFO);
            }
            try {
                return AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                new LogMessage("Error loading audio '" + name + "': " + e, LogMessage.WARN);
                fails++;
                return null;
            }
        } else {
            fails++;
            new LogMessage(String.format("Could not find audio '%s' (%d fails)", name, fails), LogMessage.WARN);
            return null;
        }
    }

    /**
     * Directly play a sound based on a filename.
     * @param fileName the filename, without the path: {@code foo.wav}, {@code bar.wav}
     */
    public static void playSound(String fileName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(AudioReader.class.getResource("/audio/" + fileName)));
            Clip c = forName(fileName);
            Objects.requireNonNull(c).open(ais);
            c.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            new LogMessage("Error attempting to play audio '" + fileName + "': " + e, LogMessage.WARN);
        }
    }
}
