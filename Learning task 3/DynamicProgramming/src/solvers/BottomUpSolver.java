package solvers;

/**
 * Class representing a bottom up solver.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class BottomUpSolver implements Solver {

    /**
     * Solves the sum.
     *
     * @param numbers The numbers.
     * @param sum     The sum.
     * @return If the sum is solved.
     */
    @Override
    public boolean solve(int[] numbers, int sum) {
        int[][] numbersArray;
        int tmp = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] >= 0) {
                tmp += numbers[i];
            }
        }

        numbersArray = new int[numbers.length][tmp];

        for (int i = 0; i < numbers.length; i++) {
            int sumB = 0;

            for (int j = 0; j <= i; j++) {
                numbersArray[i][numbers[j] - 1] = 1;
                sumB += numbers[j];
                numbersArray[i][sumB - 1] = 1;

                if (j > 0) {
                    tmp = sumB - numbers[j - 1];
                    numbersArray[i][tmp - 1] = 1;
                }
            }
        }

        if (sum <= numbersArray[0].length) {
            for (int i = 0; i < numbersArray.length; i++) {
                if (numbersArray[i][sum - 1] != 0) {
                    return true;
                }
            }
        }

        return false;
    }

}
