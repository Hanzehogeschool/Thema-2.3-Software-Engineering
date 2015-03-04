package ttt;

import java.util.Random;

public class TicTacToe {

    public static final int HUMAN = 0;
    public static final int COMPUTER = 1;
    public static final int EMPTY = 2;
    public static final int HUMAN_WIN = 0;
    public static final int DRAW = 1;
    public static final int UNCLEAR = 2;
    public static final int COMPUTER_WIN = 3;
    private int[][] board = new int[3][3];
    private Random random = new Random();
    private int side = random.nextInt(2);
    private int position = UNCLEAR;
    private char computerChar, humanChar;
    private int[][][] positions =
            {
                    {{0, 0}, {0, 1}, {0, 2}},
                    {{1, 0}, {1, 1}, {1, 2}},
                    {{2, 0}, {2, 1}, {2, 2}},

                    {{0, 0}, {1, 0}, {2, 0}},
                    {{0, 1}, {1, 1}, {2, 1}},
                    {{0, 2}, {1, 2}, {2, 2}},

                    {{0, 0}, {1, 1}, {2, 2}},
                    {{0, 2}, {1, 1}, {2, 0}}
            };

    public TicTacToe() {
        clearBoard();
        initSide();
    }

    private void initSide() {
        if (this.side == COMPUTER) {
            computerChar = 'X';
            humanChar = 'O';
        } else {
            computerChar = 'O';
            humanChar = 'X';
        }
    }

    public void setComputerPlays() {
        side = COMPUTER;
        initSide();
    }

    public void setHumanPlays() {
        side = HUMAN;
        initSide();
    }

    public boolean computerPlays() {
        return side == COMPUTER;
    }

    public int chooseMove() {
        Best best = chooseMove(COMPUTER);

        return best.row * 3 + best.column;
    }

    private Best chooseMove(int side) {
        Best reply;

        int opp;
        int simpleEval;
        int bestRow = 0;
        int bestColumn = 0;
        int value;

        if ((simpleEval = positionValue()) != UNCLEAR) {
            return new Best(simpleEval);
        }

        if (side == HUMAN) {
            opp = COMPUTER;
            value = COMPUTER_WIN;
        } else {
            opp = HUMAN;
            value = HUMAN_WIN;
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (squareIsEmpty(row, column)) {
                    place(row, column, side);
                    reply = chooseMove(opp);
                    place(row, column, EMPTY);

                    if ((side == COMPUTER && reply.val > value) || (side == HUMAN && reply.val < value)) {
                        value = reply.val;
                        bestRow = row;
                        bestColumn = column;
                    }
                }
            }
        }

        return new Best(value, bestRow, bestColumn);
    }

    public boolean moveOk(int move) {
        return (move >= 0 && move <= 8 && board[move / 3][move % 3] == EMPTY);
    }

    public void playMove(int move) {
        board[move / 3][move % 3] = side;

        if (side == COMPUTER) {
            side = HUMAN;
        } else {
            side = COMPUTER;
        }
    }

    private void clearBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private boolean boardIsFull() {
        boolean boardFull = true;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == EMPTY) {
                    boardFull = false;
                }
            }
        }

        return boardFull;
    }

    public boolean isAWin(int side) {
        for (int i = 0; i < positions.length; i++) {
            if (board[positions[i][0][0]][positions[i][0][1]] == side && board[positions[i][1][0]][positions[i][1][1]] == side && board[positions[i][2][0]][positions[i][2][1]] == side) {
                return true;
            }
        }

        return false;
    }

    private void place(int row, int column, int piece) {
        board[row][column] = piece;
    }

    private boolean squareIsEmpty(int row, int column) {
        return board[row][column] == EMPTY;
    }

    public int positionValue() {
        int pos = 0;

        if (isAWin(COMPUTER)) {
            pos = COMPUTER_WIN;
        } else if (isAWin(HUMAN)) {
            pos = HUMAN_WIN;
        } else if (boardIsFull()) {
            pos = DRAW;
        } else {
            pos = UNCLEAR;
        }

        return pos;
    }

    public String toString() {
        String output = "\n";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == COMPUTER)
                    output += computerChar + " ";
                else if (board[i][j] == HUMAN)
                    output += humanChar + " ";
                else
                    output += "  ";
            }

            output += "\n";
        }

        return output;
    }

    public boolean gameOver() {
        position = positionValue();

        return position != UNCLEAR;
    }

    public String winner() {
        if (position == COMPUTER_WIN) {
            return "computer";
        } else if (position == HUMAN_WIN) {
            return "human";
        } else {
            return "nobody";
        }
    }

}
