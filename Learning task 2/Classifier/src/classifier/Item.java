package classifier;

import java.util.HashMap;
import java.util.Map;

public class Item {

    private Map<String, Feature> features = new HashMap<String, Feature>();
    private String name;

    public Item(String name, Feature[] features) {
        this.name = name;

        for (int i = 0; i < features.length; i++) {
            this.features.put(features[i].getName(), features[i]);
        }
    }

    public String getFeatureValue(String featureName) {
        Feature feature = (Feature) features.get(featureName);

        if (feature != null) {
            return feature.getValue();
        } else {
            return null;
        }
    }

    public void setFeatureValue(String featureName, String newValue) {
        Feature feature = (Feature) features.get(featureName);

        if (feature != null) {
            feature.setValue(newValue);
        }
    }

}
