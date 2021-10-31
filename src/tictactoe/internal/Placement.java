package tictactoe.internal;

import java.io.Serializable;

/**
 * Wrapper for two values: x & y.
 */
public class Placement implements Serializable {
    public int x;
    public int y;

    public Placement(int x, int y) {
        this(new int[] {x, y});
    }

    public Placement(int... arr) {
        if (arr.length != 2) {
            throw new IllegalArgumentException("Placement requires two parameters (x, y), found " + arr.length);
        }
        this.x = arr[0];
        this.y = arr[1];
    }

    @Override
    public String toString() {
        return "Placement{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * @return {@code true} if x and y fit within the constraints of 0 (inclusive) to 3 (exclusive); else, {@code false}
     */
    public boolean isValidPlacement() {
        return this.x >= 0 && this.x < 3 && this.y >= 0 && this.y < 3;
    }
}
