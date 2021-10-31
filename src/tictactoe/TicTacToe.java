package tictactoe;

import external.AudioReader;
import external.ImageReader;

import tictactoe.components.PlayZone;
import tictactoe.components.MenuBar;
import tictactoe.components.ImageBackground;
import tictactoe.components.ControlPanel;
import tictactoe.components.StatusBar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Objects;

public class TicTacToe extends JFrame {
    /**
     * This is the main content panel of the app. To rendera component,
     * use the {@link JComponent#add(Component)} method on this object.
     */
    public static final JPanel WRAPPER;

    /**
     * This app's icon, found under 'src/images/AppIcon.png'
     */
    public static final ImageIcon APP_ICON;

    /**
     * Constant value, <b>the most important measurement</b> in this app.
     * Many components are sized relative to this variable.
     */
    public static final int DECIDED_SIZE;

    /** Wrapper for {@code Toolkit.getDefaultToolkit().getScreenSize()} */
    public static final Dimension WINDOW_SIZE;

    /** Value that is used in a few locations in this program; will most likely get removed. */
    public static final Dimension APP_SIZE;

    /**
     * The playing field; it is declared outside the scope of the constructor
     * in order to allow for other classes to access it.
     */
    public static final PlayZone PLAY_ZONE;

    /**
     * The uppermost components of the app.
     */
    public static final JPanel TOP_WRAPPER;

    // Initialize the constant variables.
    static {
        APP_ICON = new ImageIcon(Objects.requireNonNull(ImageReader.forName("AppIcon.png")));
        WRAPPER = new JPanel(new BorderLayout());
        WINDOW_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
        DECIDED_SIZE = asInt(WINDOW_SIZE.height / 1.75);
        APP_SIZE = new Dimension(asInt(DECIDED_SIZE * 1.05), asInt(DECIDED_SIZE * 1.05));

        PLAY_ZONE = new PlayZone();
        TOP_WRAPPER = new ImageBackground(new BorderLayout());
    }

    /**
     * Initialize the app.
     */
    public TicTacToe() {
        super("Desktop Tic Tac Toe");

        int size = asInt(getPercentOf(DECIDED_SIZE, 10));

        TOP_WRAPPER.setBorder(new EmptyBorder(0, 0, asInt(DECIDED_SIZE * 0.025), asInt(DECIDED_SIZE * 0.025)));

        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setOpaque(false);

        JPanel text = new JPanel(new BorderLayout());
        text.setOpaque(false);
        text.setSize(new Dimension(DECIDED_SIZE / 3, DECIDED_SIZE));

        JLabel header = new JLabel();
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(size / 3, size, 0, size));
        header.setText("Tic Tac Toe");
        header.setFont(new Font("Arial", Font.BOLD, asInt(getBestFontSize() * 1.85)));
        header.setHorizontalAlignment(SwingConstants.LEFT);
        header.setVerticalAlignment(SwingConstants.CENTER);
        text.add(header, BorderLayout.NORTH);

        textWrapper.add(text, BorderLayout.CENTER);

        ControlPanel test = new ControlPanel();
        test.setBackground(Color.RED);
        test.setOpaque(true);

        TOP_WRAPPER.add(PLAY_ZONE, BorderLayout.CENTER);
        TOP_WRAPPER.add(test, BorderLayout.NORTH);

        JComponent status = new StatusBar();

        JPanel sidebar = new MenuBar(this);

        JPanel temp = new JPanel(new BorderLayout());
        temp.setBorder(new MatteBorder(0, 0, asInt(getPercentOf(DECIDED_SIZE, 1)),  asInt(getPercentOf(DECIDED_SIZE, 1)), Colors.query("MAIN_BORDER")));
        temp.add(TOP_WRAPPER, BorderLayout.CENTER);
        temp.add(status, BorderLayout.SOUTH);


        // Adding final components
        WRAPPER.add(temp, BorderLayout.CENTER);
        WRAPPER.add(sidebar, BorderLayout.EAST);


        //
        // Init state
        //
        this.add(WRAPPER);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLayout(new BorderLayout());
        this.setIconImage(APP_ICON.getImage());
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeiconified(WindowEvent e) {
                AudioReader.playSound("gn_button_click.wav");
            }
        });

        ImageBackground.maxSize = this.getSize();

        this.setVisible(true);
    }

    @SuppressWarnings("SameParameterValue")
    public static double getPercentOf(double val, double x) {
        return val / 100f * x;
    }

    public static int getBestFontSize() {
        return WINDOW_SIZE.height / 25;
    }

    public static int asInt(double d) {
        return (int) d;
    }
}