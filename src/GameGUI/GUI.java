package GameGUI;

import javax.swing.*;

import GameMechanics.GameControl;
import GameMechanics.Grid;

import java.awt.*;
import java.awt.event.*;

public class GUI {

    private JFrame frame;
    private JLabel[][] slots;
    private JButton[] buttons;
    //variables used in grid
    private int xSize = 7;
    private int ySize = 6;
    private int currentPlayer = 1;
    private boolean newGame;
    private int user = 1;
    private Color player1Color = Color.RED, player2Color = Color.YELLOW;
    private Color userColor, cpuColor;
    //making of grid and logic
    Grid grid;
    GameControl control; //create game logic controller

    public GUI(GameControl control, Grid grid, int userPlayer) {
        this.control = control;
        this.grid = grid;
        newGame = false;

        setPlayers(userPlayer);

        frame = new JFrame("connect four");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(new GridLayout(ySize + 1, xSize));

        slots = new JLabel[xSize][ySize];
        buttons = new JButton[xSize];

        for (int i = 0; i < xSize; i++) {
            buttons[i] = new JButton("");
            JButton button = buttons[i];
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(userColor);
                }
            
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.WHITE);
                }
            });
            buttons[i].setActionCommand("" + i);
            buttons[i].addActionListener(
                    new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            currentPlayer = control.currentPlayer;
                            int a = Integer.parseInt(e.getActionCommand());
                            int userPlay = control.placePosition(a, currentPlayer);
                            if (userPlay == 1 && !newGame) {
                                control.cpuMoveWithDelay();
                            } else if (newGame) {
                                return;
                            }else {
                                JOptionPane.showMessageDialog(null, "choose another one", "column is filled", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
            panel.add(buttons[i]);
        }
        for (int column = 0; column < ySize; column++) {
            for (int row = 0; row < xSize; row++) {
                slots[row][column] = new JLabel();
                slots[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                slots[row][column].setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                slots[row][column].setOpaque(true);
                int position = grid.checkPosition(row, column);
                Color slotColor = (position == 1) ? Color.RED : 
                    (position == 2) ? Color.YELLOW : Color.WHITE;
                slots[row][column].setBackground(slotColor);
                panel.add(slots[row][column]);
            }
        }

        //jframe stuff
        frame.setContentPane(panel);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateBoard() { // keeps the gui in sync with the logic and grid
        int x = grid.lastPlayed[1], y = grid.lastPlayed[0];
        if (currentPlayer == user) {
            slots[x][y].setBackground(userColor);
        } else {
            slots[x][y].setBackground(cpuColor);
        }
    }

    private void showEndGame(String message) {
        int n = JOptionPane.showConfirmDialog(
                frame,
                "new game?",
                message,
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            frame.dispose();
            newGame = true;
            control.newGame();
        } else {
            frame.dispose();
            System.exit(0);
        }
    }

    public void showWon() {
        showEndGame("player " + currentPlayer + " wins");
    }

    public void showDraw() {
        showEndGame("draw game");
    }

    public void setPlayers(int userPlayer) {
        if (userPlayer == 1) {
            userColor = player1Color;
            cpuColor = player2Color;
            user = 1;
        } else if (userPlayer == 2) {
            userColor = player2Color;
            cpuColor = player1Color;
            user = 2;
        } else {
            throw new IllegalArgumentException("userPlayer must be 1 or 2");
        }
    }

    public void changeCurrentPlayer(int newPlayer) {
        currentPlayer = newPlayer;
    }
}