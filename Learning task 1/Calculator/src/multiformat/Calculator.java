package multiformat;

public class Calculator {

    private Rational operand_0 = new Rational();
    private Rational operand_1 = new Rational();
    private Format format = new FixedPointFormat();
    private Base base = new DecimalBase();

    /**
     * Adds a new operand.
     *
     * @param newOperand New operand.
     * @throws FormatException
     */
    public void addOperand(String newOperand) throws FormatException {
        try {
            validateOperand(newOperand);
            operand_1 = operand_0;
            operand_0 = format.parse(newOperand, base);
        } catch (NumberBaseException numberBaseException) {
            System.err.println(numberBaseException.getMessage());
        }
    }

    /**
     * Validates the operand.
     *
     * @param operand Operand.
     * @throws NumberBaseException
     */
    private void validateOperand(String operand) throws NumberBaseException {
        String baseDigits = base.getDigits() + "+-*/^";

        for (int i = 0; i < operand.length(); i++) {
            if (operand.charAt(i) != '.' && operand.charAt(i) != '-' && operand.charAt(i) != '+') {
                if (baseDigits.indexOf(operand.charAt(i)) < 0) {
                    throw new NumberBaseException("Error! Invalid character: " + operand.charAt(i));
                }
            }
        }
    }

    public void add() {
        operand_0 = operand_1.plus(operand_0);
        operand_1 = new Rational();
    }

    public void subtract() {
        operand_0 = operand_1.minus(operand_0);
        operand_1 = new Rational();
    }

    public void multiply() {
        operand_0 = operand_1.mul(operand_0);
        operand_1 = new Rational();
    }

    public void divide() {
        operand_0 = operand_1.div(operand_0);
        operand_1 = new Rational();
    }

    public void delete() {
        operand_0 = operand_1;
        operand_1 = new Rational();
    }

    public String firstOperand() {
        return format.toString(operand_1, base);
    }

    public String secondOperand() {
        return format.toString(operand_0, base);
    }

    public void setBase(Base newBase) {
        base = newBase;
    }

    public Base getBase() {
        return base;
    }

    public void setFormat(Format newFormat) {
        format = newFormat;
    }

    public Format getFormat() {
        return format;
    }

}
