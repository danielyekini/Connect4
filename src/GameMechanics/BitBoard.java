package GameMechanics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bitboard representation of the Connect4 grid. Uses a 64-bit integer for each
 * player where bit <code>x + y*WIDTH</code> represents the cell at column x and
 * row y (bottom row is y=0).
 */
public class BitBoard {
    public static final int WIDTH = 7;
    public static final int HEIGHT = 6;

    private final long[] boards = new long[2]; // [0] for player1, [1] for player2
    private final int[] heights = new int[WIDTH];

    public BitBoard() {
    }

    /**
     * Construct a bitboard from the current {@link Grid} state.
     */
    public BitBoard(Grid grid) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                // Grid indexes from top; convert so that y=0 is bottom.
                int value = grid.checkPosition(x, HEIGHT - 1 - y);
                if (value != 0) {
                    play(x, value);
                }
            }
        }
    }

    /**
     * Plays a piece for the given player at the specified column.
     */
    public void play(int column, int player) {
        int idx = column + heights[column] * WIDTH;
        boards[player - 1] |= (1L << idx);
        heights[column]++;
    }

    /**
     * Removes the top piece from the given column for the specified player.
     */
    public void undo(int column, int player) {
        heights[column]--;
        int idx = column + heights[column] * WIDTH;
        boards[player - 1] &= ~(1L << idx);
    }

    /**
     * Returns true if the column is not full.
     */
    public boolean canPlay(int column) {
        return heights[column] < HEIGHT;
    }

    /**
     * All available moves ordered from the centre outwards for better move
     * ordering during search.
     */
    public List<Integer> orderedMoves() {
        List<Integer> moves = new ArrayList<>();
        for (int c = 0; c < WIDTH; c++) {
            if (canPlay(c)) {
                moves.add(c);
            }
        }
        // sort by distance from the centre column (3)
        Collections.sort(moves, (a, b) -> Integer.compare(Math.abs(3 - a), Math.abs(3 - b)));
        return moves;
    }

    /**
     * Check if the given player has a four-in-a-row on this board.
     */
    public boolean isWin(int player) {
        long b = boards[player - 1];
        // horizontal
        long m = b & (b >> 1);
        if ((m & (m >> 2)) != 0) {
            return true;
        }
        // vertical
        m = b & (b >> WIDTH);
        if ((m & (m >> 2 * WIDTH)) != 0) {
            return true;
        }
        // diagonal /
        m = b & (b >> (WIDTH - 1));
        if ((m & (m >> 2 * (WIDTH - 1))) != 0) {
            return true;
        }
        // diagonal \
        m = b & (b >> (WIDTH + 1));
        if ((m & (m >> 2 * (WIDTH + 1))) != 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if there are no legal moves left.
     */
    public boolean isDraw() {
        for (int c = 0; c < WIDTH; c++) {
            if (canPlay(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Unique key for transposition table: lower bits contain player 1 pieces and
     * upper bits player 2 pieces.
     */
    public long key() {
        return boards[0] | (boards[1] << (WIDTH * HEIGHT));
    }

    /**
     * Returns the occupant of the cell at (x, y): 0 for empty, 1 or 2 for players.
     */
    public int get(int x, int y) {
        int idx = x + y * WIDTH;
        if ((boards[0] & (1L << idx)) != 0) {
            return 1;
        }
        if ((boards[1] & (1L << idx)) != 0) {
            return 2;
        }
        return 0;
    }

    /**
     * Number of empty cells remaining on the board.
     */
    public int numEmpty() {
        int filled = 0;
        for (int c = 0; c < WIDTH; c++) {
            filled += heights[c];
        }
        return WIDTH * HEIGHT - filled;
    }
}
