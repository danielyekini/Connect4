package CPUPlayers;

import java.util.ArrayList;

import GameMechanics.Grid;

public class CPUPerfect extends CPUPlayer{

    Grid grid;

    int maxPlayer;
    int oppPlayer;
    int bestMove;
    int depth = 8;
    int firstMoves = 3;
    double inf = Double.POSITIVE_INFINITY;

    public CPUPerfect(Grid grid, int maxPlayer) {
        this.grid = grid;
        this.maxPlayer = maxPlayer;
        if (maxPlayer == 1) {
            oppPlayer = 2;
            //firstPlayer = true;
        } else {
            oppPlayer = 1;
            //firstPlayer = false;
        }
    }

    @Override
    public int play() {
        if (firstMoves > 0) {
            firstMoves--;
            return 3;
        }
        Grid state = grid.copy();
        int numsLeft = state.numPositionsLeft();
        System.out.println(numsLeft);
        depth = (numsLeft % 4 == 0) ? depth+2 : 
                (numsLeft % 5 == 0) ? depth+5 : depth;
        minimax(depth, maxPlayer, state, -inf, inf);
        System.out.println("bestMove: " + bestMove);
        System.out.println("depth: " + depth);
        return bestMove;
    }

    private double minimax(int depth, int currentPlayer, Grid state, double alpha, double beta) {
        // System.out.println(depth);
        if (depth <= 0 || state.checkWin() != -1) {
            // System.out.println("depth: " + depth);
            int lastPlayer = (currentPlayer == 1) ? 2 : 1;
            double score = evaluate(lastPlayer, state);
            return score;
        }
        ArrayList<Integer> posMoves = state.posMoves();
        int potentialMove = 0;

        if (currentPlayer == maxPlayer) {
            double maxEval = -inf;
            for (int positionX : posMoves) {
                state.placePosition(positionX, maxPlayer);
                double evaluation = Math.max(maxEval, minimax(depth--, oppPlayer, state, alpha, beta));
                if (evaluation > maxEval) {
                    maxEval = evaluation;
                    potentialMove = positionX;
                }
                // System.out.println("maximizer position: " + positionX + " at depth " + depth + " has evaluation of " + maxEval);
                state.clearPosition(positionX);
                alpha = Math.max(alpha, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            bestMove = potentialMove;
            return maxEval;
        } else {
            double minEval = inf;
            for (int positionX : posMoves) {
                state.placePosition(positionX, currentPlayer);
                double evaluation = Math.min(minEval, minimax(depth--, maxPlayer, state, alpha, beta));
                minEval = Math.min(minEval, evaluation);
                // System.out.println("minimizer position: " + positionX + " at depth " + depth + " has evaluation of " + minEval);
                state.clearPosition(positionX);
                beta = Math.min(beta, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private double evaluate(int lastPlayer, Grid state) {
        int spaces = state.numPositionsLeft();
        int checkWin = state.checkWin();
        if (checkWin == 1) {
            double staticScore = (maxPlayer == lastPlayer) ? spaces + 1 : (-1 * spaces) -1;
            return staticScore;
        }
        return 0;
    }

    
}
