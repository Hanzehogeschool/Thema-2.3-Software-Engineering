package multiformat;

public abstract class Format {

    public abstract String getName();

    /**
     * Translate the internal Rational to a String representation
     * using the correct Format and Base.
     */
    abstract String toString(Rational p_number, Base p_base);

    /**
     * Translate a string representation in a specific format and base to
     * the internal Rational representation.
     */
    abstract Rational parse(String p_number, Base p_base) throws FormatException;

}
