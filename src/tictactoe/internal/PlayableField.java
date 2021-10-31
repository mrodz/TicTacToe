package tictactoe.internal;

/**
 * A playable field
 * @see Grid
 * @see tictactoe.internal.players.HardBot.Position
 */
public interface PlayableField {
    /** Override to get this fields' content as a {@code char[][]} array. */
    char[][] toCharArray();
}
