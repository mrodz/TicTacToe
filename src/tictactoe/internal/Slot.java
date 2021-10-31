package tictactoe.internal;

import javax.swing.JButton;

/**
 * Saves an x & y coordinate, along with bundling a {@link JButton} with a {@code char} symbol.
 * This is a low level class.
 * @see Grid
 * @see Grid#setupSlots()
 */
public class Slot {
    protected final JButton button;
    protected char symbol;
    public int x;
    public int y;

    public Slot(JButton button, char symbol, int x, int y) {
        this.button = button;
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    public JButton getButton() {
        return button;
    }

    public char getSymbol() {
        return this.symbol;
    }

    protected void setSymbol(char symbol) {
        this.symbol = symbol;
        this.button.setText(String.valueOf(symbol));
    }

    @Override
    public String toString() {
        return "Slot{" +
                "symbol='" + symbol +
                "', x=" + x +
                ", y=" + y +
                '}';
    }
}
