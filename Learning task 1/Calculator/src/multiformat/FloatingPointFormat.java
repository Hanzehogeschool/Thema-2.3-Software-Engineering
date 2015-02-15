package multiformat;

public class FloatingPointFormat extends Format {

    public String getName() {
        return "float";
    }

    public String toString(Rational number, Base base) {
        double value = number.numerator / number.denominator;
        double baseNum = base.getBase();

        if (value == 0.0) {
            return "0.0*10^1";
        } else {
            double power = Math.floor(Math.log(Math.abs(value)) / Math.log(baseNum));
            double mantissa = Math.abs(value) / Math.pow(baseNum, power);
            String result = "";
            result = base.toString(mantissa) + "*10^"
                    + base.toString(power);
            if (value < 0) {
                result = "-" + result;
            }
            return result;
        }
    }

    public Rational parse(String number, Base base) throws FormatException {
        int indexMul = number.indexOf('*');
        int indexPow = number.indexOf('^');
        if (indexMul <= 0 || indexPow <= 0) {
            throw new FormatException("Error! Not a floating point format");
        }
        double mantissa = base.parse(number.substring(0, indexMul));
        double power = base.parse(number.substring(indexPow + 1));
        double value = mantissa * Math.pow(base.getBase(), power);
        return new Rational(value);
    }

}
