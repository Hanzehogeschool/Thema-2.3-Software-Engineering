package test;

import junit.framework.TestCase;
import multiformat.Calculator;
import multiformat.FormatException;

public class TestCalculator extends TestCase {

    public TestCalculator(String arg0) {
        super(arg0);
    }

    public void testOperations() {
        Calculator calculator = new Calculator();

        try {
            calculator.addOperand("3.2");
            assertEquals("0.0", calculator.firstOperand());
            assertEquals("3.2", calculator.secondOperand());

            calculator.addOperand("2.8");
            assertEquals("3.2", calculator.firstOperand());
            assertEquals("2.8", calculator.secondOperand());

            calculator.add();
            assertEquals("0.0", calculator.firstOperand());
            assertEquals("6.0", calculator.secondOperand());
        } catch (FormatException e) {
            fail("Unexpected format exception.");
        }
    }

}
