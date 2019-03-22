package model;

import view.*;

public class Model {
    private View v;
    private int turn;
    private int movesCount;
    private char[][] board;
    private String message;

    public Model() {
        this.board = new char[3][3];
        this.movesCount = 0;
        this.turn = 1;
    }

    public void registerView(View v) {
        this.v = v;
    }

    public void PlayMove(int x, int y) {
        if(movesCount< 9){
            char symbol;
            if (turn % 2 != 0)
                symbol = 'X';
            else
                symbol = 'O';
            board[x][y] = symbol;
            movesCount=movesCount+1;
            if(isWinner(x, y,symbol)) {
                message="Player " + turn + " has won!";
                v.update(x, y, board[x][y], message,true);
            }
            else if(movesCount==9) {
                message="Game ended in a Draw";
                v.update(x, y, board[x][y], message,true);
            }
            else {
                if(turn%2 != 0) {
                    turn=2;
                    message="Player 2's turn: O";
                }
                else {
                    turn=1;
                    message="Player 1's turn ; X";
                }
                v.update(x, y, board[x][y], message,false);
            }
        }
    }

    public boolean isWinner(int x, int y, char symbol) {
        int countRow = 0;
        int countCol = 0;
        int countLDiag = 0;
        int countRDiag = 0;
        for(int i=0; i<board.length;i++) {
            if(board[x][i] == symbol)
                countRow++;
            if(board[i][y] == symbol)
                countCol++;
            if(board[i][i] == symbol)
                countRDiag++;
            if(board[board.length-1-i][i] == symbol)
                countLDiag++;	
        }
        if(countCol==board.length || countRow==board.length || countLDiag == board.length || countRDiag == board.length)
            return true;
        return false;
    }

    public void ResetModel() {
        movesCount = 0;
        turn=1;
        message="";
        for(int i=0; i<board.length;i++) {
            for(int j=0; j<board.length;j++) {
                board[i][j] = '\0';
            }
        }
        v.resetGame();
    }
		
}
