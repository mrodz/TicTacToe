package tictactoe.internal.players;

import tictactoe.internal.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/** Hard Bot */
public class HardBot implements Bot {
    private char me;
    private char opp;

    /**
     * A modified {@link Grid}, wrapper for a {@code char[][]} array and other important
     * details to consider in {@link #minimax(Position, boolean)}
     */
    public static class Position implements PlayableField {
        char[][] state;

        private Position(Character[][] arr) {
            char[][] chars = new char[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    chars[i][j] = arr[i][j];
                }
            }
            this.state = chars;
        }

        /**
         * <b>Gets every possible placement on a {@link Grid}</b>
         * @param position the <u>original</u> base grid to be analyzed
         * @param nextPlayer the player who's character will be placed in every available slot
         * @return an {@code array} of {@link Grid Grids} that contains every possible placement with a depth of 1.
         */
        private static Position[] getPossibleMoves(Position position, char nextPlayer) {
            java.util.ArrayList<Position> tg = new java.util.ArrayList<>();
            final Character[][] baseChars = position.toCharacterArray();
            for (int i = 0, c = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++, c++) {
                    if (baseChars[i][j] == AppConstants.BLANK_SLOT)
                        tg.add(new Position(copyMatrixWithNewPoint(baseChars, nextPlayer, i, j)));
                }
            }
            return tg.toArray(new Position[0]);
        }

        /**
         * Return a copied matrix with an updated slot at the specified coordinates.
         * @param original the original matrix to be copied.
         * @param element the element to be inserted.
         * @param x {@code original[x][?]}
         * @param y {@code original[?][y]}
         * @param <T> Both the return type and the type of the specified array + new element.
         * @return See description; used to get possible moves.
         */
        private static <T> T[][] copyMatrixWithNewPoint(final T[][] original, T element, int x, int y) {
            if (x >= original.length || y >= original[0].length || x < 0 || y < 0) {
                throw new IndexOutOfBoundsException(String
                        .format("Array keys too big: found (%d, %d), actual size (%d x %d)", x, y, original.length, original[0].length));
            }
            T[][] newArr = deepCopyArray(original);

            for (int i = 0; i < original.length; i++) {
                System.arraycopy(original[i], 0, newArr[i], 0, original[i].length);
            }

            newArr[x][y] = element;
            return newArr;
        }

        /**
         * Copy arrays and matrices
         * @param array The array to be copied.
         * @param <T> The array class and return type; should be inferred.
         * @return a deep copy of an array. This can only return a trully-deep copy if {@code T} is
         * a primitive wrapper.
         * @see #deepCopyArrayInternal(Object)
         */
        @SuppressWarnings("unchecked")
        private static <T> T[] deepCopyArray(final T[] array) {
            return (T[]) deepCopyArrayInternal(array);
        }

        /**
         * Recursive function to clone an array object.
         * @param array the array to be copied, as an {@link Object}
         * @return an {@link Object}, should be cast to fit the required type once called.
         */
        private static Object deepCopyArrayInternal(Object array) {
            int length = Array.getLength(array);
            Object copy = Array.newInstance(array.getClass().getComponentType(), length);
            for (int i = 0; i < length; i++) {
                Object val = Array.get(array, i);
                if (Objects.nonNull(val) && val.getClass().isArray()) {
                    val = deepCopyArrayInternal(val);
                }
                Array.set(copy, i, val);
            }
            return copy;
        }

        /**
         * Gets the characters of the grid and returns said value as a matrix.
         * @return the internal state of this position.
         */
        @Override
        public char[][] toCharArray() {
            char[][] res = new char[this.state.length][this.state[0].length];
            for (int i = 0; i < this.state.length; i++) {
                System.arraycopy(this.state[i], 0, res[i], 0, this.state[i].length);
            }
            return res;
        }

        /**
         * Same function as {@link #toCharArray()}, but the {@code chars} are cast as
         * {@link Character Characters} instead.
         * @see #toCharArray()
         */
        public Character[][] toCharacterArray() {
            Character[][] res = new Character[this.state.length][this.state[0].length];
            for (int i = 0; i < this.state.length; i++) {
                for (int j = 0; j < this.state[i].length; j++) {
                    res[i][j] = state[i][j];
                }
            }
            return res;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "state=" + Arrays.deepToString(state) +
                    '}';
        }

        public boolean isGameOver() {
            return Grid.analyzePosition(this, AppConstants.FIRST_MOVE) != GameState.PLAYING;
        }
    }

    /**
     * Implementation of the Minimax Algorithm, used to calculate the best possible position.
     * I give my thanks to Sebastian Lague for making a video that gives a great explanation
     * of this algorithm and its inner workings.
     * @param position the starting {@link Position} to be analyzed
     * @param isMaximizing is this algorithm <u>maximizing</u> or <u>minimizing</u>
     * @return the weight of an initial position.
     */
    private int minimax(Position position, boolean isMaximizing) {
        if (position.isGameOver()) {
            return analyzePosition(position);
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (Position g : Position.getPossibleMoves(position, this.me)) {
                int score = minimax(g, false);
                bestScore = Math.max(score, bestScore);
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Position g : Position.getPossibleMoves(position, this.opp)) {
                int score = minimax(g, true);
                bestScore = Math.min(score, bestScore);
            }
        }
        return bestScore;
    }

    /**
     * Assign a position a integer weight.
     * @see AppConstants#WIN_WEIGHT
     * @see AppConstants#LOSS_WEIGHT
     * @see AppConstants#TIE_WEIGHT
     */
    private int analyzePosition(Position p) {
        GameState gs = Grid.analyzePosition(p, this.me);
        switch (gs) {
            case PLAYING:
                throw new IllegalArgumentException("the board is still active");
            case FIRST_WIN:
                return AppConstants.WIN_WEIGHT;
            case SECOND_WIN:
                return AppConstants.LOSS_WEIGHT;
            default:
                return AppConstants.TIE_WEIGHT;
        }
    }

    /**
     * This method takes advantage of the Minimax algorithm to guarantee at least a tie.
     * It is important to note, too, that there is one optimization; if the hard bot has
     * the first move, then it will chose from one of five random placements that are
     * still guaranteed to create a viable outcome. This adds for more unique situations,
     * and skips calculating some 3,265,920 outcomes (Better speed + performance).
     * @return the best possible move to be made.
     * @see Bot#getPlacement()
     */
    @Override
    public Placement getPlacement() {
        if (Game.round.steps == 0) {
            Random r = new Random();
            switch (r.nextInt(5)) {
                case 0: return new Placement(0, 0);
                case 1: return new Placement(0, 2);
                case 2: return new Placement(1, 1);
                case 3: return new Placement(2, 2);
                case 4: return new Placement(2, 0);
                default: throw new IllegalStateException("RNG produced unexpected value");
            }
        } else {
            // set temporary player fields
            this.me = Game.round.turn;
            this.opp = me == AppConstants.FIRST_MOVE ? AppConstants.SECOND_MOVE : AppConstants.FIRST_MOVE;

            int bestScore = Integer.MIN_VALUE;
            int[] move = new int[2];
            Character[][] board = Game.master.toCharacterArray();
            for (int i = 0, c = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++, c++) {
                    if (board[i][j] == AppConstants.BLANK_SLOT) {
                        int score = minimax(new Position(Position.<Character>copyMatrixWithNewPoint(board, this.me, i, j)), false);
                        if (score > bestScore) {
                            bestScore = score;
                            move = new int[]{i, j};
                        }
                    }
                }
            }
            return new Placement(move);
        }
    }
}
