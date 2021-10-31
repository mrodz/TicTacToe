package tictactoe.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores a bunch of app constants
 */
public class AppConstants {
    public static final char FIRST_MOVE     = 'X';
    public static final char SECOND_MOVE    = 'O';
    public static final char BLANK_SLOT     = ' ';
    public static final char BLANK_SLOT_ALT = '_';

    // See HardBot#minimax();
    public static final int WIN_WEIGHT  = +10;
    public static final int TIE_WEIGHT  = 0;
    public static final int LOSS_WEIGHT = -10;

    public static final String[] players = Messages.Players.getNames();

    /**
     * Set whether the try-catch all in the main method should print the stack trace if it encounters
     * an unexpected error.
     */
    public static final boolean STACK_TRACE_ON_EXCEPTION = true;

    /** @see tictactoe.components.PlayZone#paintWinningLine(Grid)  */
    public static class GameKeys {
        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>X</td><td>O</td><td>O</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>O</td><td>O</td><td>X</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>X</td><td>X</td><td>O</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int TIE_NONE = 0;

        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>X</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>X</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>X</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int VERTICAL_1 = 1;

        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>_</td><td>X</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>X</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>X</td><td>_</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int VERTICAL_2 = 2;

        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>X</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>X</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>X</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int VERTICAL_3 = 3;

        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>X</td><td>X</td><td>X</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int HORIZONTAL_1 = 4;

        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>X</td><td>X</td><td>X</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int HORIZONTAL_2 = 5;

        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>X</td><td>X</td><td>X</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int HORIZONTAL_3 = 6;

        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>X</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>X</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>X</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int DIAGONAL_1 = 7;
        /**
         * <p>
         * What does this key represent? -
         *     <table>
         *         <tr>
         *             <td>|</td><td>_</td><td>_</td><td>X</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>_</td><td>X</td><td>_</td><td>|</td>
         *         </tr>
         *         <tr>
         *             <td>|</td><td>X</td><td>_</td><td>_</td><td>|</td>
         *         </tr>
         *     </table>
         * </p>
         */
        public static final int DIAGONAL_2 = 8;
    }

    /** Holds {@link String}s that represent states. */
    public static class Messages {
        /** @see tictactoe.components.StatusBar */
        public static class Status {
            public static final String NOT_STARTED  = "Game hasn't started";
            public static final String RESET_BOARD  = "Board Reset!";
            public static final String GAME_STARTED = "Started the Game!";
            public static final String WINNER = "%c wins!";
            public static final String TIE = "Draw";
            public static final String PLAYING = "Game in progress, %c's turn";
        }

        /** @see tictactoe.components.ControlPanel */
        public static class PlayerDescriptions {
            public static final String USER = "Human Player - you control this";
            public static final String BOT1 = "Easy Bot - random placements";
            public static final String BOT2 = "Medium Bot - slightly smarter bot";
            public static final String BOT3 = "Hard Bot - challenging";
        }

        /** @see tictactoe.internal.players.Playable */
        public enum Players {
            USER("Human", PlayerDescriptions.USER),
            BOT1("Easy", PlayerDescriptions.BOT1),
            BOT2("Medium", PlayerDescriptions.BOT2),
            BOT3("Hard" /*"Robot"*/, PlayerDescriptions.BOT3);

            String name;
            String desc;
            Players(String name, String desc) {
                this.name = name;
                this.desc = desc;
            }

            @Override
            public String toString() {
                return name;
            }

            public static boolean exists(String forName) {
                Set<String> names = new HashSet<>(Arrays.asList(getNames()));
                return names.contains(forName);
            }

            public static String[] getNames() {
                Players[] ps = Players.values();
                String[] res = new String[ps.length];
                for (int i = 0; i < ps.length; i++) {
                    res[i] = ps[i].name;
                }
                return res;
            }
        }
    }


    /**
     * Similar to the native Python function, returns an array of integers ranging from 0 to {@code x} - 1,
     * and can be used to make {@code for} loops look cleaner.
     * <p>
     *     For example, you could turn:
     * </p>
     * <pre>
     *     for (int i = 0; i < 100; i++) { //... }
     * </pre>
     * <p>
     *     Into:
     * </p>
     * <pre>
     *     for (int i : range(100)) { //... }
     * </pre>
     * <p>
     *     It is important to note that since this method is essentially a wrapper for the
     *     basic {@code for} loop, then using the above snippet of code is slower than the
     *     normal implementation. This method undergoes a time complexity of <tt>O(n)</tt>,
     *     where <tt>n</tt> is the input size.
     * </p>
     * @param x the upper bound of the array, exclusive.
     * @return an array with a size of {@code x} filled with every input leading up to {@code x}.<br/>
     *         "{@code range(5) == {0, 1, 2, 3, 4}}"
     */
    public static int[] range(int x) {
        int[] arr = new int[x];
        for (int i = 0; i < x; i++)
            arr[i] = i;
        return arr;
    }
}
