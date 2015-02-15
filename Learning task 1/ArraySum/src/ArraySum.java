public class ArraySum {

    private static int[] arrayOne = {1, 2, 3};
    private static int[] arrayTwo = {4, 5, 6};

    public static void main(String[] args) {
        try {
            sumArrays(arrayOne, arrayTwo);
        } catch (ArraySizeException arraySizeException) {
            System.out.println(arraySizeException.getMessage());
        }
    }

    public static void sumArrays(int[] arrayOne, int[] arrayTwo) throws ArraySizeException {
        if (arrayOne.length == arrayTwo.length) {
            System.out.print("[");

            for (int i = 0; i < arrayOne.length; i++) {
                System.out.print(arrayOne[i] + arrayTwo[i]);

                if (i != arrayOne.length - 1) {
                    System.out.print(", ");
                }
            }

            System.out.print("]");
        } else {
            throw new ArraySizeException("The size of both arrays are not equal.");
        }
    }

}
