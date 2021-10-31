package tictactoe.components;

import tictactoe.Bubble;
import tictactoe.Colors;

import java.awt.Color;
import java.awt.Graphics;

import java.io.Serializable;

/**
 * Used to draw pretty shapes behind the grid bubbles.
 * @see Bubble
 */
public class GridBubble extends Bubble implements Serializable {
    private boolean won = false;
    private boolean hovering = false;
    private boolean clicking = false;
    private boolean locked = false;

    /**
     *
     * @param fill the colored portion inside of the box
     * @param border the border around the box
     * @param background a color to paint the entire background of the component.
     * @param x length of the bubble
     * @param y height of the bubble
     * @param paintBG paint the background with the color specified in the {@code background} parameter?
     * @param thickness thickness of the border.
     */
    public GridBubble(Color fill, Color border, Color background, int x, int y, boolean paintBG, int thickness) {
        super(fill, border, background, x, y, paintBG, thickness);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.won && this.hovering) {
            super.fill = Colors.query("WON_TILE_HOVER");
        } else if (this.won) {
            super.fill = Colors.query("WON_TILE");
        } else if (this.locked) {
            super.fill = Colors.query("PLAYZONE_BUTTON_BACKGROUND_LOCKED");
        } else if (this.clicking && this.hovering) {
            super.fill = Colors.query("PLAYZONE_BUTTON_BACKGROUND_MIXED");
        } else if (this.clicking) {
            super.fill = Colors.query("PLAYZONE_BUTTON_BACKGROUND_CLICKED");
        } else if (this.hovering) {
            super.fill = Colors.query("PLAYZONE_BUTTON_BACKGROUND_HOVER");
        } else {
            super.fill = Colors.query("PLAYZONE_BUTTON_BACKGROUND");
        }

        if (this.hovering) {
            super.border = Colors.query("PLAYZONE_BUTTON_BORDER_HOVER");
        } else {
            super.border = Colors.query("PLAYZONE_BUTTON_BORDER");
        }
        super.paintComponent(g);
    }

    public void resetAll() {
        PlayZone.locked = true;
        PlayZone.won = false;
        this.hovering = false;
        this.clicking = false;
        this.locked = false;
        this.won = false;
        this.background = Colors.query("PLAYZONE_BUTTON_BACKGROUND");
        this.border = Colors.query("PLAYZONE_BUTTON_BORDER");
        this.repaint();
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public void setClicking(boolean clicking) {
        this.clicking = clicking;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean hasWon() {
        return this.won;
    }

    public boolean isLocked() {
        return this.locked;
    }
}
