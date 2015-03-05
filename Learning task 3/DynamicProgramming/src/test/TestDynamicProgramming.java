package test;

import org.junit.Test;
import solvers.BottomUpSolver;
import solvers.RecursiveSolver;
import solvers.Solver;
import solvers.TopDownSolver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test to test dynamic programming.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TestDynamicProgramming {

    /**
     * The solver.
     */
    Solver solver;

    /**
     * Tests the recursive solver.
     *
     * @see solvers.RecursiveSolver
     */
    @Test
    public void testRecursive() {
        solver = new RecursiveSolver();
        doTest();
    }

    /**
     * Tests the bottom up solver.
     *
     * @see solvers.BottomUpSolver
     */
    @Test
    public void testBottomUp() {
        solver = new BottomUpSolver();
        doTest();
    }

    /**
     * Tests the top down solver.
     *
     * @see solvers.TopDownSolver
     */
    @Test
    public void testTopDown() {
        solver = new TopDownSolver();
        doTest();
    }

    /**
     * Does the test.
     */
    private void doTest() {
        assertTrue(solver.solve(new int[]{3, 5, 7, 9, 11}, 17));
        assertFalse(solver.solve(new int[]{2, 4}, 5));
        assertFalse(solver.solve(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, 31));
        assertTrue(solver.solve(new int[]{1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, 31));
    }

}
