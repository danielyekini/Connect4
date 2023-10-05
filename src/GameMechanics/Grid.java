package GameMechanics;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    final int xSize = 7;
    final int ySize = 6;
    public int[] lastPlayed;
    ArrayList<Integer> posMoves = new ArrayList<>(xSize);
    private static int[][] gameGrid;

    public Grid() {
        gameGrid = new int[ySize][xSize];
        for (int i = 0; i < xSize; i++) {
            posMoves.add(i);
        }
    }

    public void printGrid() {
        System.out.println("\n");
        for (int[] line : gameGrid) {
            System.out.println(Arrays.toString(line));
        }
    }

    public int checkPosition(int x, int y) {
        return gameGrid[y][x];
    }

    public int placePosition(int position, int player) {
        lastPlayed = new int[2];
        int x = position;
        int y = 0;

        if (gameGrid[y][x] != 0) { return -1; }

        for (int yIndex = 5; yIndex > 0; yIndex--) {
            if (checkPosition(x, yIndex) == 0) {
                y = yIndex;
                break;
            }
        }

        if (y == 0) {posMoves.remove(x);}
        gameGrid[y][x] = player;
        lastPlayed[0] = y;
        lastPlayed[1] = x;
        return 1;
    }

    public ArrayList<Integer> getPosMoves() {
        return posMoves;
    }

    public int checkWin() {
        //checks for HORIZONTAL wins
        for (int y = 5; y >= 0; y--) { 
            for (int x = 0; x < 4; x++) {
                if (checkPosition(x, y)!=0 && checkPosition(x, y)==checkPosition(x+1, y) && checkPosition(x+1, y)==checkPosition(x+2, y) && checkPosition(x+2, y)==checkPosition(x+3, y)) {
                    return 1;
                }
            }
        }

        //checks for VERTICAL wins
        for (int x = 0; x < 7; x++) { 
            for (int y = 5; y > 2; y--) {
                if (checkPosition(x, y)!=0 && checkPosition(x, y)==checkPosition(x, y-1) && checkPosition(x, y-1)==checkPosition(x, y-2) && checkPosition(x, y-2)==checkPosition(x, y-3)) {
                    return 1;
                }
            }
        }

        //checks for DIAGONAL wins
        for (int x = 0; x < 4; x++) { 
            for (int y = 0; y < 3; y++) {
                if (checkPosition(x, y)!=0 && checkPosition(x, y)==checkPosition(x+1, y+1) && checkPosition(x+1, y+1)==checkPosition(x+2, y+2) && checkPosition(x+2, y+2)==checkPosition(x+3, y+3)) {
                    return 1;
                }
            }
        }
        for (int x = 6; x >= 3; x--) { 
            for (int y = 0; y < 3; y++) {
                if (checkPosition(x, y)!=0 && checkPosition(x, y)==checkPosition(x-1, y+1) && checkPosition(x-1, y+1)==checkPosition(x-2, y+2) && checkPosition(x-2, y+2)==checkPosition(x-3, y+3)) {
                    return 1;
                }
            }
        }

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (checkPosition(x, y) == 0) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
