package test;

import classifier.DecisionTree;
import classifier.Feature;
import classifier.FeatureType;
import classifier.Item;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestTraining extends TestCase {

    private FeatureType yn = new FeatureType("YesNo", new String[]{"yes", "no"});

    public TestTraining(String arg0) {
        super(arg0);
    }

    private Item createItem(String ac, String abs) {
        Feature[] featureValues = new Feature[]{
                new Feature("AC", ac, yn),
                new Feature("ABS", abs, yn)
        };

        return new Item("car", featureValues);
    }

    public void testBuildDecisionTree() {
        Map<Item, String> trainingsSet = new HashMap<Item, String>();
        Map<String, FeatureType> features = new HashMap<String, FeatureType>();

        features.put("AC", yn);
        features.put("ABS", yn);

        Item item1 = createItem("yes", "yes");
        trainingsSet.put(item1, "high");

        Item item2 = createItem("yes", "no");
        trainingsSet.put(item2, "medium");

        Item item3 = createItem("no", "yes");
        trainingsSet.put(item3, "medium");

        Item item4 = createItem("no", "no");
        trainingsSet.put(item4, "low");

        DecisionTree dc = new DecisionTree(trainingsSet, features);

        assertEquals("high", dc.assignCategory(item1));
        assertEquals("medium", dc.assignCategory(item2));
        assertEquals("medium", dc.assignCategory(item3));
        assertEquals("low", dc.assignCategory(item4));
    }

}
