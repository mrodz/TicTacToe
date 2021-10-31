package tictactoe.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import external.AudioReader;
import external.LogMessage;
import tictactoe.Colors;
import tictactoe.internal.*;
import tictactoe.internal.players.Human;

import static tictactoe.TicTacToe.*;
import static tictactoe.internal.AppConstants.range;

/**
 * This is the actual playing space of the app, where the game is displayed and users can submit their input.
 * @see Round#play()
 */
public class PlayZone extends JPanel implements Serializable {
    private static final Font baseFont = new Font("Arial", Font.PLAIN, asInt(getBestFontSize() * 0.75));
    private static final Font hoverFont = new Font("Arial", Font.BOLD, asInt(getBestFontSize() * 0.75));
    private static final JPanel init = new JPanel(new GridLayout(3, 3));

    private final int BAR_THICKNESS = asInt(DECIDED_SIZE * 0.05);

    public static transient boolean locked = true;
    public static transient boolean won = false;
    public static transient boolean move = false;

    public PlayZone() {
        //to center
        init.setOpaque(false);

        initializeButtons();

        setSize(new Dimension(DECIDED_SIZE, DECIDED_SIZE));
        setBorder(new EmptyBorder(APP_SIZE.height / 15, APP_SIZE.width / 15, APP_SIZE.height / 15, APP_SIZE.width / 15));
        setOpaque(false);
        setLayout(new BorderLayout());
        add(init, BorderLayout.CENTER);
    }

