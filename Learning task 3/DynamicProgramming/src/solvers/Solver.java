package solvers;

/**
 * Class representing a solver.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public interface Solver {

    /**
     * Solves the sum.
     *
     * @param numbers The numbers.
     * @param sum     The sum.
     * @return If the sum is solved.
     */
    public boolean solve(int[] numbers, int sum);

}
