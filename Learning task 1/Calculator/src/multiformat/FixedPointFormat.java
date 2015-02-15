package multiformat;

public class FixedPointFormat extends Format {

    static final int MAX_PRECISION = 10;

    public String getName() {
        return "fixed";
    }

    String toString(Rational number, Base base) {
        double value = (double) number.numerator / (double) number.denominator;
        String result = base.toString(Math.abs(value));

        if (result.equals("0")) result += ".0";
        if (value < 0) result = "-" + result;

        return result;
    }

    public Rational parse(String number, Base base) {
        return new Rational(base.parse(number));
    }

}
