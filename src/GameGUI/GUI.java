package GameGUI;

import javax.swing.*;
import javax.swing.border.*;

import GameMechanics.GameControl;
import GameMechanics.Grid;

import java.awt.*;
import java.awt.event.*;

public class GUI {

    private JFrame frame;
    private JLabel[][] slots;
    private JButton[] buttons;
    //variables used in grid
    private int xsize = 7;
    private int ysize = 6;
    private int currentPlayer = 1;
    private Color player1Color = Color.RED;
    private Color player2Color = Color.YELLOW;
    //making of grid and logic
    Grid grid;
    GameControl control; //create game logic controller

    public GUI(GameControl control, Grid grid) {
        this.control = control;
        this.grid = grid;

        frame = new JFrame("connect four");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(new GridLayout(xsize, ysize + 1));

        slots = new JLabel[xsize][ysize];
        buttons = new JButton[xsize];

        for (int i = 0; i < xsize; i++) {
            buttons[i] = new JButton("");
            JButton button = buttons[i];
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(player1Color);
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
                            if (control.placePosition(a, currentPlayer) == 1) {
                                control.cpuMove();
                            } else {
                                JOptionPane.showMessageDialog(null, "choose another one", "column is filled", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
            panel.add(buttons[i]);
        }
        for (int column = 0; column < ysize; column++) {
            for (int row = 0; row < xsize; row++) {
                slots[row][column] = new JLabel();
                slots[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                slots[row][column].setBorder(new LineBorder(Color.blue));
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

    public void updateBoard() {//keeps the gui in sync with the logggggtjiic and grid
        int row = grid.lastPlayed[1], column = grid.lastPlayed[0];
        slots[row][column].setOpaque(true);
        if (currentPlayer == 1) {
            slots[row][column].setBackground(player1Color);
        } else {
            slots[row][column].setBackground(player2Color);
        }
    }

    public void showWon() {
        String winner = "player " + currentPlayer + " wins";
        int n = JOptionPane.showConfirmDialog(
                frame,
                "new game?",
                winner,
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            frame.dispose();
            control.newGame();
        } else {
            frame.dispose();
            System.exit(0);
        }
    }

    public void showDraw() {
        String winner = "draw game";
        int n = JOptionPane.showConfirmDialog(
                frame,
                "new game?",
                winner,
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            frame.dispose();
            control.newGame();
        } else {
            frame.dispose();
            System.exit(0);
        }
    }

    public void changeCurrentPlayer(int newPlayer) {
        currentPlayer = newPlayer;
    }

    // public static void main(String[] args) {
    //     GUI gui = new GUI();
    // }
}
