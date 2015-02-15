package test;

import multiformat.Calculator;
import multiformat.OctalBase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Test case to test octal base.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TestOctalBase {

    /**
     * The calculator.
     */
    Calculator calculator;

    /**
     * The octal base.
     */
    OctalBase octalBase;

    /**
     * Set up the tests.
     */
    @Before
    public void setUp() {
        calculator = new Calculator();
        octalBase = new OctalBase();
        calculator.setBase(octalBase);
    }

    /**
     * Tests the octal base.
     *
     * @see multiformat.OctalBase
     */
    @Test
    public void testOctalBase() throws Exception {
        calculator.addOperand("5.0");
        calculator.addOperand("6.0");
        calculator.add();

        assertEquals("13.0", calculator.secondOperand());
    }

}
