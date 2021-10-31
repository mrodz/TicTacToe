package tictactoe.components;

import external.AudioReader;
import external.ImageReader;
import tictactoe.Colors;
import tictactoe.TicTacToe;
import tictactoe.internal.AppConstants;
import tictactoe.internal.Game;
import tictactoe.internal.players.Playable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

import static tictactoe.TicTacToe.*;

/**
 * The yellow bubble at the top of the application window; contains the Action Button
 * and two player select wheels.
 */
public class ControlPanel extends JPanel {
    static int shift = asInt(DECIDED_SIZE * 0.025);
    static Color fill = Colors.query("CONTROL_PANEL_BACKGROUND");
    static Color border = Colors.query("CONTROL_PANEL_BORDER");
    static int[] winSize = {/*x*/ asInt(DECIDED_SIZE * 0.9), /*y*/ asInt(DECIDED_SIZE * 0.9 * 0.2),};
    static int[] size = {/*x*/ asInt(winSize[0] * 0.9), /*y*/ asInt(winSize[1] * 0.65),};

    VolatileTextButton p1 = new VolatileTextButton(AppConstants.players);
    VolatileTextButton p2 = new VolatileTextButton(AppConstants.players);

    public ControlPanel() {
        this.setSize(winSize[0], winSize[1]);
        this.setPreferredSize(new Dimension(winSize[0], winSize[1]));
        this.setOpaque(false);

        Image start =
                Objects.requireNonNull(ImageReader.forName("start_button.png"))
                        .getScaledInstance((int) (DECIDED_SIZE * 0.085), (int) (DECIDED_SIZE * 0.085), Image.SCALE_SMOOTH);
        Image start_clicked =
                Objects.requireNonNull(ImageReader.forName("start_button_clicked.png"))
                        .getScaledInstance((int) (DECIDED_SIZE * 0.085), (int) (DECIDED_SIZE * 0.085), Image.SCALE_SMOOTH);
        Image start_hover =
                Objects.requireNonNull(ImageReader.forName("start_button_hover.png"))
                        .getScaledInstance((int) (DECIDED_SIZE * 0.085), (int) (DECIDED_SIZE * 0.085), Image.SCALE_SMOOTH);

        Image reset =
                Objects.requireNonNull(ImageReader.forName("reset.png"))
                        .getScaledInstance((int) (DECIDED_SIZE * 0.085), (int) (DECIDED_SIZE * 0.085), Image.SCALE_SMOOTH);
        Image reset_clicked =
                Objects.requireNonNull(ImageReader.forName("reset_clicked.png"))
                        .getScaledInstance((int) (DECIDED_SIZE * 0.085), (int) (DECIDED_SIZE * 0.085), Image.SCALE_SMOOTH);
        Image reset_hover =
                Objects.requireNonNull(ImageReader.forName("reset_hover.png"))
                        .getScaledInstance((int) (DECIDED_SIZE * 0.085), (int) (DECIDED_SIZE * 0.085), Image.SCALE_SMOOTH);

        JPanel items = new JPanel(new BorderLayout());
        items.setOpaque(false);
        items.setBorder(new EmptyBorder(asInt(DECIDED_SIZE * 0.03), 0, 0, 0));

        JButton actionButton;
        {
            actionButton = new JButton(new ImageIcon(start)) {

                @Override
                public JToolTip createToolTip() {
                    JToolTip t = new JToolTip() {
                        @Override
                        public void setBackground(Color bg) {
                            super.setBackground(Colors.query("BUBBLE_BACKGROUND"));
                        }

                        @Override
                        public void setForeground(Color fg) {
                            super.setForeground(Color.BLACK);
                        }

                        @Override
                        public void setFont(Font font) {
                            super.setFont(new Font("Arial", Font.BOLD, (int) (getBestFontSize() * 0.45)));
                        }
                    };
                    t.setOpaque(false);
                    return t;
                }
            };

            Font baseFont = new Font("Arial", Font.PLAIN, (int) (getBestFontSize() * 0.5));
            Font hoverFont = new Font("Arial", Font.BOLD, (int) (getBestFontSize() * 0.5));

            actionButton.setHorizontalAlignment(SwingConstants.CENTER);
            actionButton.setVerticalAlignment(SwingConstants.CENTER);

//            actionButton.setToolTipText("Start Button: Click to play!");
            actionButton.setFont(baseFont);
            actionButton.setForeground(Color.BLACK);
            actionButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            actionButton.setHorizontalTextPosition(SwingConstants.CENTER);

            // Mouse Actions
            actionButton.addMouseListener(new MouseAdapter() {
                boolean clicking = false;
                boolean hovering = false;
                boolean startButton = true;

                Image originalSprite = startButton ? start : reset;
                Image clickedSprite = startButton ? start_clicked : reset_clicked;
                Image hoverSprite = startButton ? start_hover : reset_hover;

                @Override
                public void mousePressed(MouseEvent e) {
                    actionButton.setIcon(new ImageIcon(clickedSprite));
                    actionButton.setFont(hoverFont);
                    clicking = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    actionButton.setIcon(new ImageIcon(originalSprite));
                    actionButton.setForeground(Color.BLACK);
                    actionButton.setIcon(new ImageIcon(hovering ? hoverSprite : originalSprite));
                    clicking = false;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!clicking) {
                        actionButton.setIcon(new ImageIcon(hoverSprite));
                    }
                    hovering = true;
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    if (!clicking && hoverSprite != null) {
                        actionButton.setIcon(new ImageIcon(originalSprite));
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    startButton = !startButton;
                    StatusBar.updateText(AppConstants.Messages.Status.GAME_STARTED);
                    originalSprite = (startButton ? start : reset);
                    clickedSprite = (startButton ? start_clicked : reset_clicked);
                    hoverSprite = (startButton ? start_hover : reset_hover);

                    actionButton.setIcon(new ImageIcon(hoverSprite));

                    if (!startButton) {
                        String[] arr = getPlayerNames();
                        Playable[] players = {Playable.forName(arr[0]), Playable.forName(arr[1])};
                        Game.updatePlayers(players[0], players[1]);

//                        actionButton.setToolTipText("Reset Button: Click to reset the board!");

                        p1.setLocked(true);
//                        p1.text.setToolTipText(String.format("%s - This is player 'X'", arr[0]));
                        p1.setBold(true);
                        p2.setLocked(true);
//                        p2.text.setToolTipText(String.format("%s - This is player 'O'", arr[0]));
                        p2.setBold(true);

                        try {
                            AudioReader.playSound("game_start.wav");
                        } finally {
                            Game.round.play();
                        }
                    } else {
//                        actionButton.setToolTipText("Start Button: Click to play!");
                        Game.resetBoard();
                        Game.round.t.interrupt();
                        TicTacToe.playzone.lock(true);

                        StatusBar.updateText(AppConstants.Messages.Status.RESET_BOARD);
//                        p1.text.setToolTipText(AppConstants.Messages.Players.forName(p1.values.getCurrentElement()).getDesc());
                        p1.setLocked(false);
                        p1.setBold(false);

//                        p2.text.setToolTipText(AppConstants.Messages.Players.forName(p2.values.getCurrentElement()).getDesc());
                        p2.setBold(false);
                        p2.setLocked(false);

                        AudioReader.playSound("sweep.wav");
                    }
                }
            });

        }

        actionButton.setBorderPainted(false);
        actionButton.setContentAreaFilled(false);
        actionButton.setFocusable(false);

        items.add(p1, BorderLayout.WEST);
        items.add(actionButton, BorderLayout.CENTER);
        items.add(p2, BorderLayout.EAST);

        items.setOpaque(false);
        this.add(items);

    }

    public String[] getPlayerNames() {
        return new String[] {p1.values.getCurrentElement(), p2.values.getCurrentElement()};
    }

    /**
     * Draws the yellow box around the top of the app.
     * @param g default {@link Graphics} parameter
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(asInt(DECIDED_SIZE * 0.02)));

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(
                (winSize[0] - size[0]) / 2f + shift, (winSize[1] - size[1]) / 2f, size[0], size[1], 50, 200);

        g2d.setPaint(border);
        g2d.fill(bubble);

        g2d.setPaint(fill);
        g2d.draw(bubble);
    }
}
