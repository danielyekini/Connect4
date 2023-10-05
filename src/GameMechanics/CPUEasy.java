package GameMechanics;

import java.util.ArrayList;
import java.util.Random;

public class CPUEasy {

    private ArrayList<Integer> posMoves;

    public CPUEasy(ArrayList<Integer> posMoves) {
        this.posMoves = posMoves;
    }
    
    public int play(int player) {
        System.out.println("\n" + posMoves);
        Random rand = new Random();
        int pos;
        int n = rand.nextInt(posMoves.size());
        pos = posMoves.get(n);
        System.out.println("Position played: " + pos);
        return pos;
    }
}
