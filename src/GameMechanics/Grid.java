package GameMechanics;
import java.util.Arrays;

public class Grid {

    private static int[][] gameGrid = new int[6][7];

    public static void printGrid() {
        System.out.println("\n");
        for (int[] line : gameGrid) {
            System.out.println(Arrays.toString(line));
        }
    }

    protected static int checkPosition(int n, int m) {
        return gameGrid[n][m];
    }

    protected static int checkPosition(int position) {
        int n = (position <= 7) ? 0
        : (position <= 14) ? 1
        : (position <= 21) ? 2
        : (position <= 28) ? 3
        : (position <= 35) ? 4 : 5;  

        int m = ((position-1)%7==0) ? 0
        : ((position-2)%7==0) ? 1
        : ((position-3)%7==0) ? 2
        : ((position-4)%7==0) ? 3
        : ((position-5)%7==0) ? 4
        : ((position-6)%7==0) ? 5 : 6;

        return gameGrid[n][m];
    }

    public static void placePosition(int position, int player) {
        int m = position-1;

        int n = 0;
        for (int i = 5; i > 0; i--) {
            if (checkPosition(i, m)==0) {
                n=i;
                break;
            }
        }

        // int n = (position <= 7) ? 0
        // : (position <= 14) ? 1
        // : (position <= 21) ? 2
        // : (position <= 28) ? 3
        // : (position <= 35) ? 4 : 5;  

        // int m = ((position-1)%7==0) ? 0
        // : ((position-2)%7==0) ? 1
        // : ((position-3)%7==0) ? 2
        // : ((position-4)%7==0) ? 3
        // : ((position-5)%7==0) ? 4
        // : ((position-6)%7==0) ? 5 : 6;

        gameGrid[n][m] = player;
    }

    public static int checkWin() {
        int winValue = 0;
        //checks for HORIZONTAL wins
        for (int n = 5; n >= 0; n--) { 
            for (int m = 0; m < 4; m++) {
                if (checkPosition(n, m)!=0 && checkPosition(n, m)==checkPosition(n, m+1) && checkPosition(n, m+1)==checkPosition(n, m+2) && checkPosition(n, m+2)==checkPosition(n, m+3)) {
                    winValue = 1;
                    break;
                }
            }
        }

        //checks for VERTICAL wins
        for (int m = 0; m < 7; m++) { 
            for (int n = 5; n > 2; n--) {
                if (checkPosition(n, m)!=0 && checkPosition(n, m)==checkPosition(n-1, m) && checkPosition(n-1, m)==checkPosition(n-2, m) && checkPosition(n-2, m)==checkPosition(n-3, m)) {
                    winValue = 1;
                    break;
                }
            }
        }

        //checks for DIAGONAL wins
        for (int m = 0; m < 4; m++) { 
            for (int n = 0; n < 3; n++) {
                if (checkPosition(n, m)!=0 && checkPosition(n, m)==checkPosition(n+1, m+1) && checkPosition(n+1, m+1)==checkPosition(n+2, m+2) && checkPosition(n+2, m+2)==checkPosition(n+3, m+3)) {
                    winValue = 1;
                    break;
                }
            }
        }
        for (int m = 6; m >= 3; m--) { 
            for (int n = 0; n < 3; n++) {
                if (checkPosition(n, m)!=0 && checkPosition(n, m)==checkPosition(n+1, m-1) && checkPosition(n+1, m-1)==checkPosition(n+2, m-2) && checkPosition(n+2, m-2)==checkPosition(n+3, m-3)) {
                    winValue = 1;
                    break;
                }
            }
        }
        return winValue;
    }
}
