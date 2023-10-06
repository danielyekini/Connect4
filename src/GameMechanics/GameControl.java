package GameMechanics;

import javax.swing.JOptionPane;

import CPUPlayers.*;
import GameGUI.GUI;

public class GameControl {
    
    Grid grid;
    GUI game;
    CPUPlayer cpu;

    private boolean newGame = false;
    
    public int currentPlayer;

    public void start() {
        currentPlayer = 1;
        grid = new Grid();
        int difficulty = pickDifficulty();
        cpu = (difficulty == 0) ? new CPUEasy(grid.getPosMoves()) : new CPUPerfect(grid.getPosMoves());
        int userPlayer = pickPlayer() + 1;
        game = new GUI(this, grid, userPlayer);
        if (userPlayer == 2) {
            cpuMove();
        }
    }

    public int pickDifficulty() {
        String[] options = {"Easy", "Hard"};
        int choice = JOptionPane.showOptionDialog(
            null, "Pick Difficulty", "Pick Difficulty", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        return choice;
    }

    public int pickPlayer() {
        String[] options = {"Player 1 (Red)", "Player 2 (Yellow)"};
        int choice = JOptionPane.showOptionDialog(
            null, "Choose Player", "Choose Player", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        return choice;
    }

    public void cpuMove() {
        placePosition(cpu.play(currentPlayer), currentPlayer);
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



    
    //sets a place to current player
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
    //     while (grid.checkWin() == 0) {
    //         if (numTurns % 2 == 0) {
    //             grid.printGrid();
    //             grid.placePosition(Input.Integer("Select Position (1-5): "), player);
    //             numTurns--;
    //         } 
    //         else {
    //             grid.printGrid();
    //             grid.placePosition(CPUEasy.play(player), player);
    //             numTurns++;
    //         }
            
    //         player = (player==1) ? 2 : 1;
    //     }
    // }
}
