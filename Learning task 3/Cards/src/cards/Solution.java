package cards;

import java.util.Stack;

public class Solution extends Stack<Candidate> {

    int[][] check = {{}, {}, {1}, {0}, {2}, {3, 4}, {5, 6}, {7}};
    private Candidate[][] board = new Candidate[4][4];
    private int[] row = {0, 1, 1, 1, 2, 2, 2, 3};
    private int[] column = {2, 0, 1, 2, 1, 2, 3, 2};

    public Solution() {

    }

    private boolean bordersCard(int row, int column, char cardChar) {
        if (row > 0) {
            if (getChar(row - 1, column) == cardChar) {
                return true;
            }
        }

        if (row < 3) {
            if (getChar(row + 1, column) == cardChar) {
                return true;
            }
        }

        if (column > 0) {
            if (getChar(row, column - 1) == cardChar) {
                return true;
            }
        }

        if (column < 3) {
            if (getChar(row, column + 1) == cardChar) {
                return true;
            }
        }

        return false;
    }

    private char getChar(int row, int column) {
        if (board[row][column] != null) {
            return board[row][column].getCardChar();
        }

        return '?';
    }

    public boolean fits(Candidate candidate) {
        int i = this.size();

        if (bordersCard(row[i], column[i], candidate.getCardChar())) {
            return false;
        }

        return true;
    }

    public void record(Candidate candidate) {
        int i = this.size();
        board[row[i]][column[i]] = candidate;
        this.push(candidate);
    }

    public boolean complete() {
        if (this.size() == 8) {
            return isCorrect();
        }

        return false;
    }

    public void show() {
        System.out.println(this);
    }

    public Candidate eraseRecording() {
        int i = this.size() - 1;
        board[row[i]][column[i]] = null;

        return this.pop();
    }

    private char mustBeAdjacentTo(char card) {
        if (card == 'A') {
            return 'K';
        }

        if (card == 'K') {
            return 'Q';
        }

        if (card == 'Q') {
            return 'J';
        }

        return '?';
    }

    private boolean isCorrect() {
        for (int i = 0; i < 8; i++) {
            if (board[row[i]][column[i]] != null) {
                char chr = mustBeAdjacentTo(board[row[i]][column[i]].getCardChar());

                if (chr != '?') {
                    if (!bordersCard(row[i], column[i], chr)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public String toString() {
        String str = "";

        for (Candidate[] row : board) {
            for (Candidate object : row) {
                if (object == null) {
                    str += "[ ]";
                } else {
                    str += "[" + object.getCardChar() + "]";
                }
            }

            str += "\n";
        }

        return str;
    }

}
