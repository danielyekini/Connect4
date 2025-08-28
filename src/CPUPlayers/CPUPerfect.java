package CPUPlayers;

import java.util.HashMap;
import java.util.Map;

import GameMechanics.BitBoard;
import GameMechanics.Grid;

/**
 * Strong CPU player using a minimax search with alpha-beta pruning, bitboards,
 * transposition table and iterative deepening. The previous implementation
 * relied on a hack to play the centre moves; the enhanced engine is able to
 * calculate those by itself.
 */
public class CPUPerfect extends CPUPlayer {

    private final Grid grid;
    private final int maxPlayer;
    private final int oppPlayer;

    private int bestMove;
    private final int maxDepth = 8;
    private final double inf = Double.POSITIVE_INFINITY;

    private final Map<Long, TTEntry> table = new HashMap<>();
    private int searchDepth;

    public CPUPerfect(Grid grid, int maxPlayer) {
        this.grid = grid;
        this.maxPlayer = maxPlayer;
        this.oppPlayer = (maxPlayer == 1) ? 2 : 1;
    }

    @Override
    public int play() {
        BitBoard state = new BitBoard(grid);
        table.clear();
        bestMove = -1; // no default column

        for (int depth = 1; depth <= maxDepth; depth++) {
            searchDepth = depth;
            minimax(state, depth, -inf, inf, maxPlayer);
        }

        // fall back to the first legal move if search fails
        if (bestMove == -1) {
            for (int move : state.orderedMoves()) {
                bestMove = move;
                break;
            }
        }
        return bestMove;
    }

    private double minimax(BitBoard board, int depth, double alpha, double beta, int currentPlayer) {
        long key = board.key();
        TTEntry entry = table.get(key);
        if (entry != null && entry.depth >= depth) {
            return entry.value;
        }

        if (depth == 0 || board.isWin(maxPlayer) || board.isWin(oppPlayer) || board.isDraw()) {
            double eval = evaluate(board);
            table.put(key, new TTEntry(eval, depth));
            return eval;
        }

        if (currentPlayer == maxPlayer) {
            double maxEval = -inf;
            for (int move : board.orderedMoves()) {
                board.play(move, currentPlayer);
                double evaluation = minimax(board, depth - 1, alpha, beta, oppPlayer);
                board.undo(move, currentPlayer);
                if (evaluation > maxEval) {
                    maxEval = evaluation;
                    if (depth == searchDepth) {
                        bestMove = move;
                    }
                }
                alpha = Math.max(alpha, evaluation);
                if (alpha >= beta) {
                    break; // beta cut-off
                }
            }
            table.put(key, new TTEntry(maxEval, depth));
            return maxEval;
        } else {
            double minEval = inf;
            for (int move : board.orderedMoves()) {
                board.play(move, currentPlayer);
                double evaluation = minimax(board, depth - 1, alpha, beta, maxPlayer);
                board.undo(move, currentPlayer);
                if (evaluation < minEval) {
                    minEval = evaluation;
                }
                beta = Math.min(beta, evaluation);
                if (alpha >= beta) {
                    break; // alpha cut-off
                }
            }
            table.put(key, new TTEntry(minEval, depth));
            return minEval;
        }
    }

    private double evaluate(BitBoard board) {
        int empty = board.numEmpty();
        if (board.isWin(maxPlayer)) {
            return 1000 + empty; // reward quicker wins
        }
        if (board.isWin(oppPlayer)) {
            return -1000 - empty; // penalise quicker losses
        }

        // positional heuristic for non-terminal states
        return evaluateBoard(board, maxPlayer) - evaluateBoard(board, oppPlayer);
    }

    /**
     * Evaluate non-terminal positions for the given player.
     * Rewards windows that contain only the player's pieces and empty cells.
     */
    private int evaluateBoard(BitBoard board, int player) {
        int score = 0;

        // encourage control of the centre column
        int centre = BitBoard.WIDTH / 2;
        for (int y = 0; y < BitBoard.HEIGHT; y++) {
            if (board.get(centre, y) == player) {
                score += 3;
            }
        }

        // horizontal windows
        for (int y = 0; y < BitBoard.HEIGHT; y++) {
            for (int x = 0; x < BitBoard.WIDTH - 3; x++) {
                score += evaluateWindow(board, player, x, y, 1, 0);
            }
        }
        // vertical windows
        for (int x = 0; x < BitBoard.WIDTH; x++) {
            for (int y = 0; y < BitBoard.HEIGHT - 3; y++) {
                score += evaluateWindow(board, player, x, y, 0, 1);
            }
        }
        // positive slope diagonals
        for (int x = 0; x < BitBoard.WIDTH - 3; x++) {
            for (int y = 0; y < BitBoard.HEIGHT - 3; y++) {
                score += evaluateWindow(board, player, x, y, 1, 1);
            }
        }
        // negative slope diagonals
        for (int x = 0; x < BitBoard.WIDTH - 3; x++) {
            for (int y = 3; y < BitBoard.HEIGHT; y++) {
                score += evaluateWindow(board, player, x, y, 1, -1);
            }
        }
        return score;
    }

    private int evaluateWindow(BitBoard board, int player, int startX, int startY, int dx, int dy) {
        int countPlayer = 0;
        int countOpp = 0;
        for (int i = 0; i < 4; i++) {
            int cell = board.get(startX + i * dx, startY + i * dy);
            if (cell == player) {
                countPlayer++;
            } else if (cell != 0) {
                countOpp++;
            }
        }
        if (countOpp == 0 && countPlayer > 0) {
            switch (countPlayer) {
                case 1:
                    return 1;
                case 2:
                    return 5;
                case 3:
                    return 50;
                default:
                    return 0;
            }
        }
        return 0;
    }

    private static class TTEntry {
        double value;
        int depth;

        TTEntry(double value, int depth) {
            this.value = value;
            this.depth = depth;
        }
    }
}