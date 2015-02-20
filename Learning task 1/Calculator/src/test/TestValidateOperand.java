package test;

import multiformat.Calculator;
import multiformat.HexBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Test case to test the method validate operand from calculator.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TestValidateOperand {

    /**
     * The calculator.
     */
    Calculator calculator;

    /**
     * The hex base.
     */
    HexBase hexBase;

    /**
     * Set up the tests.
     */
    @Before
    public void setUp() {
        calculator = new Calculator();
        hexBase = new HexBase();
        calculator.setBase(hexBase);
    }

    /**
     * Tests the validate operand method.
     *
     * @see multiformat.Calculator#validateOperand(String)
     */
    @Test
    public void testValidateOperand() throws Exception {
        calculator.addOperand("1A.0");
        calculator.addOperand("2B.0");
        calculator.add();

        assertEquals("45.0", calculator.secondOperand());
    }

}
