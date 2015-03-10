import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class representing a number printer revise.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class NumberPrinterRevise {

    /**
     * The number.
     */
    public static int number = 4;

    /**
     * Number printer revise constructor.
     * Creates a new number printer revise.
     */
    public NumberPrinterRevise() {
        for (int i = 1; i <= number; i++) {
            new Thread(new NumberPrinterReviseTask(i)).start();
        }
    }

    /**
     * The main method.
     * Creates a new number printer revise without arguments.
     *
     * @param args The arguments for the number printer revise.
     */
    public static void main(String[] args) {
        new NumberPrinterRevise();
    }

    /**
     * Class representing a number printer task.
     *
     * @author Nils Berlijn
     * @author Tom Broeninkg
     * @version 1.0
     */
    static class NumberPrinterReviseTask implements Runnable {

        /**
         * The lock.
         */
        private static Lock lock = new ReentrantLock(true);

        /**
         * The condition.
         */
        private static Condition condition = lock.newCondition();

        /**
         * The last number.
         */
        private int lastNumber = 0;

        /**
         * Number printer revise task constructor.
         * Creates a new number printer revise task.
         *
         * @param lastNumber The last number.
         */
        public NumberPrinterReviseTask(int lastNumber) {
            this.lastNumber = lastNumber;
        }

        /**
         * Runs.
         */
        public void run() {
            lock.lock();

            try {
                while (lastNumber != NumberPrinterRevise.number) {
                    condition.await();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } finally {
                for (int i = 0; i < 2; i++) {
                    System.out.print(lastNumber);

                    if (i == 1) {
                        System.out.print("\n");
                        NumberPrinterRevise.number = lastNumber - 1;
                    }
                }

                condition.signalAll();
                lock.unlock();
            }
        }

    }

}
