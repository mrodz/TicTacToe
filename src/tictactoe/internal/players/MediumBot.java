package tictactoe.internal.players;

import tictactoe.internal.AppConstants;
import tictactoe.internal.Game;
import tictactoe.internal.Placement;

/**
 * <p>
 *     Medium Bot
 * </p>
 * Code recycled from
 * <a href="https://github.com/mrodz/TicTacToe/blob/7c0b567f03b357e89ebf6696edc37684ae3baf07/src/TicTacToe.java#L519">
 *     an older project
 * </a>
 */
public class MediumBot implements Bot {
    enum Urgency {
        /**Immediate win*/
        DIRE_WIN(3),

        /**Immediate loss*/
        DIRE_LOSS(2),

        /**No pressure to do a special move.*/
        NOT_DIRE(1);

        /**Weight of the move, used to judge stuff*/
        public int weight;

        Urgency(int weight) {
            this.weight = weight;
        }
    }

    static class Move {
        private final Urgency urgency;

        private Move(Urgency urgency) {
            this.urgency = urgency;
        }

        private int getWeight() {
            return urgency.weight;
        }
    }

    private final Move[][] straights = new Move[2][Game.master.toCharArray().length];
    private final Move[] diagonals = new Move[2];

    /**
     * @see #straights
     * @see #diagonals
     */
    protected void initializeFields() {
        char[][] tg = Game.master.toCharArray();
        char symbol = Game.round.turn;
        for (int i = 0; i < tg.length; i++) {
            int[][] values = {
                    {0, 0},
                    {0, 0}
            };
            int[] blanks = {0, 0};
            for (int b = 0; b < tg[i].length; b++) {
                switch (tg[i][b]) {
                    case AppConstants.FIRST_MOVE : values[0][0]++; break;
                    case AppConstants.SECOND_MOVE: values[0][1]++; break;
                    case AppConstants.BLANK_SLOT : blanks[0]++; break;
                    default:
                        throw new IllegalStateException(String
                                .format("The character at grid index %d, %d is invalid ('%c')", i, b, tg[i][b]));
                }
                switch (tg[b][i]) {
                    case AppConstants.FIRST_MOVE : values[1][0]++; break;
                    case AppConstants.SECOND_MOVE: values[1][1]++; break;
                    case AppConstants.BLANK_SLOT : blanks[1]++; break;
                    default:
                        throw new IllegalStateException(String
                                .format("The character at grid index %d, %d is invalid ('%c')", b, i, tg[b][i]));
                }
            }

            straights[0][i] = new Move(
                    blanks[0] == 1 && ((values[0][0] == 2 && symbol == AppConstants.FIRST_MOVE) || (values[0][1] == 2 && symbol == AppConstants.SECOND_MOVE))
                            ? Urgency.DIRE_WIN
                            : blanks[0] == 1 && ((values[0][0] == 2 && symbol != AppConstants.FIRST_MOVE) || (values[0][1] == 2 && symbol != AppConstants.SECOND_MOVE))
                            ? Urgency.DIRE_LOSS
                            : Urgency.NOT_DIRE
            );

            straights[1][i] = new Move(
                    blanks[1] == 1 && ((values[1][0] == 2 && symbol == AppConstants.FIRST_MOVE) || (values[1][1] == 2 && symbol == AppConstants.SECOND_MOVE))
                            ? Urgency.DIRE_WIN
                            : blanks[1] == 1 && ((values[1][0] == 2 && symbol != AppConstants.FIRST_MOVE) || (values[1][1] == 2 && symbol != AppConstants.SECOND_MOVE))
                            ? Urgency.DIRE_LOSS
                            : Urgency.NOT_DIRE
            );
        }
        for (int i = 0; i < 2; i++) {
            int[][] values = {
                    {0, 0},
                    {0, 0}
            };
            int[] blanks = {0, 0};
            for (int b = 0, c = tg[b].length - 1; b < tg.length && c >= 0; b++, c--) {
                switch (tg[b][b]) {
                    case AppConstants.FIRST_MOVE : values[0][0]++; break;
                    case AppConstants.SECOND_MOVE: values[0][1]++; break;
                    case AppConstants.BLANK_SLOT : blanks[0]++; break;
                    default:
                        throw new IllegalStateException(String
                                .format("The character at grid index %d, %d is invalid ('%c')", b, b, tg[b][b]));
                }

                switch (tg[c][b]) {
                    case AppConstants.FIRST_MOVE : values[1][0]++; break;
                    case AppConstants.SECOND_MOVE: values[1][1]++; break;
                    case AppConstants.BLANK_SLOT : blanks[1]++; break;
                    default:
                        throw new IllegalStateException(String
                                .format("The character at grid index %d, %d is invalid ('%c')", c, b, tg[c][b]));
                }
            }
            diagonals[i] = new Move(
                    blanks[i] == 1 && ((values[i][0] == 2 && symbol == AppConstants.FIRST_MOVE) || (values[i][1] == 2 && symbol == AppConstants.SECOND_MOVE))
                            ? Urgency.DIRE_WIN
                            : blanks[i] == 1 && ((values[i][0] == 2 && symbol != AppConstants.FIRST_MOVE) || (values[i][1] == 2 && symbol != AppConstants.SECOND_MOVE))
                            ? Urgency.DIRE_LOSS
                            : Urgency.NOT_DIRE
            );
        }
    }

