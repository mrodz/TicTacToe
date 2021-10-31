package tictactoe.internal;

/** All possible game states. */
public enum GameState {
    /** The game is still being played */
    PLAYING,
    /** There is no winner */
    TIE,
    /** Playable 1 (ie. {@link AppConstants#FIRST_MOVE}) won */
    FIRST_WIN,
    /** Playable 2 (ie. {@link AppConstants#SECOND_MOVE}) won */
    SECOND_WIN
}
