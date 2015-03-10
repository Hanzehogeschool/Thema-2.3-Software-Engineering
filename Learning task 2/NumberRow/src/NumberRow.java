import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Class representing a number row.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class NumberRow {

    /**
     * The numbers.
     */
    private int[] numbers;

    /**
     * Number row constructor.
     * Creates a new tree builder.
     *
     * @param amount  The amount.
     * @param maximum The maximum.
     */
    public NumberRow(int amount, int maximum) {
        numbers = new int[amount];
        fillArrayWithUniqueValues(amount, maximum);
    }

    /**
     * Fills the array with unique values.
     *
     * @param amount  The amount.
     * @param maximum The maximum.
     */
    private void fillArrayWithUniqueValues(int amount, int maximum) {
        ArrayList<Integer> helpList = new ArrayList<Integer>(maximum);

        for (int i = 0; i < maximum; i++) {
            helpList.add(i);
        }

        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            int numbers = helpList.remove(random.nextInt(helpList.size()));
            this.numbers[i] = numbers;
        }
    }

    /**
     * Algorithm a.
     *
     * @param searchValue The search value.
     * @return If the search value is found or not.
     */
    public boolean algorithmA(int searchValue) {
        boolean found = false;
        int index = 0;

        while (index < numbers.length) {
            if (numbers[index] == searchValue) {
                found = true;
            }

            index++;
        }

        return found;
    }

    /**
     * Algorithm b.
     *
     * @param searchValue The search value.
     * @return If the search value is found or not.
     */
    public boolean algorithmB(int searchValue) {
        int index = 0;

        while (index < numbers.length) {
            if (numbers[index] == searchValue) {
                return true;
            }

            index++;
        }

        return false;
    }

    /**
     * Algorithm c.
     *
     * @param searchValue The search value.
     * @return If the search value is found or not.
     */
    public boolean algorithmC(int searchValue) {
        int index = 0;

        while (index < numbers.length) {
            if (numbers[index] == searchValue) {
                return true;
            }

            index++;
        }

        return false;
    }

    /**
     * Algorithm d.
     *
     * @param searchValue The search value.
     * @return If the search value is found or not.
     */
    public boolean algorithmD(int searchValue) {
        int lowest = 0;
        int highest = numbers.length - 1;
        int middle;

        while (lowest <= highest) {
            middle = (lowest + highest) / 2;

            if (numbers[middle] > searchValue) {
                highest = middle - 1;
            } else if (numbers[middle] < searchValue) {
                lowest = middle + 1;
            } else if (numbers[middle] == searchValue) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * Sorts the numbers.
     */
    public void sort() {
        Arrays.sort(numbers);
    }

    /**
     * Prints the numbers.
     */
    public void print() {
        for (Integer number : numbers) {
            System.out.println(number);
        }
    }

}
