package tictactoe.internal;

import tictactoe.TicTacToe;
import tictactoe.components.PlayZone;
import tictactoe.components.StatusBar;

import tictactoe.internal.players.Human;
import tictactoe.internal.players.Playable;

import static tictactoe.internal.AppConstants.range;

/** Master Class, contains all of the game's internal workings. */
public class Game {
    public static volatile Grid master = new Grid();
    /** update the constructor parameters to manually set the players of the game. */
    public static volatile Round round = new Round(new Human(), new Human(), master);

    public static volatile boolean playing = false;

    /**
     * Update the game's players
     * @param player1 new player to be set as Player1
     * @param player2 new player to be set as Player2
     * @param <P1> {@link Playable}
     * @param <P2> {@link Playable}
     */
    public static synchronized <P1 extends Playable, P2 extends Playable>void updatePlayers(P1 player1, P2 player2) {
        round = new Round(player1, player2, master);
    }

    /**
     * Wipe the board of all inputs.
     */
    public static synchronized void resetBoard() {
        round.t.interrupt();
        TicTacToe.PLAY_ZONE.initializeButtons();
        TicTacToe.PLAY_ZONE.lock(true);
        PlayZone.move = false;
        Human.emptyQueue();

        for (int i : range(Game.master.slots.length)) {
            for (int j : range(Game.master.slots.length)) {
                Game.master.updateMove(new Placement(i, j), AppConstants.BLANK_SLOT);
                Game.master.backgroundBubbles[i][j].resetAll();
            }
        }

        StatusBar.updateText(StatusBar.BASE_TEXT);

        Game.updatePlayers(Game.round.getFirstPlayer(), Game.round.getSecondPlayer());
    }
}
