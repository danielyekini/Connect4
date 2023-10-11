package CPUPlayers;


import java.util.ArrayList;
import java.util.Random;

import GameMechanics.Grid;

public class CPUEasy extends CPUPlayer{
    
    Grid grid;
    private ArrayList<Integer> posMoves;
    
    public CPUEasy(Grid grid) {
        this.grid = grid;
    }

    @Override
    public int play() {
        posMoves = grid.posMoves();
        System.out.println("\n" + posMoves);
        Random rand = new Random();
        int pos;
        int n = rand.nextInt(posMoves.size());
        pos = posMoves.get(n);
        return pos;
    }
}
