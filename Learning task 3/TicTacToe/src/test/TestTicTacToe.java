package test;

import org.junit.Before;
import org.junit.Test;
import ttt.TicTacToe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test to test tic tac toe.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TestTicTacToe {

    /**
     * The tic tac toe.
     */
    private TicTacToe ticTacToe;

    /**
     * Sets up the tests.
     */
    @Before
    public void setUp() {
        ticTacToe = new TicTacToe();
    }

    /**
     * Tests the position value method.
     *
     * @see ttt.TicTacToe#positionValue()
     */
    @Test
    public void testPositionValue() {
        ticTacToe.setHumanPlays();
        ticTacToe.playMove(1);

        assertTrue(TicTacToe.UNCLEAR == ticTacToe.positionValue());
        assertFalse(TicTacToe.COMPUTER == ticTacToe.positionValue());
        assertFalse(TicTacToe.HUMAN == ticTacToe.positionValue());
        assertFalse(TicTacToe.DRAW == ticTacToe.positionValue());
    }

    /**
     * Tests the is a win method.
     *
     * @see ttt.TicTacToe#isAWin(int)
     */
    @Test
    public void testIsAWin() {
        for (int i = 0; i < 3; i++) {
            ticTacToe.setComputerPlays();
            ticTacToe.playMove(i);
        }

        assertTrue(ticTacToe.isAWin(TicTacToe.COMPUTER));
    }

    /**
     * Tests the choose move method.
     *
     * @see ttt.TicTacToe#chooseMove()
     */
    @Test
    public void testChooseMove() {
        ticTacToe.setHumanPlays();
        ticTacToe.playMove(8);
        ticTacToe.setComputerPlays();

        int bestMove = ticTacToe.chooseMove();
        assertEquals(bestMove, 4);

        ticTacToe.playMove(bestMove);
        ticTacToe.setComputerPlays();
        ticTacToe.playMove(2);

        bestMove = ticTacToe.chooseMove();
        assertEquals(bestMove, 0);

        ticTacToe.setComputerPlays();
        ticTacToe.playMove(bestMove);

        bestMove = ticTacToe.chooseMove();
        assertEquals(bestMove, 1);

        ticTacToe.setComputerPlays();
        ticTacToe.playMove(bestMove);

        assertTrue(ticTacToe.isAWin(TicTacToe.COMPUTER));
    }

}
