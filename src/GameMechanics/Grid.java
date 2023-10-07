package GameMechanics;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    final int xSize = 7;
    final int ySize = 6;
    public int[] lastPlayed = new int[2];
    private int[][] gameGrid;

    public Grid() {
        gameGrid = new int[ySize][xSize];
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

    private int[] getPositions(int position) {
        int x = position;
        int y = 0;
        
        for (int yIndex = 5; yIndex > 0; yIndex--) {
            if (checkPosition(x, yIndex) == 0) {
                y = yIndex;
                break;
            }
        }

        int[] positions = {x, y};

        return positions;
    }

    public int placePosition(int position, int player) {
        if (gameGrid[0][position] != 0) { return -1; }

        int[] positions = getPositions(position);

        int x = lastPlayed[1] = positions[0];
        int y = lastPlayed[0] = positions[1];

        for (int yIndex = 5; yIndex > 0; yIndex--) {
            if (checkPosition(x, yIndex) == 0) {
                y = yIndex;
                break;
            }
        }

        gameGrid[y][x] = player;
        return 1;
    }

    public void clearPosition(int positionX) {
        int x = positionX;
        
        for (int y = 0; y < ySize; y++) {
            if (checkPosition(x, y) != 0) {
                gameGrid[y][x] = 0;
                return;
            }
        }
    }

    public int numPositionsLeft () {
        int count = 0;
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (gameGrid[y][x] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public ArrayList<Integer> posMoves() {
        ArrayList<Integer> posMoves = new ArrayList<>();
        for (int x = 0; x < xSize; x++) {
            if (gameGrid[0][x] == 0) {
                posMoves.add(x);
            }
        }
        return posMoves;
    }

    public Grid copy() {
        Grid copyGrid = new Grid();
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                copyGrid.gameGrid[y][x] = this.gameGrid[y][x];
            }
        }
        return copyGrid;
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

        //checks for Draw
        if (posMoves().size() == 0) {
            return 0;
        }
        return -1;
    }
}
