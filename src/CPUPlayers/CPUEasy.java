package CPUPlayers;


import java.util.ArrayList;
import java.util.Random;

public class CPUEasy extends CPUPlayer{
    
    private ArrayList<Integer> posMoves;
    
    public CPUEasy(ArrayList<Integer> posMoves) {
        super(posMoves);
        this.posMoves = posMoves;
    }

    @Override
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