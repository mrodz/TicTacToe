package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.Serial;
import java.io.Serializable;

import static tictactoe.TicTacToe.DECIDED_SIZE;

/** Used to draw a rectangular bubble around elements. This is a decorative class */
public class Bubble extends JComponent implements Serializable {
    @Serial
    @Deprecated
    private static final long serialVersionUID = -5389178141802153305L;
    protected final int x;
    protected final int y;

    protected Color fill;
    protected Color border;
    protected Color background;

    protected final boolean paintBG;
    protected final int thickness;

    public Bubble(Color fill, Color border, Color background, int x, int y, boolean paintBG, int thickness) {
        this.setLayout(new BorderLayout());
        this.fill = fill;
        this.border = border;
        this.background = background;
        this.x = x;
        this.y = y;
        this.paintBG = paintBG;
        this.thickness = thickness;
    }

    public Bubble(int x, int y) {
        this(Colors.query("BUBBLE_BACKGROUND"), Colors.query("BUBBLE_BORDER"),
                Colors.query("STATUS_BAR_BACKGROUND"), x, y, true, (int) (DECIDED_SIZE * 0.025));
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        int[] starting = new int[] {(this.getWidth() - this.x) / 2 /* x */, (this.getHeight() - this.y) / 2 /* y */};

        if (this.paintBG) {
            g2d.setPaint(this.background);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        g2d.setPaint(this.fill);
        g2d.fillRect(starting[0], starting[1], this.x, this.y);
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.setStroke(new BasicStroke(this.thickness));
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double(starting[0], starting[1], this.x, this.y, 10, 10);
        Area area = new Area(rect);
        g2d.setPaint(this.border);
        g2d.draw(area);
    }
}