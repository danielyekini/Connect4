package GameMechanics;

import java.util.ArrayList;
import java.util.Random;

public class CPUEasy {
    
    public static int play(int player) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        positions.add(1);positions.add(2);positions.add(3);positions.add(4);positions.add(5);positions.add(6);
    
        Random rand = new Random();
        int pos;
        int n = rand.nextInt(positions.size());
        pos = positions.get(n);
        return pos;
    }
}
