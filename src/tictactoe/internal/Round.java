package tictactoe.internal;

import external.AudioReader;

import tictactoe.TicTacToe;
import tictactoe.components.StatusBar;
import tictactoe.internal.players.Bot;
import tictactoe.internal.players.Human;
import tictactoe.internal.players.Playable;

/**
 * This represents one single game.
 */
public class Round {
    /** How many moves have been made */
    public volatile int steps = 0;
    /**
     * Who's turn is it
     * @see AppConstants#FIRST_MOVE
     * @see AppConstants#SECOND_MOVE
     */
    public volatile char turn = AppConstants.FIRST_MOVE;
    /**
     * Once somebody has won, set the winner here.
     */
    public volatile Character winner;

    private final Playable firstPlayer;
    private final Playable secondPlayer;

    /** Has someone won? */
    private volatile boolean won;

    /**
     * @see #play()
     * @see #Round(Playable, Playable, Grid)
     * @see Human#getPlacement()
     */
    public volatile Thread t;

    public <P1 extends Playable, P2 extends Playable>Round(P1 firstPlayer, P2 secondPlayer, Grid grid) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        t = new Thread(() -> {
             Game.playing = true;

             //Main game loop
             while (Grid.analyzePosition(grid, this.turn) == GameState.PLAYING) {
                 boolean first = this.steps % 2 == 0;
                 Placement p = null;

                 try {
                     p = (first ? firstPlayer : secondPlayer).getPlacement();
                 } catch (Exception ignored) {
                 }

                 if (p != null) {
                     grid.updateMove(p, this.turn);
                     incSteps();

                     GameState gs = Grid.analyzePosition(Game.master, AppConstants.FIRST_MOVE);

                     String msg = gs == GameState.FIRST_WIN
                             ? String.format(AppConstants.Messages.Status.WINNER, AppConstants.FIRST_MOVE)
                             : gs == GameState.SECOND_WIN
                             ? String.format(AppConstants.Messages.Status.WINNER, AppConstants.SECOND_MOVE)
                             : gs == GameState.TIE
                             ? AppConstants.Messages.Status.TIE
                             : AppConstants.Messages.Status.PLAYING;

                     StatusBar.updateText(msg);

                     if (gs != GameState.PLAYING) {
                         TicTacToe.PLAY_ZONE.paintWinningLine(Game.master);
                         try {
                             String win  = "win.wav";
                             String loss = "loss.wav";

                             boolean bothBots = (firstPlayer instanceof Bot && secondPlayer instanceof Bot);

                             boolean roundWithBot = ((firstPlayer instanceof Human && secondPlayer instanceof Bot)
                                     || (firstPlayer instanceof Bot && secondPlayer instanceof Human)) ^ bothBots;

                             AudioReader.playSound(
                                     winner == null ? loss : bothBots
                                             ? win : roundWithBot
                                                    ? winner == AppConstants.FIRST_MOVE && firstPlayer instanceof Human
                                                            ? win : winner == AppConstants.SECOND_MOVE && secondPlayer instanceof Human
                                                                    ? win : loss
                                                    : win
                             );
                         } finally {
                             setLocked(true, true);

                             this.won = true;
                             if (gs == GameState.FIRST_WIN || gs == GameState.SECOND_WIN) {
                                 TicTacToe.PLAY_ZONE.paintWinningLine(Game.master);
                             }
                         }
                     }
                 } else {
                     break;
                 }
             }

             // close the game state; implementations that read human input
             // need to unlock the field first to allow clicks.
             TicTacToe.PLAY_ZONE.lock(true);
             Game.playing = false;
        });
    }

    /** Start a round of TicTacToe */
    public synchronized void play() {
        try {
            t.start();
        } catch (IllegalThreadStateException ignored) { }
    }

    /**
     * Increment the steps, and set {@link #turn} to the corresponding move.
     */
    private synchronized void incSteps() {
        turn = ++this.steps % 2 == 0 ? AppConstants.FIRST_MOVE : AppConstants.SECOND_MOVE;
    }

    /** @see TicTacToe#PLAY_ZONE */
    public synchronized void setLocked(boolean b, boolean override) {
        TicTacToe.PLAY_ZONE.lock(override || b ^ isWon());
    }

    public synchronized boolean isWon() {
        return this.won;
    }

    public synchronized Playable getFirstPlayer() {
        return firstPlayer;
    }

    public synchronized Playable getSecondPlayer() {
        return secondPlayer;
    }
}
