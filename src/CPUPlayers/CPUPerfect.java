package CPUPlayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import GameMechanics.Grid;

public class CPUPerfect extends CPUPlayer{

    Grid grid;

    int maxPlayer;
    int oppPlayer;
    int bestMove;
    double inf = Double.POSITIVE_INFINITY;
    double highestEval = 0;

    public CPUPerfect(Grid grid, int maxPlayer) {
        this.grid = grid;
        this.maxPlayer = maxPlayer;
        this.oppPlayer = (maxPlayer == 1) ? 2 : 1;
    }

    @Override
    public int play() {
        // if (maxPlayer == 1) {
        //     Random rand = new Random();
        //     int pos = rand.nextInt(0, 7);
        //     return pos;
        // }
        Grid state = grid.copy();
        System.out.println(state.posMoves());
        minimax(0, maxPlayer, state, -inf, inf);
        System.out.println("bestMove: " + bestMove);
        return bestMove;
    }

    private double minimax(int depth, int currentPlayer, Grid state, double alpha, double beta) {
        //System.out.println(depth);
        if (state.checkWin() != -1) {
            int lastPlayer = (currentPlayer == 1) ? 2 : 1;
            double score = evaluate(lastPlayer, state);
            return score;
        }
        ArrayList<Integer> posMoves = state.posMoves();

        if (currentPlayer == maxPlayer) {
            double maxEval = -inf;
            for (int positionX : posMoves) {
                state.placePosition(positionX, maxPlayer);
                double evaluation = Math.max(maxEval, minimax(depth++, oppPlayer, state, alpha, beta));
                if (evaluation > maxEval) {
                    maxEval = evaluation;
                    if (evaluation > highestEval) {
                        highestEval = evaluation;
                        bestMove = positionX;
                    }
                }
                // System.out.println("maximizer position: " + positionX + " at depth " + depth + " has evaluation of " + maxEval);
                state.clearPosition(positionX);
                alpha = Math.max(alpha, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            // state.printGrid();
            return maxEval;
        } else {
            double minEval = inf;
            for (int positionX : posMoves) {
                state.placePosition(positionX, currentPlayer);
                double evaluation = Math.min(minEval, minimax(depth++, maxPlayer, state, alpha, beta));
                minEval = Math.min(minEval, evaluation);
                // System.out.println("minimizer position: " + positionX + " at depth " + depth + " has evaluation of " + minEval);
                state.clearPosition(positionX);
                beta = Math.min(beta, evaluation);
                if (beta <= alpha) {
                    break;
                }
            }
            // state.printGrid();
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
