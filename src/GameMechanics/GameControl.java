package GameMechanics;
import java.util.ArrayList;

import GameGUI.GUI;

public class GameControl {
    
    Grid grid;
    GUI game;
    CPUEasy cpu;
    
    public int currentPlayer;

    public void start() {
        currentPlayer = 1;
        grid = new Grid();
        cpu = new CPUEasy(grid.getPosMoves());
        game = new GUI(this, grid);
    }

    public void cpuMove() {
        placePosition(cpu.play(currentPlayer), currentPlayer);
    }

    public int placePosition(int position, int player) {
        if (grid.placePosition(position, player) == 1) {
            game.updateBoard();
            grid.printGrid();
            checkWin();
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
