package CPUPlayers;

import java.util.ArrayList;

/**
 * CPUPlayer
 */
public abstract class CPUPlayer {

    private ArrayList<Integer> posMoves;

    public CPUPlayer(ArrayList<Integer> posMoves) {
        this.posMoves = posMoves;
    }

    public abstract int play(int player);


}