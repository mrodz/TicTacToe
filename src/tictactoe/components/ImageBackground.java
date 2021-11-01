package tictactoe.components;

import external.ImageReader;

import javax.swing.JPanel;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Objects;

/**
 * Renders the background image, the X's and O's (images/XOBackground.png)
 */
public class ImageBackground extends JPanel {
    static Image image;
    public static Dimension maxSize = null;
    public static boolean resized = false;

    public ImageBackground(BorderLayout borderLayout) {
        super(borderLayout);
        image = Objects.requireNonNull(ImageReader.forName("XOBackground.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        if (image == null) {
            throw new IllegalStateException("image is null");
        }

        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        if (maxSize != null && (size.height < maxSize.height || size.width < maxSize.width)) {
            image = image.getScaledInstance(maxSize.width, maxSize.height, Image.SCALE_SMOOTH);
        }

        g2d.drawImage(image, 0, 0, null);
        resized = true;
    }
}
