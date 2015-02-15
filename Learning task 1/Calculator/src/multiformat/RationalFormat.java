package multiformat;

public class RationalFormat extends Format {

    public String getName() {
        return "rat";
    }

    String toString(Rational number, Base base) {
        return base.toString(number.numerator) + "/" + base.toString(number.denominator);
    }

    Rational parse(String number, Base base) throws FormatException {
        int index = number.indexOf('/');

        if (index >= 0)
            return new Rational(base.parse(number.substring(0, index).trim()), base.parse(number.substring(index + 1).trim()));
        else {
            throw new FormatException("Error! Not a rational format");
        }
    }

}
