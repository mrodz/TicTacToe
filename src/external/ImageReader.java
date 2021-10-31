package external;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Utility Class used to read images in the {@link images} directory.
 * @see ImageReader#forName(String)
 */
public class ImageReader {
    /** Used to keep track of how many times these resources have been accessed. */
    private static final ArrayList<URL> resources = new ArrayList<>();
    /** how many times has a resource failed to load */
    private static int fails = 0;

    /**
     * Get an image from its name. This method searches for image files found inside the {@link images} directory.
     * @param name the filename, without the path: {@code foo.png}, {@code bar.jpg}
     * @return a {@link Image} corresponding to the audio clip.
     */
    public static Image forName(String name) {
        URL binary = ImageReader.class.getResource("/images/" + name);
        new LogMessage(String.format("Attempting to load image '%s'", name), LogMessage.INFO);
        if (binary != null) {
            if (resources.size() <= 250) {
                resources.add(binary);
                new LogMessage(String.format("Successfully loaded '%s' (total %d, %d failed)", name, resources.size(), fails), LogMessage.INFO);
            } else {
                new LogMessage(String.format("Successfully loaded '%s' (Over 250 calls, reduced to save memory)", name), LogMessage.INFO);
            }
            return new ImageIcon(binary).getImage();
        } else {
            fails++;
            new LogMessage(String.format("Could not find image '%s' (%d fails)", name, fails), LogMessage.WARN);
            return null;
        }
    }
}