    /**
     * @return a value according to this logic: if the bot can win in one
     * move, it will do so; if the opponent can win in one move, it will
     * block this attempt; otherwise, it will return a random {@link Placement}
     * @see Bot#getPlacement()
     */
    @Override
    public Placement getPlacement() {
        Placement temp = getPlacement0();
        return new Placement(temp.x - 1, temp.y - 1);
    }

    private Placement getPlacement0() {
        char[][] tg = Game.master.toCharArray();
        initializeFields();

        //straights
        for (int i = 0; i < 6; i++) {
            boolean horizontal = i < 3;
            int tempI = horizontal ? i : i - 3;
            if (straights[horizontal ? 0 : 1][tempI].getWeight() == Urgency.DIRE_WIN.weight) {
                for (int x = 0; x < tg.length; x++) {
                    if (tg[horizontal ? tempI : x][horizontal ? x : tempI] == AppConstants.BLANK_SLOT) {
                        tempI++;
                        x++;
                        return new Placement(horizontal ? tempI : x, horizontal ? x : tempI);
                    }
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            boolean horizontal = i < 3;
            int tempI = horizontal ? i : i - 3;
            if (straights[horizontal ? 0 : 1][tempI].getWeight() == Urgency.DIRE_LOSS.weight) {
                for (int x = 0; x < tg.length; x++) {
                    if (tg[horizontal ? tempI : x][horizontal ? x : tempI] == AppConstants.BLANK_SLOT) {
                        tempI++;
                        x++;
                        return new Placement(horizontal ? tempI : x, horizontal ? x : tempI);
                    }
                }
            }
        }

        //diagonals
        for (int i = 0; i < 2; i++) {
            if (diagonals[i].getWeight() == Urgency.DIRE_WIN.weight) {
                for (int b = 0, c = tg[b].length - 1; b < tg.length && c >= 0; b++, c--) {
                    if (tg[i == 0 ? b : c][b] == AppConstants.BLANK_SLOT) {
                        c++;
                        b++;
                        return new Placement(i == 0 ? b : c, b);
                    }
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            if (diagonals[i].getWeight() == Urgency.DIRE_LOSS.weight) {
                for (int b = 0, c = tg[b].length - 1; b < tg.length && c >= 0; b++, c--) {
                    if (tg[i == 0 ? b : c][b] == AppConstants.BLANK_SLOT) {
                        c++;
                        b++;
                        return new Placement(i == 0 ? b : c, b);
                    }
                }
            }
        }

        //No placement detected:
        java.util.Random random = new java.util.Random();
        int savedX, savedY;
        do {
            savedX = random.nextInt(tg.length);
            savedY = random.nextInt(tg.length);
        } while (tg[savedX][savedY] != AppConstants.BLANK_SLOT);

        return new Placement(++savedX, ++savedY);
    }
}