    /**
     * <p>
     *     Add logic (meant for human input) to the buttons; synchronization with {@link Human#getPlacement()},
     *     stylistic preferences, and click/hover scripting.
     * </p>
     * <b>MUST BE CALLED IN ORDER TO START THE GAME!</b>
     */
    public synchronized void initializeButtons() {
        for (int i : range(Game.master.slots.length)) {
            for (int j : range(Game.master.slots.length)) {
                Slot slot = Game.master.slots[i][j];
                slot.getButton().setFont(new Font("Arial", Font.PLAIN, asInt(getBestFontSize() * 0.75)));

                int ds = (int) (DECIDED_SIZE / 3.7);
                slot.getButton().setSize(new Dimension(ds, ds));
                slot.getButton().setPreferredSize(new Dimension(ds, ds));

                // clear previous action listeners
                Arrays.stream(slot.getButton().getActionListeners()).forEach(slot.getButton()::removeActionListener);

                // on master click
                slot.getButton().addActionListener((n) -> {
                    // ignore all clicks on a locked cell.
                    if (locked || Game.master.slots[i][j].getSymbol() != AppConstants.BLANK_SLOT) {
                        AudioReader.playSound("no.wav");
                        return;
                    }

                    // if a call is made to Human#getPlacement(), then its thread
                    // will pause until an input (here) is read.
                    synchronized (Human.syncObject) {
                        // Button Clicks from https://www.zapsplat.com
                        try {
                            Random r = new Random();

                            String link1 = "click1.wav";
                            String link2 = "click2.wav";
                            AudioReader.playSound(r.nextInt(2) == 0 ? link1 : link2);

                        } finally {
                            madeMove();
                            Human.addPlacementToQueue(new Placement(slot.x, slot.y));
                            Human.syncObject.notify();
                        }
                    }
                });

                slot.getButton().setVisible(true);
                slot.getButton().setFocusable(false);
                slot.getButton().setContentAreaFilled(false);
                slot.getButton().setBorderPainted(false);

                slot.getButton().addMouseListener(new MouseAdapter() {
                    boolean clicking = false;
                    boolean hovering = false;
                    boolean discreteLocked = false;

                    {
                        repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        clicking = true;
                        if (!discreteLocked) {
                            Game.master.backgroundBubbles[i][j].repaint();
                            Game.master.backgroundBubbles[i][j].setClicking(!locked);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (!hovering) slot.getButton().setFont(baseFont);
                        clicking = false;
                        Game.master.backgroundBubbles[i][j].setClicking(false);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (locked || Game.master.slots[i][j].getSymbol() != AppConstants.BLANK_SLOT)
                            Game.master.backgroundBubbles[i][j].setLocked(true);

                        slot.getButton().setFont(hoverFont);
                        hovering = true;
                        Game.master.backgroundBubbles[i][j].setHovering(true);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (locked || Game.master.backgroundBubbles[i][j].isLocked()) {
                            Game.master.backgroundBubbles[i][j].setLocked(false);
                        }

                        hovering = false;
                        Game.master.backgroundBubbles[i][j].setHovering(false);
                        if (clicking || Game.master.backgroundBubbles[i][j].hasWon()) {
                            slot.getButton().setFont(hoverFont);
                        } else {
                            slot.getButton().setFont(baseFont);
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        discreteLocked = true;
                    }
                });

                Game.master.backgroundBubbles[i][j].add(slot.getButton());
                init.add(Game.master.backgroundBubbles[i][j]);

                Game.master.slots[i][j] = slot;
            }
        }
    }

    /**
     * If a winner is found, paint the background of the tiles that directly cause the win
     * to the colors specified in {@link Colors} as "WON_TILE" and "WON_TILE_HOVER" respectively.
     * @param g the grid to be analyzed, should be reserved to only {@link Game#master}
     */
    public void paintWinningLine(Grid g) {
        GameState gs = Grid.analyzePosition(g, AppConstants.FIRST_MOVE);

        if (gs == GameState.FIRST_WIN || gs == GameState.SECOND_WIN) {


            won = true;
            int key = Grid.analyzePositionToKey(g);

            lock(true);

            // paint the winning three green.
            for (int k : range(3)) {
                switch (key) {
                    case AppConstants.GameKeys.TIE_NONE:
                        Game.round.winner = null;
                        break;
                    case AppConstants.GameKeys.VERTICAL_1:
                        Game.master.backgroundBubbles[k][0].setWon(true);
                        Game.master.slots[k][0].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[k][0].getSymbol();
                        break;
                    case AppConstants.GameKeys.VERTICAL_2:
                        Game.master.backgroundBubbles[k][1].setWon(true);
                        Game.master.slots[k][1].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[k][1].getSymbol();
                        break;
                    case AppConstants.GameKeys.VERTICAL_3:
                        Game.master.backgroundBubbles[k][2].setWon(true);
                        Game.master.slots[k][2].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[k][2].getSymbol();
                        break;
                    case AppConstants.GameKeys.HORIZONTAL_1:
                        Game.master.backgroundBubbles[0][k].setWon(true);
                        Game.master.slots[0][k].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[0][k].getSymbol();
                        break;
                    case AppConstants.GameKeys.HORIZONTAL_2:
                        Game.master.backgroundBubbles[1][k].setWon(true);
                        Game.master.slots[1][k].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[1][k].getSymbol();
                        break;
                    case AppConstants.GameKeys.HORIZONTAL_3:
                        Game.master.backgroundBubbles[2][k].setWon(true);
                        Game.master.slots[2][k].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[2][k].getSymbol();
                        break;
                    case AppConstants.GameKeys.DIAGONAL_1:
                        Game.master.backgroundBubbles[k][k].setWon(true);
                        Game.master.slots[k][k].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[k][k].getSymbol();
                        break;
                    case AppConstants.GameKeys.DIAGONAL_2:
                        Game.master.backgroundBubbles[2 - k][k].setWon(true);
                        Game.master.slots[2 - k][k].getButton().setFont(hoverFont);
                        Game.round.winner = Game.master.slots[2 - k][k].getSymbol();
                        break;
                    default:
                        new LogMessage("Unknown game key found: " + key, LogMessage.ERROR);
                        throw new IllegalStateException("illegal return key: " + key);
                }
            }
            repaint();
        }
    }

    /**
     * Draws a background for the game board, to give the buttons contrast against
     * the other background image. Also draws the "board," or the two sticks that
     * serve as a visual representation of the columns that make up the {@link PlayZone}.
     * @param g default {@link Graphics} parameter, will be cast to {@link Graphics2D}.
     */
    @Override
    protected void paintComponent(Graphics g) {
        int startingPosition = (this.getWidth() - BAR_THICKNESS * (Game.master.slots.length - 1)) / 3;

        Graphics2D g2d = (Graphics2D) g;

        int[] starting = {asInt(this.getWidth() * 0.05), asInt(this.getHeight() * 0.05)};
        int[] sizes = {this.getWidth() - starting[0] * 2, this.getHeight() - starting[1] * 2};

        // Paint the first background (drop shadow)
        g2d.setPaint(new Color(0xFF8386A0, true));
        g2d.fillRect(asInt(starting[0] + DECIDED_SIZE * 0.0115), asInt(starting[1] + DECIDED_SIZE * 0.0115), asInt(sizes[0] + DECIDED_SIZE * 0.0115), asInt(sizes[1] + DECIDED_SIZE * 0.0115));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.setStroke(new BasicStroke(asInt(BAR_THICKNESS * 0.25)));
        RoundRectangle2D.Double rect1 = new RoundRectangle2D.Double(asInt(starting[0] + DECIDED_SIZE * 0.0115), asInt(starting[1] + DECIDED_SIZE * 0.0115), asInt(sizes[0] + DECIDED_SIZE * 0.0115), asInt(sizes[1] + DECIDED_SIZE * 0.0115), 10, 10);
        Area area1 = new Area(rect1);
        g2d.draw(area1);

        // Paint the normal background
        g2d.setPaint(Colors.query("PLAYZONE_BACKGROUND"));
        g2d.fillRect(starting[0], starting[1], sizes[0], sizes[1]);
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.setStroke(new BasicStroke(asInt(BAR_THICKNESS * 0.25)));
        RoundRectangle2D.Double rect0 = new RoundRectangle2D.Double(starting[0], starting[1], sizes[0], sizes[1], 10, 10);
        Area area0 = new Area(rect0);
        g2d.draw(area0);

        // Horizontal
        g2d.setPaint(new Color(0xc49176));
        g2d.fillRect(asInt(startingPosition * 1.125), startingPosition / 3, BAR_THICKNESS, asInt(this.getHeight() - startingPosition * 0.66f));
        g2d.fillRect(this.getWidth() - BAR_THICKNESS - asInt(startingPosition * 1.125), startingPosition / 3, BAR_THICKNESS, asInt(this.getHeight() - startingPosition * 0.66f));

        // Vertical
        g2d.setPaint(new Color(0xae826b));
        g2d.fillRect(startingPosition / 3, asInt(startingPosition * 1.125), asInt(this.getHeight() - startingPosition * 0.66f), BAR_THICKNESS);
        g2d.fillRect(startingPosition / 3, this.getWidth() - BAR_THICKNESS - asInt(startingPosition * 1.125), asInt(this.getHeight() - startingPosition * 0.66f), BAR_THICKNESS);
    }

    /**
     * Sets whether the grid can be used. If true, it will function as normal;
     * if not, then the cells be painted with a background color of
     * {@link Colors#query(String) Colors.query("PLAYZONE_BUTTON_BACKGROUND_LOCKED")}
     * and clicking functions will not work.
     *
     * @param l true or false
     */
    public synchronized void lock(boolean l) {
        locked = l;
    }

    /**
     * Sets {@link #move} to its inverse.
     */
    public static void madeMove() {
        move = !move;
    }
}
