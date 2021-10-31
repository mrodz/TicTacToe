package tictactoe;

import external.AudioReader;
import external.ImageReader;
import tictactoe.components.*;
import tictactoe.components.MenuBar;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class TicTacToe extends JFrame {
    public static final JPanel WRAPPER = new JPanel(new BorderLayout());
    public static final ImageIcon APP_ICON = new ImageIcon(Objects.requireNonNull(ImageReader.forName("AppIcon.png")));
    public static final Dimension WINDOW_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int DECIDED_SIZE = asInt(WINDOW_SIZE.height / 1.75);
    public static final Dimension APP_SIZE = new Dimension(asInt(DECIDED_SIZE * 1.05), asInt(DECIDED_SIZE * 1.05));

    public static PlayZone playzone = new PlayZone();
    public static JPanel topWrapper = new ImageBackground(new BorderLayout());

    public TicTacToe() {
        super("Desktop Tic Tac Toe");

        int size = asInt(getPercentOf(DECIDED_SIZE, 10));

        topWrapper.setBorder(new EmptyBorder(0, 0, asInt(DECIDED_SIZE * 0.025), asInt(DECIDED_SIZE * 0.025)));

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

        topWrapper.add(playzone, BorderLayout.CENTER);
        topWrapper.add(test, BorderLayout.NORTH);

        JComponent status = new StatusBar();

        JPanel sidebar = new MenuBar(this);

        JPanel temp = new JPanel(new BorderLayout());
        temp.setBorder(new MatteBorder(0, 0, asInt(getPercentOf(DECIDED_SIZE, 1)),  asInt(getPercentOf(DECIDED_SIZE, 1)), Colors.query("MAIN_BORDER")));
        temp.add(topWrapper, BorderLayout.CENTER);
        temp.add(status, BorderLayout.SOUTH);


        WRAPPER.add(temp, BorderLayout.CENTER);
        WRAPPER.add(sidebar, BorderLayout.EAST);

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