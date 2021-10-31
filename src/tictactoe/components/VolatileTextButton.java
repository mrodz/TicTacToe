package tictactoe.components;

import external.AudioReader;
import external.ImageReader;
import tictactoe.Colors;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static tictactoe.TicTacToe.*;

/**
 * A button with a text state and two arrows used to cycle between input values.
 * @see ControlPanel
 */
public class VolatileTextButton extends JPanel {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public boolean locked = false;

    private static final Font baseFont = new Font("Arial", Font.PLAIN, asInt(getBestFontSize() * 0.45));
    private static final Font boldFont = new Font("Arial", Font.BOLD, asInt(getBestFontSize() * 0.45));

    JButton leftArrow;
    JButton rightArrow;

    Image leftArrowSprite  = Objects.requireNonNull(ImageReader.forName("LeftArrow.png"))
            .getScaledInstance(asInt(DECIDED_SIZE * 0.025), asInt(DECIDED_SIZE * 0.025), Image.SCALE_SMOOTH);
    Image rightArrowSprite = Objects.requireNonNull(ImageReader.forName("RightArrow.png"))
            .getScaledInstance(asInt(DECIDED_SIZE * 0.025), asInt(DECIDED_SIZE * 0.025), Image.SCALE_SMOOTH);

    JLabel text = new JLabel() {
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
    {
        text.setFont(baseFont);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setOpaque(false);
    }
    Wheel<String> values;

    public VolatileTextButton(String... values) {
        if (values.length <= 0) {
            throw new IllegalArgumentException("No arguments for 'values' parameter");
        }

        Dimension textBoxSize = new Dimension(asInt(DECIDED_SIZE * 0.12), asInt(DECIDED_SIZE * 0.05));
        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setOpaque(false);

        textWrapper.setMinimumSize(textBoxSize);
        textWrapper.setPreferredSize(textBoxSize);
        textWrapper.setMaximumSize(textBoxSize);

        this.setLayout(new BorderLayout());
        this.values = new Wheel<>(values);

        this.text.setText(this.values.getCurrentElement());
        textWrapper.add(this.text, BorderLayout.CENTER);

        this.leftArrow = buildArrow(VolatileTextButton.LEFT);
        this.rightArrow = buildArrow(VolatileTextButton.RIGHT);

        this.add(leftArrow, BorderLayout.WEST);
        this.add(textWrapper, BorderLayout.CENTER);
        this.add(rightArrow, BorderLayout.EAST);

        this.setOpaque(false);

    }

    public void setBold(boolean b) {
        this.text.setFont(b ? boldFont : baseFont);
    }

    public JButton buildArrow(int direction) {
        if (direction != 0 && direction != 1) {
            throw new IllegalArgumentException("Incorrect direction, expected LEFT (0) or RIGHT (1).");
        }

        JButton temp = new JButton() {
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

        boolean left = direction == 0;

        temp.setOpaque(false);

        temp.setIcon(new ImageIcon(left ? leftArrowSprite : rightArrowSprite));

        temp.addActionListener(l -> {
            if (!locked) {
                if (left) {
                    text.setText(values.getPreviousElement());
                } else {
                    text.setText(values.getNextElement());
                }
                WRAPPER.repaint();
                AudioReader.playSound("gn_button_click.wav");
            } else {
                AudioReader.playSound("no.wav");
            }
        });

        temp.setBorderPainted(false);
        temp.setFocusable(false);
        temp.setContentAreaFilled(false);

        return temp;

    }

    public void setLocked(boolean b) {
        this.locked = b;
    }
}
