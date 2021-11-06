package tictactoe.components;

import external.AudioReader;
import external.ImageReader;

import external.LogMessage;
import tictactoe.Colors;
import tictactoe.internal.AppConstants;
import tictactoe.internal.Game;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;

import java.util.Objects;
import java.util.function.Consumer;

import static tictactoe.TicTacToe.DECIDED_SIZE;
import static tictactoe.TicTacToe.asInt;
import static tictactoe.TicTacToe.getPercentOf;
import static tictactoe.TicTacToe.getBestFontSize;

/**
 * The sidebar to the app; contains the exit + minimize buttons, and also the tutorial.
 */
public class MenuBar extends JPanel implements Serializable {
    public MenuBar(JFrame applicant) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS /*nope X_AXIS*/));
        this.setSize(DECIDED_SIZE / 5, this.getSize().height);
        this.setBorder(new EmptyBorder((int) getPercentOf(DECIDED_SIZE, 3), /*(int) getPercentOf(DECIDED_SIZE, 3)*/ -asInt(getPercentOf(DECIDED_SIZE, 1.5)), (int) getPercentOf(DECIDED_SIZE, 3), /*(int) getPercentOf(DECIDED_SIZE, 3))*/ -asInt(getPercentOf(DECIDED_SIZE, 1))));

        //
        // SPRITES
        //
        Image exitSprite =
                Objects.requireNonNull(ImageReader.forName("cancel.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);
        Image exitSpriteClicked =
                Objects.requireNonNull(ImageReader.forName("cancel_clicked.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);

        Image minimizeSprite =
                Objects.requireNonNull(ImageReader.forName("minimize_button.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);

        Image minimizeSpriteClicked =
                Objects.requireNonNull(ImageReader.forName("minimize_button_clicked.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);

        Image resetSprite =
                Objects.requireNonNull(ImageReader.forName("reset.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);
        Image resetSpriteClicked =
                Objects.requireNonNull(ImageReader.forName("reset_clicked.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);

        Image creditsSprite =
                Objects.requireNonNull(ImageReader.forName("credits_button.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);
        Image creditsSpriteClicked =
                Objects.requireNonNull(ImageReader.forName("credits_button_clicked.png"))
                        .getScaledInstance((int) (this.getWidth() * 0.50), (int) (this.getWidth() * 0.50), Image.SCALE_SMOOTH);

        //
        // END OF SPRITES
        //

        JButton exitButton = createSidebarButton(exitSprite, exitSpriteClicked, "Exit", "Click to close this app", n -> {
            //new ConfirmWindow();
            Game.round.t.interrupt();
            applicant.dispose();
            new LogMessage("Attempting to close window", LogMessage.INFO);
        });
        JButton minimizeButton = createSidebarButton(minimizeSprite, minimizeSpriteClicked, "Hide", "Click to minimize this app", n -> applicant.setState(JFrame.ICONIFIED));
        JPanel menuDivider = new JPanel();
        menuDivider.setSize(exitButton.getSize());
        menuDivider.setOpaque(false);

        JButton resetButton = createSidebarButton(resetSprite, resetSpriteClicked, "Reset", "Click to reset this game", n -> {
            Game.resetBoard();
            StatusBar.updateText(AppConstants.Messages.Status.RESET_BOARD);
        });
        resetButton.setName("ButtonStartReset");

        JButton creditsButton = createSidebarButton(creditsSprite, creditsSpriteClicked, "Help", "Click to view the tutorial + credits", n -> new Tutorial());

        this.add(exitButton);
        this.add(Box.createRigidArea(new Dimension(0, (int) getPercentOf(DECIDED_SIZE, 2))));
        this.add(minimizeButton);
        this.add(Box.createRigidArea(new Dimension(0, (int) getPercentOf(DECIDED_SIZE, 2))));
        this.add(menuDivider);
        this.add(Box.createRigidArea(new Dimension(0, (int) getPercentOf(DECIDED_SIZE, 2))));
        this.add(creditsButton);
    }

    public static JButton createSidebarButton(Image originalSprite, Image clickedSprite, String text, String hoverText, Consumer<MouseEvent> clickAction) {
        return createSidebarButton(originalSprite, clickedSprite, null, text, hoverText, clickAction, Colors.query("BUBBLE_BACKGROUND"));
    }

    public static JButton createSidebarButton(Image originalSprite, Image clickedSprite, Image hoverSprite, String text, String hoverText, Consumer<MouseEvent> clickAction, Color textColor) {
        // Setting custom tooltip aesthetics.
        JButton temp = new JButton(new ImageIcon(originalSprite)) {
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

        Font baseFont  = new Font("Arial", Font.PLAIN, (int) (getBestFontSize() * 0.5));
        Font hoverFont = new Font("Arial", Font.BOLD, (int) (getBestFontSize() * 0.5));

        temp.setHorizontalAlignment(SwingConstants.CENTER);
        temp.setVerticalAlignment(SwingConstants.CENTER);
        if (text != null) {
            temp.setText(text);
        }
        temp.setToolTipText(hoverText);
        temp.setFont(baseFont);
        temp.setForeground(Color.BLACK);
        temp.setVerticalTextPosition(SwingConstants.BOTTOM);
        temp.setHorizontalTextPosition(SwingConstants.CENTER);


        synchronized (MenuBar.class) {
            // Mouse Actions
            temp.addMouseListener(new MouseAdapter() {
                boolean clicking = false;
                boolean hovering = false;

                @Override
                public void mousePressed(MouseEvent e) {
                    temp.setIcon(new ImageIcon(clickedSprite));
                    temp.setFont(hoverFont);
                    temp.setForeground(textColor);
                    clicking = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    temp.setIcon(new ImageIcon(originalSprite));
                    temp.setForeground(Color.BLACK);
                    if (hoverSprite != null) {
                        temp.setIcon(new ImageIcon(hovering ? hoverSprite : originalSprite));
                    }
                    temp.setFont(baseFont);
                    clicking = false;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (hoverSprite != null && !clicking) {
                        temp.setIcon(new ImageIcon(hoverSprite));
                    }
                    hovering = true;
                    temp.setFont(hoverFont);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovering = false;
                    if (clicking) {
                        temp.setFont(hoverFont);
                    } else {
                        temp.setFont(baseFont);
                        if (hoverSprite != null) {
                            temp.setIcon(new ImageIcon(originalSprite));
                        }
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    clickAction.accept(e);
                    AudioReader.playSound("gn_button_click.wav");
                }
            });
        }

        temp.setFocusable(false);
        temp.setBorderPainted(false);
        temp.setContentAreaFilled(false);

        return temp;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Colors.query("SIDEBAR_BACKGROUND"));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
