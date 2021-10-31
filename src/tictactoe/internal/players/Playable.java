package tictactoe.internal.players;

import tictactoe.internal.AppConstants;
import tictactoe.internal.Placement;

/**
 * Default interface that every 'player' inherits from.
 * Must override the {@link #getPlacement()} method.
 * @see Bot
 * @see EasyBot
 * @see MediumBot
 * @see HardBot
 * @see Human
 */
public interface Playable {
    /**
     * Implement the logic necessary to get the placement associated with each specific 'player.'
     * @return a {@link Placement} created, hopefully the product of some logic.
     */
    Placement getPlacement();

    static Playable forName(String name) {
        if (!AppConstants.Messages.Players.exists(name)) {
            throw new IllegalArgumentException("could not find player: " + name);
        }

        if (name.equals(AppConstants.Messages.Players.USER.toString())) {
            return new Human();
        } else if (name.equals(AppConstants.Messages.Players.BOT1.toString())) {
            return new EasyBot();
        } else if (name.equals(AppConstants.Messages.Players.BOT2.toString())) {
            return new MediumBot();
        } else if (name.equals(AppConstants.Messages.Players.BOT3.toString())) {
            return new HardBot();
        }

        throw new IllegalStateException("error finding player: " + name);
    }
}