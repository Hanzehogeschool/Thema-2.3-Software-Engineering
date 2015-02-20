package test;

import junit.framework.TestCase;
import multiformat.*;

public class TestFormat extends TestCase {

    public TestFormat(String arg0) {
        super(arg0);
    }

    public void testFormatBase() {
        Calculator calculator = new Calculator();

        try {
            calculator.addOperand("0.75");

            assertEquals("0.75", calculator.secondOperand());
            calculator.setBase(new BinaryBase());
            assertEquals("0.11", calculator.secondOperand());
            calculator.setBase(new HexBase());
            assertEquals("0.C", calculator.secondOperand());

            calculator.setFormat(new FloatingPointFormat());
            assertEquals("C.0*10^-1.0", calculator.secondOperand());
            calculator.setBase(new BinaryBase());
            assertEquals("1.1*10^-1.0", calculator.secondOperand());
            calculator.setBase(new DecimalBase());
            assertEquals("7.5*10^-1.0", calculator.secondOperand());

            calculator.setFormat(new RationalFormat());
            assertEquals("3.0/4.0", calculator.secondOperand());
            calculator.setBase(new BinaryBase());
            assertEquals("11.0/100.0", calculator.secondOperand());
            calculator.setBase(new HexBase());
            assertEquals("3.0/4.0", calculator.secondOperand());
        } catch (FormatException e) {
            fail("Unexpected exception.");
        }
    }

}
