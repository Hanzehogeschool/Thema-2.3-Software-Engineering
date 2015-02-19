import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NumberRow {

    private int[] numbers;

    public NumberRow(int amount, int maximum) {
        numbers = new int[amount];
        fillArrayWithUniqueValues(amount, maximum);
    }

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

    public boolean algorithmC(int searchValue) {
        int index = 0;

        while (index < searchValue) {
            if (numbers[index] == searchValue) {
                return true;
            } else if (numbers[index] < searchValue) {
                return false;
            }
        }

        return false;
    }

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

    public void sort() {
        Arrays.sort(numbers);
    }

    public void print() {
        for (Integer number : numbers) {
            System.out.println(number);
        }
    }

}
