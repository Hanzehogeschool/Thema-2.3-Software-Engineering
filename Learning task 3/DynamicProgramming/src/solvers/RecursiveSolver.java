package solvers;

/**
 * Class representing a recursive solver.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class RecursiveSolver implements Solver {

    /**
     * Solves the sum.
     *
     * @param numbers The numbers.
     * @param sum     The sum.
     * @return If the sum is solved.
     */
    @Override
    public boolean solve(int[] numbers, int sum) {
        if (numbers.length != 0) {
            for (int i = 0; i < numbers.length; i++) {
                int sumB = numbers[i];

                for (int j = i + 1; j < numbers.length; j++) {
                    sumB += numbers[j];

                    if (sumB == sum || sumB - numbers[j - 1] == sum) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
