package GameMechanics;



public class GameControl {
    
    public static int playedPosition = 0, player = 1, numTurns = 2;

    public static void start() {
        while (Grid.checkWin() == 0) {
            if (numTurns % 2 == 0) {
                Grid.printGrid();
                Grid.placePosition(Input.Integer("Select Position (1-5): "), player);
                numTurns--;
            } 
            else {
                Grid.printGrid();
                Grid.placePosition(CPUEasy.play(player), player);
                numTurns++;
            }
            
            player = (player==1) ? 2 : 1;
        }
    }
}
