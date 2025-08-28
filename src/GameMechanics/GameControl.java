package GameMechanics;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
        int difficulty = pickDifficulty();
        userPlayer = pickPlayer() + 1;
        cpuPlayer = (userPlayer == 1) ? 2 : 1;
        cpu = (difficulty == 0) ? new CPUEasy(grid) : new CPUPerfect(grid, cpuPlayer);
        SwingUtilities.invokeLater(() -> {
            game = new GUI(this, grid, userPlayer);
            if (userPlayer == 2) {
                cpuMoveWithDelay();
            }
        });
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

    public void cpuMoveWithDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int move = cpu.play();
            SwingUtilities.invokeLater(() -> placePosition(move, currentPlayer));
        }).start();
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
}
