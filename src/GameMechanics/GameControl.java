package GameMechanics;

import javax.swing.JOptionPane;

import CPUPlayers.*;
import GameGUI.GUI;

public class GameControl {
    
    Grid grid;
    GUI game;
    CPUPlayer cpu;
    int userPlayer, cpuPlayer;

    private boolean newGame = false;
    
    public int currentPlayer;

    public void start() {
        currentPlayer = 1;
        grid = new Grid();
        //int difficulty = pickDifficulty();
        userPlayer = pickPlayer() + 1;
        cpuPlayer = (userPlayer == 1) ? 2 : 1;
        //cpu = (difficulty == 0) ? new CPUEasy(grid.posMoves()) : new CPUPerfect(grid, cpuPlayer);
        cpu = new CPUEasy(grid, cpuPlayer);
        game = new GUI(this, grid, userPlayer);
        if (userPlayer == 2) {
            cpuMove();
        }
    }

    public int pickPlayer() {
        String[] options = {"Player 1 (Red)", "Player 2 (Yellow)"};
        int choice = JOptionPane.showOptionDialog(
            null, "Choose Player", "Choose Player", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        return choice;
    }

    public int pickDifficulty() {
        String[] options = {"Easy", "Hard"};
        int choice = JOptionPane.showOptionDialog(
            null, "Pick Difficulty", "Pick Difficulty", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        return choice;
    }

    public void cpuMove() {
        placePosition(cpu.play(), currentPlayer);
    }

    public int placePosition(int position, int player) {
        if (grid.placePosition(position, player) == 1) {
            game.updateBoard();
            grid.printGrid();
            checkWin();
            if (newGame) {
                newGame = false;
                return 0;
            }
            changePlayer();
            return 1;
        }
        return -1;
    }

    private void changePlayer() {
        currentPlayer = (currentPlayer==1) ? 2 : 1;
        game.changeCurrentPlayer(currentPlayer);
    }

    private void checkWin() {
        int checkWin = grid.checkWin();
        if (checkWin == 1) {
            game.showWon();
        } else if (checkWin == 0) {
            game.showDraw();
        }
    }

    public void newGame() {
        newGame = true;
        grid = null;
        cpu = null;
        game = null;
        start();
    }



    
    // sets a place to current player
    // if (controller.set_and_check(a, y, currentPlayer)) {
    //     hasWon = true;
    // } else if (controller.draw_game()) {//checks for drawgame
    //     hasDraw = true;
    // } else {
    //     //change player
    //     currentPlayer = grid.changeplayer(currentPlayer, 2);
    //     frame.setTitle("connect four - player " + currentPlayer + "'s turn");
    // }

    // public void start() {
    //     currentPlayer = 1;
    //     grid = new Grid();

    //     int difficulty = Input.Integer("Choose difficulty 1(easy) 2(hard): ");
    //     int userPlayer = Input.Integer("Choose player 1/2: ");
    //     int cpuPlayer = (userPlayer == 1) ? 2 : 1;
    //     cpu = (difficulty == 1) ? new CPUEasy(grid.posMoves()) : new CPUPerfect(grid, cpuPlayer);
        
    //     while (grid.checkWin() == -1) {

    //         grid.printGrid();

    //         if (currentPlayer == userPlayer) {
    //             grid.placePosition(Input.Integer("Select Position (1-7): "), userPlayer);
    //         } 
    //         else {
    //             grid.placePosition(cpu.play(), cpuPlayer);
    //         }
            
    //         currentPlayer = (currentPlayer==userPlayer) ? cpuPlayer : userPlayer;
    //     }
    //     grid.printGrid();
    //     int win = grid.checkWin();
    //     if (win == 1) {
    //         String winner = (currentPlayer == userPlayer) ? "cpu" : "user";
    //         System.out.println("\n" + winner + " Wins!");
    //     } else if (win == 0) {
    //         System.out.println("\nDRAW!!!");
    //     }
        // grid = new Grid();
        // int cpuPlayer = 1;
        // cpu = new CPUPerfect(grid, cpuPlayer);
        // System.out.println(cpu.play());
    // }
}
