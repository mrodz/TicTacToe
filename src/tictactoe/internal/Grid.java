package tictactoe.internal;

import tictactoe.Colors;
import tictactoe.components.GridBubble;

import javax.swing.JButton;

import java.util.Arrays;

import static tictactoe.TicTacToe.*;

/**
 * A wrapper class that contains a {@link Slot Slot[][]} array and a {@link GridBubble GridBubble[][]} array;
 * Internal game state.
 */
public class Grid implements PlayableField, Cloneable {
    public Slot[][] slots = new Slot[3][3];
    public GridBubble[][] backgroundBubbles;

    {
        setupSlots();
        setupBackgroundBubbles();
    }

    /** initialize {@link #backgroundBubbles} */
    private void setupBackgroundBubbles() {
        int ds = (int) (DECIDED_SIZE / 3.7);
        GridBubble[][] res = new GridBubble[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i][j] = new GridBubble(Colors.query("PLAYZONE_BUTTON_BACKGROUND"), Colors.query("PLAYZONE_BUTTON_BORDER"),
                        null, asInt(ds * 0.60), asInt(ds * 0.60), false, asInt(ds * 0.02));
            }
        }
        backgroundBubbles = res;
    }

    /** initialize {@link #slots} */
    protected void setupSlots() {
        for (int i = slots.length; i > 0; i--) {
            for (char j = 'A'; j <= 'C'; j++) {
                slots[slots.length - i][j - 'A'] = new Slot(new JButton(), AppConstants.BLANK_SLOT, slots.length - i, j - 'A');
                // slots[slots.length - i][j - 'A'].button.setText("" + j + i); see slot ids.
                slots[slots.length - i][j - 'A'].button.setName(String.format("Button%s", "" + j + i));
                slots[slots.length - i][j - 'A'].button.setText(" ");
            }
        }
    }

    /**
     * Use array indexes
     * @param p a valid {@link Placement}
     * @param newChar the replacement
     */
    public void updateMove(Placement p, char newChar) {
        slots[p.x][p.y].setSymbol(newChar);
        slots[p.x][p.y].getButton().setText(String.valueOf(newChar));
    }

    @Override
    public char[][] toCharArray() {
        char[][] res = new char[slots.length][slots[0].length];
        for (int i = 0; i < slots.length; i++) {
            for (int j = 0; j < slots[i].length; j++) {
                res[i][j] = slots[i][j].symbol;
            }
        }
        return res;
    }

    public Character[][] toCharacterArray() {
        Character[][] res = new Character[slots.length][slots[0].length];
        for (int i = 0; i < slots.length; i++) {
            for (int j = 0; j < slots[i].length; j++) {
                res[i][j] = slots[i][j].symbol;
            }
        }
        return res;
    }


    /**
     * Analyze a position to a key
     * @param g the position
     * @return an {@link AppConstants.GameKeys}
     */
    public static int analyzePositionToKey(PlayableField g) {
        if (analyzePosition(g, AppConstants.FIRST_MOVE) == GameState.PLAYING) {
            throw new IllegalArgumentException("Grid provided is still playing a round!");
        }

        char[][] tg = g.toCharArray();

        // diagonals
        if (tg[0][0] == tg[1][1] && tg[1][1] == tg[2][2]) {
            return AppConstants.GameKeys.DIAGONAL_1;
        }

        if (tg[2][0] == tg[1][1] && tg[1][1] == tg[0][2]) {
            return AppConstants.GameKeys.DIAGONAL_2;
        }

        // straights
        for (int i = 0; i < tg.length; i++) {
            // horizontal
            if (tg[i][0] == tg[i][1] && tg[i][1] == tg[i][2] && tg[i][1] != AppConstants.BLANK_SLOT) {
                return 3 + ++i;
            }

            // vertical
            if (tg[0][i] == tg[1][i] && tg[1][i] == tg[2][i] && tg[1][i] != AppConstants.BLANK_SLOT) {
                return ++i;
            }
        }

        // none apply
        return AppConstants.GameKeys.TIE_NONE;
    }

    /**
     * Analyze a position to a game state.
     * @param g the position
     * @param maximizingPlayer the player who is being weighed as a bias
     * @return a {@link GameState}
     */
    public static GameState analyzePosition(PlayableField g, char maximizingPlayer) {
        if (!(maximizingPlayer == AppConstants.FIRST_MOVE || maximizingPlayer == AppConstants.SECOND_MOVE)) {
            throw new IllegalArgumentException("Illegal player: '" + maximizingPlayer + "' (int: " + (int) maximizingPlayer + ')');
        }

        char[][] tg = g.toCharArray();
        char opp = maximizingPlayer == AppConstants.FIRST_MOVE ? AppConstants.SECOND_MOVE : AppConstants.FIRST_MOVE;

        // straights
        for (int i = 0; i < tg.length; i++) {
            if ((tg[i][0] == tg[i][1] && tg[i][1] == tg[i][2] && tg[i][1] == maximizingPlayer)
                    || (tg[0][i] == tg[1][i] && tg[1][i] == tg[2][i] && tg[1][i] == maximizingPlayer)) {
                return GameState.FIRST_WIN;
            } else if ((tg[i][0] == tg[i][1] && tg[i][1] == tg[i][2] && tg[i][1] == opp)
                    || (tg[0][i] == tg[1][i] && tg[1][i] == tg[2][i] && tg[1][i] == opp)) {
                return GameState.SECOND_WIN;
            }
        }

        // diagonals
        if (tg[1][1] != AppConstants.BLANK_SLOT && (tg[0][0] == tg[1][1] && tg[1][1] == tg[2][2] || tg[2][0] == tg[1][1] && tg[1][1] == tg[0][2])) {
            return tg[1][1] == maximizingPlayer ? GameState.FIRST_WIN : GameState.SECOND_WIN;
        }

        // no winner; check for tie.
        for (char[] arr : tg) {
            for (char c : arr) {
                if (c == AppConstants.BLANK_SLOT) {
                    return GameState.PLAYING;
                }
            }
        }

        // none apply
        return GameState.TIE;
    }

    @Override
    public String toString() {
        return String.format("Grid{slots='%s'}", Arrays.deepToString(Arrays.stream(this.toCharArray()).peek(n -> {
            for (int i = 0; i < n.length; i++) {
                n[i] = n[i] == AppConstants.BLANK_SLOT ? AppConstants.BLANK_SLOT_ALT : n[i];
            }
        }).toArray()));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
