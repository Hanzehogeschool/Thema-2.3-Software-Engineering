package multiformat;

/**
 * Class representing a rational.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class Rational {

    /**
     * The precision.
     */
    static final double PRECISION = 10;

    /**
     * The epsilon.
     */
    static final double EPSILON = Math.pow(10, -PRECISION);

    /**
     * The numerator.
     */
    double numerator = 0.0;

    /**
     * The denominator.
     */
    double denominator = 1.0;

    /**
     * Constructor.
     * Create a new rational.
     *
     * @param num Numerator.
     * @param den Denominator.
     */
    public Rational(double num, double den) {
        numerator = num;
        denominator = den;
        simplify();
    }

    /**
     * Constructor.
     * Create a new rational.
     * Parameter-free Constructor.
     */
    public Rational() {

    }

    /**
     * Constructor.
     * Create a new rational.
     *
     * @param number Number.
     */
    public Rational(double number) {
        numerator = number;
        denominator = 1.0;
        canonical();
        simplify();
    }

    /**
     * Get rid of any decimals in the numerator. E.g. 12.5/1.0 becomes 125.0/10.0
     * (Note that any decimals in the denominator aren't handled, eg 10/0.5.
     * This seems an omission.)
     * Seen also unit test TestRational.java
     *
     * @see test.TestRational#testCanonical()
     */
    public void canonical() {
        double num = Math.abs(numerator);
        double decimal = num - Math.floor(num);
        int num_digits = 0;

        while (decimal > EPSILON && num_digits < PRECISION) {
            num = num * 10;
            decimal = num - Math.floor(num);
            num_digits++;
        }

        numerator = numerator * Math.pow(10.0, num_digits);
        denominator = denominator * Math.pow(10.0, num_digits);
    }

    /**
     * Simplify the rational. 125/10 becomes 25/2.
     * Seen also unit test TestRational.java
     *
     * @see test.TestRational#testSimplify()
     */
    public void simplify() {
        double divisor = Math.min(Math.abs(numerator), denominator);

        for (; divisor > 1.0; divisor -= 1.0) {
            double rn = Math.abs(
                    Math.IEEEremainder(Math.abs(numerator), divisor));
            double rd = Math.abs(
                    Math.IEEEremainder(denominator, divisor));

            if (rn < EPSILON && rd < EPSILON) {
                numerator /= divisor;
                denominator /= divisor;
                divisor = Math.min(Math.abs(numerator), denominator);
            }
        }
    }

    /**
     * Add two rationals.
     *
     * @param other Another rational to add to this.
     * @return A new rational representing the sum.
     */
    public Rational plus(Rational other) {
        if (denominator == other.denominator) {
            return new Rational(numerator + other.numerator
                    , other.denominator);
        } else {
            return new Rational(numerator * other.denominator +
                    denominator * other.numerator
                    , denominator * other.denominator);
        }
    }

    /**
     * Minus two rationals.
     *
     * @param other Another rational to minus to this.
     * @return A new rational representing the sum.
     */
    public Rational minus(Rational other) {
        if (denominator == other.denominator) {
            return new Rational(numerator - other.numerator, denominator);
        } else {
            return new Rational(numerator * other.denominator -
                    denominator * other.numerator
                    , denominator * other.denominator);
        }
    }

    /**
     * Multiplies two rationals.
     *
     * @param other Another Rational to multiply to this.
     * @return A new Rational representing the sum.
     */
    public Rational mul(Rational other) {
        return new Rational(
                numerator * other.numerator,
                denominator * other.denominator);
    }

    /**
     * Divides two rationals.
     * It checks also if it's not divided by 0.
     *
     * @param other Another rational to divide to this.
     * @return A new rational representing the sum.
     * @see test.TestRational#testDivide()
     */
    public Rational div(Rational other) {
        if (other.numerator != 0.0 || other.denominator != 0.0) {
            return new Rational(numerator * other.denominator, denominator * other.numerator);
        } else {
            System.err.print("Error! Cannot divide 0\n");
            return new Rational(1, 1);
        }
    }

    /**
     * Copy two rationals.
     *
     * @param other Another rational to copy to this.
     */
    public void copyOf(Rational other) {
        this.numerator = other.numerator;
        this.denominator = other.denominator;
    }

    /**
     * Get the numerator.
     *
     * @return The numerator.
     */
    public double getNumerator() {
        return numerator;
    }

    /**
     * Set the numerator.
     *
     * @param num The new numerator.
     */
    public void setNumerator(double num) {
        numerator = num;
    }

    /**
     * Get the denominator.
     *
     * @return The denominator.
     */
    public double getDenominator() {
        return denominator;
    }

    /**
     * Set the denominator.
     *
     * @param den The new denominator.
     */
    public void setDenominator(double den) {
        denominator = den;
    }

}
