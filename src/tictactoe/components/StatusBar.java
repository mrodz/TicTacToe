package tictactoe.components;

import tictactoe.Bubble;
import tictactoe.Colors;
import tictactoe.TicTacToe;
import tictactoe.internal.AppConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static tictactoe.TicTacToe.DECIDED_SIZE;
import static tictactoe.TicTacToe.getPercentOf;

/** The segment at the bottom of the app; here, the game status is displayed. */
public class StatusBar extends Bubble {
    private static final JLabel actualStatus = new JLabel();
    public static final String BASE_TEXT = AppConstants.Messages.Status.NOT_STARTED;

    public StatusBar() {
        super((int) (DECIDED_SIZE / 1.25), (int) (DECIDED_SIZE / 5.5));
        int size = (int) getPercentOf(DECIDED_SIZE, 10);

        this.setOpaque(true);
        this.setSize(new Dimension(DECIDED_SIZE, DECIDED_SIZE / 4));
        this.setBorder(new EmptyBorder(size, (int) (size * 1.75), size, (int) (size * 1.75)));

        JLabel status = new JLabel();
        status.setText("Game Status:");
        status.setHorizontalAlignment(SwingConstants.CENTER);
        status.setVerticalAlignment(SwingConstants.CENTER);
        status.setOpaque(false);
        status.setBackground(Colors.query("BUBBLE_BORDER"));
        status.setFont(new Font("Arial", Font.BOLD, (int) (TicTacToe.getBestFontSize() / 1.25)));

        actualStatus.setName("LabelStatus");
        actualStatus.setText(BASE_TEXT);
        actualStatus.setFont(new Font("Arial", Font.PLAIN, (int) (TicTacToe.getBestFontSize() / 1.5)));
        actualStatus.setOpaque(false);
        actualStatus.setFocusable(false);
        actualStatus.setHorizontalAlignment(SwingConstants.CENTER);
        actualStatus.setVerticalAlignment(SwingConstants.CENTER);
        actualStatus.setSize(DECIDED_SIZE, DECIDED_SIZE / 4);

        this.add(status, BorderLayout.NORTH);
        this.add(actualStatus, BorderLayout.CENTER);
    }

    public static void updateText(String newText) {
        actualStatus.setText(newText);
    }
}
