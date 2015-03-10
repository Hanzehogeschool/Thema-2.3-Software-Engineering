import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class representing a number printer.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class NumberPrinter {

    /**
     * The number.
     */
    public static int number = 4;

    /**
     * Number printer constructor.
     * Creates a new number printer.
     */
    public NumberPrinter() {
        for (int i = 1; i <= number; i++) {
            new Thread(new NumberPrinterTask(i)).start();
        }
    }

    /**
     * The main method.
     * Creates a new number printer without arguments.
     *
     * @param args The arguments for the number printer.
     */
    public static void main(String[] args) {
        new NumberPrinter();
    }

    /**
     * Class representing a number printer task.
     *
     * @author Nils Berlijn
     * @author Tom Broeninkg
     * @version 1.0
     */
    static class NumberPrinterTask implements Runnable {

        /**
         * The lock.
         */
        private static Lock lock = new ReentrantLock(true);

        /**
         * The last number.
         */
        private int lastNumber = 0;

        /**
         * Number printer task constructor.
         * Creates a new number printer task.
         *
         * @param lastNumber The last number.
         */
        public NumberPrinterTask(int lastNumber) {
            this.lastNumber = lastNumber;
        }

        /**
         * Runs.
         */
        public void run() {
            lock.lock();

            for (int i = 0; i < 2; i++) {
                System.out.print(lastNumber);

                if (i == 1) {
                    System.out.print("\n");
                }
            }

            lock.unlock();
        }

    }

}
