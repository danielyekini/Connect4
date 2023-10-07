package CPUPlayers;


import java.util.ArrayList;
import java.util.Random;

import GameMechanics.Grid;

public class CPUEasy extends CPUPlayer{
    
    Grid grid;
    CPUPlayer helper;
    private ArrayList<Integer> posMoves;
    int numMoves = 0;
    
    public CPUEasy(Grid grid, int maxPlayer ) {
        this.grid = grid;
        helper = new CPUPerfect(grid, maxPlayer);
    }

    @Override
    public int play() {
        if (numMoves > 10) {
            return helper.play();
        }

        posMoves = grid.posMoves();
        System.out.println("\n" + posMoves);
        Random rand = new Random();
        int pos;
        int n = rand.nextInt(posMoves.size());
        pos = posMoves.get(n);
        numMoves++;
        return pos;
    }
}
