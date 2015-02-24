package test;

import classifier.*;
import junit.framework.TestCase;

/**
 * JUnit test to test classifier.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TestClassifier extends TestCase {

    /**
     * Test classifier constructor.
     * Creates a new test classifier.
     *
     * @param arg0 The arguments.
     */
    public TestClassifier(String arg0) {
        super(arg0);
    }

    /**
     * Generates the tree.
     *
     * @return The generated tree.
     */
    private DecisionTree generateTree() {
        Node root = new Node("AC");

        Node n1 = new Node("ABS");
        Node n2 = new Node("ABS");

        root.addChild("yes", n1);
        root.addChild("no", n2);

        Node l1 = new Node("high");
        Node l2 = new Node("medium");
        Node l3 = new Node("medium");
        Node l4 = new Node("low");

        n1.addChild("yes", l1);
        n1.addChild("no", l2);
        n2.addChild("yes", l3);
        n2.addChild("no", l4);

        return new DecisionTree(root);
    }

    /**
     * Tests the category.
     */
    public void testCategory() {
        DecisionTree dt = generateTree();

        FeatureType yn = new FeatureType("YesNo", new String[]{
                "yes", "no"
        });

        Feature[] features = new Feature[]{
                new Feature("AC", "yes", yn),
                new Feature("ABS", "yes", yn)
        };

        Item item = new Item("car", features);

        String category = dt.assignCategory(item);
        assertEquals("high", category);

        item.setFeatureValue("AC", "no");
        category = dt.assignCategory(item);
        assertEquals("medium", category);

        item.setFeatureValue("ABS", "no");
        category = dt.assignCategory(item);
        assertEquals("low", category);
    }

    /**
     * Generates the three features tree.
     *
     * @return The generated three features tree.
     */
    private DecisionTree generateThreeFeaturesTree() {
        Node root = new Node("AC");

        Node n1 = new Node("ABS");
        Node n2 = new Node("ABS");

        root.addChild("yes", n1);
        root.addChild("no", n2);

        Node f1 = new Node("Turbo");
        Node f2 = new Node("Turbo");
        Node f3 = new Node("Turbo");
        Node f4 = new Node("Turbo");

        n1.addChild("yes", f1);
        n1.addChild("no", f2);
        n2.addChild("yes", f3);
        n2.addChild("no", f4);

        Node l1 = new Node("high");
        Node l2 = new Node("high");
        Node l3 = new Node("medium");
        Node l4 = new Node("medium");
        Node l5 = new Node("medium");
        Node l6 = new Node("medium");
        Node l7 = new Node("low");
        Node l8 = new Node("low");

        f1.addChild("yes", l1);
        f1.addChild("no", l2);
        f2.addChild("yes", l3);
        f2.addChild("no", l4);
        f3.addChild("yes", l5);
        f3.addChild("no", l6);
        f4.addChild("yes", l7);
        f4.addChild("no", l8);

        return new DecisionTree(root);
    }

    /**
     * Tests the three features.
     */
    public void testThreeFeatures() {
        DecisionTree dt = generateThreeFeaturesTree();

        FeatureType yn = new FeatureType("YesNo", new String[]{
                "yes", "no"
        });

        Feature[] features = new Feature[]{
                new Feature("AC", "yes", yn),
                new Feature("ABS", "yes", yn),
                new Feature("Turbo", "yes", yn)
        };

        Item item = new Item("car", features);

        String category = dt.assignCategory(item);
        assertEquals("high", category);

        item.setFeatureValue("Turbo", "no");
        category = dt.assignCategory(item);
        assertEquals("high", category);

        item.setFeatureValue("AC", "no");
        category = dt.assignCategory(item);
        assertEquals("medium", category);

        item.setFeatureValue("ABS", "no");
        category = dt.assignCategory(item);
        assertEquals("low", category);
    }

}
