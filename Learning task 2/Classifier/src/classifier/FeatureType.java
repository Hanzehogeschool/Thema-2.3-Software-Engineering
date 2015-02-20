package classifier;

import java.util.Collection;
import java.util.TreeSet;

public class FeatureType {

    private TreeSet<String> allowed = new TreeSet<String>();
    private String name;

    public FeatureType(String name, String[] allowedValues) {
        this.name = name;

        for (int i = 0; i < allowedValues.length; i++) {
            allowed.add(allowedValues[i]);
        }
    }

    public boolean isAllowed(String value) {
        return allowed.contains(value);
    }

    public Collection<String> allowedValues() {
        return allowed;
    }

}
