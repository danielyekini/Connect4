package CPUPlayers;

import java.util.ArrayList;
import java.util.Random;

import GameMechanics.Grid;

public class CPUPerfect extends CPUPlayer{

    ArrayList<Integer> posMoves;
    Grid grid;

    int maxPlayer;

    public CPUPerfect(Grid grid, ArrayList<Integer> posMoves, int maxPlayer) {
        this.grid = grid;
        this.posMoves = new ArrayList<>(posMoves);
        this.maxPlayer = maxPlayer;
    }

    @Override
    public int play() {
        // if (player == 1) {
        //     Random rand = new Random();
        //     int pos = rand.nextInt(0, 7);
        //     return pos;
        // }
        return minimax(maxPlayer, grid.copy());
    }

    private int minimax(int maxPlayer, Grid state) {
        return 0;
    }
    
}
