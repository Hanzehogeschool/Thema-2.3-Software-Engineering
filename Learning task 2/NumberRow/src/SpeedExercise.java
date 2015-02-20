import utils.ANSI;

public class SpeedExercise {

    private static final int AMOUNT = 100000;
    private static final int MAXIMUM = 200000;
    private static final int TEST_AMOUNT = 5;
    private static long startTime;
    private static long endTime;
    private static boolean found;
    private static int searchNumber;

    public static void main(String[] args) {
        System.out.println(ANSI.ANSI_BLUE + "Speed Exercise\n");

        testAlgorithms();
        testAlgorithmAvsAlgorithmB(TEST_AMOUNT);
    }

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
        startTime = time();
        found = numberRow.algorithmD(searchNumber);
        endTime = time();
        printResults();
    }

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

    private static long time() {
        return System.currentTimeMillis();
    }

    private static int randomNumber() {
        return (int) Math.floor((Math.random() * 200000));
    }

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
