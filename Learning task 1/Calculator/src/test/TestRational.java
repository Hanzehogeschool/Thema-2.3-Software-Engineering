package test;

import multiformat.Rational;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Test case to test rational.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TestRational {

    /**
     * The rational.
     */
    Rational rational;

    /**
     * Set up the tests.
     */
    @Before
    public void setUp() {
        rational = new Rational();
    }

    /**
     * Tests the simplify method.
     *
     * @see multiformat.Rational#simplify()
     */
    @Test
    public void testSimplify() {
        rational.setNumerator(25);
        rational.setDenominator(5);
        rational.simplify();

        assertEquals(5.0, rational.getNumerator());
        assertEquals(1.0, rational.getDenominator());

        rational.setNumerator(10);
        rational.setDenominator(0.5);
        rational.simplify();

        assertEquals(10.0, rational.getNumerator());
        assertEquals(0.5, rational.getDenominator());
    }

    /**
     * Tests the canonical method.
     *
     * @see multiformat.Rational#canonical()
     */
    @Test
    public void testCanonical() {
        rational.setNumerator(12.5);
        rational.setDenominator(1.0);
        rational.canonical();

        assertEquals(125.0, rational.getNumerator());
        assertEquals(10.0, rational.getDenominator());

        rational.setNumerator(12.5);
        rational.setDenominator(0.01);
        rational.canonical();

        assertEquals(125.0, rational.getNumerator());
        assertEquals(0.1, rational.getDenominator());
    }

    /**
     * Tests the canonical and simplify method.
     *
     * @see multiformat.Rational#canonical()
     * @see multiformat.Rational#simplify()
     */
    @Test
    public void testCanonicalAndSimplify() {
        rational.setNumerator(12.5);
        rational.setDenominator(1.0);
        rational.canonical();
        rational.simplify();

        assertEquals(25.0, rational.getNumerator());
        assertEquals(2.0, rational.getDenominator());
    }

    /**
     * Tests the divide method.
     *
     * @see multiformat.Rational#div(multiformat.Rational)
     */
    @Test
    public void testDivide() {
        rational.setNumerator(1.0);
        rational.setDenominator(0.0);
        rational.canonical();
        rational.simplify();

        assertEquals(1.0, rational.getNumerator(), 0);
        assertEquals(1.0, rational.getDenominator(), 0);
    }

}
