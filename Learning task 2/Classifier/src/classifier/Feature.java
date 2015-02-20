package classifier;

public class Feature {

    private String name;
    private String value;
    private FeatureType type;

    public Feature(String name, String value, FeatureType type) {
        this.name = name;
        this.type = type;
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        if (type.isAllowed(newValue)) {
            value = newValue;
        } else
            throw new IllegalArgumentException("value '" + newValue +
                    "' not valid for attribute " + name);
    }

    public String toString() {
        return name + "=" + value;
    }

    public FeatureType type() {
        return type;
    }

}
