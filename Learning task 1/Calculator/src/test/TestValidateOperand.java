package test;

import multiformat.Calculator;
import multiformat.HexBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestValidateOperand {

    Calculator calculator;
    HexBase hexBase;

    @Before
    public void setUp() {
        calculator = new Calculator();
        hexBase = new HexBase();
        calculator.setBase(hexBase);
    }

    @Test
    public void testValidateOperand() throws Exception {
        calculator.addOperand("1A.0");
        calculator.addOperand("2B.0");
        calculator.add();

        assertEquals("45.0", calculator.secondOperand());
    }

}
