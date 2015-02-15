/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package multiformat;

/**
 * Class representing a rational ('breuk').
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class Rational {

    static final double PRECISION = 10;
    static final double EPSILON = Math.pow(10, -PRECISION);

    double numerator = 0.0; // teller
    double denominator = 1.0; // noemer

    /**
     * Constructor
     * Create a new Rational
     *
     * @param num Numerator
     * @param den Denominator
     */
    public Rational(double num, double den) {
        numerator = num;
        denominator = den;
        simplify();
    }

    /**
     * Constructor
     * Create a new Rational
     * Parameter-free Constructor
     */
    public Rational() {

    }

    /**
     * Constructor
     * Create a new Rational
     * User for double
     *
     * @param number Number
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
     * Seen also unittest TestRational.java
     *
     * @see test.TestRational
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
     * Seen also unittest TestRational.java
     *
     * @see test.TestRational
     */
    public void simplify() {
        // Take the smallest from the two (10.0)
        double divisor = Math.min(Math.abs(numerator), denominator);
        // Step from 10.0 to 9.0 to ... 1.0
        for (; divisor > 1.0; divisor -= 1.0) {
            double rn = Math.abs(
                    Math.IEEEremainder(Math.abs(numerator), divisor));
            double rd = Math.abs(
                    Math.IEEEremainder(denominator, divisor));
            // If both the numerator and denominator have a very small remainder
            // then they can both be divided by devisor (in our example 5).
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
     * @param other Another Rational to add to this.
     * @return A new Rational representing the sum.
     */
    public Rational plus(Rational other) {
        if (denominator == other.denominator)
            return new Rational(numerator + other.numerator
                    , other.denominator);
        else
            // a/x + b/y =
            // (breuken gelijknamig maken)
            // a*y/x*y + b*x/x*y = (a*y + b*x)/x*y
            return new Rational(numerator * other.denominator +
                    denominator * other.numerator
                    , denominator * other.denominator);
    }

    /**
     * Minus two Rationals.
     *
     * @param other Another Rational to minus to this.
     * @return A new Rational representing the sum.
     */
    public Rational minus(Rational other) {
        if (denominator == other.denominator)
            return new Rational(numerator - other.numerator, denominator);
        else
            return new Rational(numerator * other.denominator -
                    denominator * other.numerator
                    , denominator * other.denominator);
    }

    /**
     * Multiplies two Rationals.
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
     * Divides two Rationals.
     * It checks also if it's not divided by 0.
     *
     * @param other Another Rational to devide to this.
     * @return A new Rational representing the sum.
     */
    public Rational div(Rational other) {
        return new Rational(
                numerator * other.denominator,
                denominator * other.numerator);
    }

    /**
     * Copy two Rationals.
     *
     * @param other Another Rational to copy to this.
     */
    public void copyOf(Rational other) {
        this.numerator = other.numerator;
        this.denominator = other.denominator;
    }

	/* getters and setters for unittesting purposes. */

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
