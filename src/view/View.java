package view;

import controller.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class View {
    private Controller c;
    private GameListener listener;
    private JFrame myFrame;
    private JButton[][] blocks;
    private JButton reset;
    private JTextArea playerMessage;
    
    public View() {
        this.myFrame = new JFrame("Tic Tac Toe");
        this.blocks = new JButton[3][3];
        this.reset = new JButton("Reset Game");
        this.playerMessage = new JTextArea();
        initialize();
    }
    
    public void registerController(Controller c) {
        this.c = c;
    }
    
    public void initialize () {
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(new Dimension(400, 400));
        myFrame.setResizable(true);
        JPanel boardContainer = new JPanel(new FlowLayout());
        JPanel board = new JPanel(new GridLayout(3,3));
        boardContainer.add(board, BorderLayout.CENTER);
        JPanel resetContainer = new JPanel(new FlowLayout());
        resetContainer.add(reset);
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);
        myFrame.add(boardContainer, BorderLayout.NORTH);
        myFrame.add(resetContainer, BorderLayout.CENTER);
        myFrame.add(messages, BorderLayout.SOUTH);
        messages.add(playerMessage);
        playerMessage.setText("Player 1's turn: X");
        for(int row = 0; row<3 ;row++) {
            for(int column = 0; column<3 ;column++) {
                blocks[row][column] = new JButton();
                blocks[row][column].setPreferredSize(new Dimension(90,90));
                blocks[row][column].setText("");
                board.add(blocks[row][column]);
            }
        }
        this.listener = new GameListener();
        for(int row = 0; row<3 ;row++) {
            for(int column = 0; column<3 ;column++) {
                blocks[row][column].addActionListener(listener);
            }
        }
        reset.addActionListener(listener);
        myFrame.setVisible(true);
    }

    public boolean isReset(ActionEvent e) {
        if(e.getSource() == reset)
            return true;
        return false;
    }
    
    public ArrayList<Integer> getPosition(ActionEvent e) {
    	ArrayList<Integer> position = new ArrayList<Integer>();
    	for(int row = 0; row<3 ;row++) {
            for(int column = 0; column<3 ;column++) {
                if(blocks[row][column] == e.getSource()) {
                    position.add(row);
                    position.add(column);
                }
            }
        }
        return position;
    }
    
    public void update(int row, int column, char symbol, String message, boolean isWinner) {
        blocks[row][column].setText(symbol+"");
        blocks[row][column].setEnabled(false);
        playerMessage.setText(message);
        if(isWinner){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    blocks[i][j].setEnabled(false);
                }
            }
            playerMessage.setText(message);
            JOptionPane.showMessageDialog(null, message);
            c.resetGame();
        }
    }
    
    public void resetGame() {
    	for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                blocks[row][column].setText("");
                blocks[row][column].setEnabled(true);
            }
        }
        playerMessage.setText("Player 1's turn: X");
    }
    
    public class GameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isReset(e)) {
                c.resetGame();
            } else {
                ArrayList<Integer> position = getPosition(e);
                c.updateMove(position);
            }
        }
    }

}
