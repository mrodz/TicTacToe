package tictactoe.internal.players;

import tictactoe.internal.AppConstants;
import tictactoe.internal.Game;
import tictactoe.internal.Placement;

import java.util.Random;

/**
 * Easy Bot
 */
public class EasyBot implements Bot {
    /**
     * @return a {@link Placement} consisting of random inputs.
     * @see Bot#getPlacement()
     */
    @Override
    public Placement getPlacement() {
        char[][] arr = Game.master.toCharArray();
        Random r = new Random();
        int[] placements = new int[2];

        do {
            placements[0] = r.nextInt(Game.master.slots.length);
            placements[1] = r.nextInt(Game.master.slots.length);
        } while (arr[placements[0]][placements[1]] != AppConstants.BLANK_SLOT);

        return new Placement(placements[0], placements[1]);
    }
}
