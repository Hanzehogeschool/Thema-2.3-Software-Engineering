import utils.ANSI;

import java.util.Random;

/**
 * Class representing a speed exercise.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class SpeedExercise {

    /**
     * The amount.
     */
    private static final int AMOUNT = 100000;

    /**
     * The maximum.
     */
    private static final int MAXIMUM = 200000;

    /**
     * The test amount.
     */
    private static final int TEST_AMOUNT = 5;

    /**
     * The start time.
     */
    private static long startTime;

    /**
     * The end time.
     */
    private static long endTime;

    /**
     * The found.
     */
    private static boolean found;

    /**
     * The search number.
     */
    private static int searchNumber;

    /**
     * The main method.
     * Creates a new speed exercise without arguments.
     *
     * @param args The arguments for the speed exercise.
     */
    public static void main(String[] args) {
        System.out.println(ANSI.ANSI_BLUE + "Speed Exercise\n");

        testAlgorithms();
        testAlgorithmAvsAlgorithmB(TEST_AMOUNT);
    }

    /**
     * Tests the algorithms.
     */
    private static void testAlgorithms() {
        System.out.println(ANSI.ANSI_YELLOW + "Performing algorithms test...");
        NumberRow numberRow = new NumberRow(AMOUNT, MAXIMUM);
        searchNumber = randomNumber();
        System.out.println(ANSI.ANSI_YELLOW + "Searching for number " + searchNumber + "...\n");

        System.out.println(ANSI.ANSI_YELLOW + "Performing algorithm a...");
        startTime = time();
        found = numberRow.algorithmA(searchNumber);
        endTime = time();
        printResults();

        System.out.println(ANSI.ANSI_YELLOW + "Performing algorithm b...");
        startTime = time();
        found = numberRow.algorithmB(searchNumber);
        endTime = time();
        printResults();

        System.out.println(ANSI.ANSI_YELLOW + "Performing algorithm c...");
        numberRow.sort();
        startTime = time();
        found = numberRow.algorithmC(searchNumber);
        endTime = time();
        printResults();

        System.out.println(ANSI.ANSI_YELLOW + "Performing algorithm d...");
        numberRow.sort();
        startTime = time();
        found = numberRow.algorithmD(searchNumber);
        endTime = time();
        printResults();
    }

    /**
     * Tests algorithm a versus algorithm b.
     *
     * @param testAmount The amount.
     */
    private static void testAlgorithmAvsAlgorithmB(int testAmount) {
        System.out.println(ANSI.ANSI_YELLOW + "Performing algorithm a versus algorithm b test...");
        NumberRow[] numberRows = new NumberRow[testAmount];

        for (int i = 0; i < testAmount; i++) {
            numberRows[i] = new NumberRow(AMOUNT, MAXIMUM);
        }

        System.out.println(ANSI.ANSI_MAGENTA + "Search value\tAlgorithm a\t\tAlgorithm b");

        for (NumberRow numberRow : numberRows) {
            searchNumber = randomNumber();
            System.out.print(searchNumber);

            startTime = time();
            found = numberRow.algorithmA(searchNumber);
            endTime = time();
            System.out.print("\t\t\t" + (endTime - startTime) + " ms");

            startTime = time();
            found = numberRow.algorithmB(searchNumber);
            endTime = time();
            System.out.print("\t\t\t" + (endTime - startTime) + " ms\n");
        }
    }

    /**
     * The current time in milliseconds.
     *
     * @return The current time in milliseconds.
     */
    private static long time() {
        return System.currentTimeMillis();
    }

    /**
     * Generates a random number.
     *
     * @return A random number.
     */
    private static int randomNumber() {
        return new Random().nextInt(MAXIMUM);
    }

    /**
     * Prints the results.
     */
    public static void printResults() {
        String string;

        if (found) {
            string = ANSI.ANSI_GREEN + "Value " + searchNumber + " is found, ";
        } else {
            string = ANSI.ANSI_RED + "Value " + searchNumber + " is not found, ";
        }

        string += "the search took " + (endTime - startTime) + " ms.\n";

        System.out.println(string);
    }

}
