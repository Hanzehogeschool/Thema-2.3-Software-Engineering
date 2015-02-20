package multiformat;

public abstract class Format {

    public abstract String getName();

    abstract String toString(Rational p_number, Base p_base);

    abstract Rational parse(String p_number, Base p_base) throws FormatException;

}
