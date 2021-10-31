package tictactoe.internal.players;

import tictactoe.TicTacToe;
import tictactoe.internal.Game;
import tictactoe.internal.Placement;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Human Implementation
 */
public class Human implements Playable {
    public static final Object syncObject = new Object();
    /**
     * A queue to process all human inputs.
     * @see ArrayDeque#poll()
     */
    private static final Queue<Placement> placementQueue = new ArrayDeque<>();

    /**
     * Waits for user input, locking on ({@link #syncObject}), and returns said value.
     * @return the input from a human user.
     * @see tictactoe.components.PlayZone Playzone
     * @see Playable#getPlacement()
     */
    @Override
    public Placement getPlacement() {
        TicTacToe.playzone.lock(Game.round.isWon());

        // This thread will sleep until a response (placement)
        // is detected in any PlayZone cell.
        synchronized (Human.syncObject) {
            try {
                syncObject.wait();
            } catch (InterruptedException ignored) { }
        }

        Placement value = placementQueue.poll();

        // Debugging and error checks
        if (value == null) {
            Game.round.t.interrupt();
            throw new IllegalStateException("Empty user placement queue!");
        }

        if (!value.isValidPlacement()) {
            throw new IllegalStateException("Invalid placement; too large values");
        }

        return value;
    }

    public static synchronized void addPlacementToQueue(Placement p) {
        placementQueue.add(p);
    }

    public static synchronized void emptyQueue() {
        placementQueue.clear();
    }
}
