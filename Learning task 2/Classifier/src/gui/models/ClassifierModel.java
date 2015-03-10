package gui.models;

import classifier.DecisionTree;
import classifier.Feature;
import classifier.FeatureType;
import classifier.Item;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a classifier model.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class ClassifierModel {

    /**
     * The decision tree.
     */
    private DecisionTree decisionTree;

    /**
     * The training set.
     */
    private Map<Item, String> trainingSet;

    /**
     * The yes and no.
     */
    private FeatureType yesNo;

    /**
     * The features.
     */
    private String[] features;

    /**
     * The categories.
     */
    private Map<String, Float> categories;

    /**
     * The item.
     */
    private Item item;

    /**
     * The category.
     */
    private String category;

    /**
     * The price.
     */
    private Float price;

    /**
     * The color.
     */
    private String color;

    /**
     * The weight.
     */
    private Integer weight;

    /**
     * Classifier model constructor.
     * Creates a new classifier model.
     *
     * @param categoriesFile  The categories file.
     * @param featuresFile    The features file.
     * @param trainingSetFile The training set file.
     * @throws Exception
     */
    public ClassifierModel(File categoriesFile, File featuresFile, File trainingSetFile) throws Exception {
        categories = generateCategoriesFromFile(categoriesFile);
        features = generateFeaturesFromFile(featuresFile);
        yesNo = new FeatureType("YesNo", new String[]{"yes", "no"});
        trainingSet = new HashMap<Item, String>();
        decisionTree = generateDecisionTreeFromFile(trainingSetFile);
        item = createItem("car", getFeatures());
    }

    /**
     * Generates the categories from file.
     *
     * @param file The file.
     * @return The generated categories.
     * @throws Exception
     */
    private Map<String, Float> generateCategoriesFromFile(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        Map<String, Float> categories = new HashMap<String, Float>();

        try {
            Integer categoriesCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            String line;
            Integer lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null && lineNumber <= categoriesCount) {
                String[] currentLine = line.split(";");
                categories.put(currentLine[0], Float.parseFloat(currentLine[1]));
                lineNumber++;
            }
        } finally {
            bufferedReader.close();
        }

        return categories;
    }

    /**
     * Generates the features from file.
     *
     * @param file The file.
     * @return The generated features.
     * @throws Exception
     */
    private String[] generateFeaturesFromFile(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] features = new String[]{};

        try {
            Integer featuresCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            features = new String[featuresCount];
            String line;
            Integer lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null && lineNumber <= featuresCount) {
                features[lineNumber] = line;
                lineNumber++;
            }
        } finally {
            bufferedReader.close();
        }

        return features;
    }

    /**
     * Generates the decision tree from file.
     *
     * @param file The file.
     * @return The generated decision tree.
     * @throws Exception
     */
    private DecisionTree generateDecisionTreeFromFile(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        DecisionTree decisionTree;

        try {
            Integer featuresCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            Integer itemsCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            String line;
            Integer lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null && lineNumber <= itemsCount) {
                String[] currentLine = line.split(";");
                trainingSet.put(createItem(currentLine[0], resizeArray(currentLine, 2)), currentLine[currentLine.length - 1]);
                lineNumber++;
            }

            decisionTree = new DecisionTree(trainingSet, createFeatures(featuresCount));
        } finally {
            bufferedReader.close();
        }

        return decisionTree;
    }

    /**
     * Creates the item.
     *
     * @param name     The name.
     * @param features The features.
     * @return The created item.
     */
    public Item createItem(String name, String[] features) {
        Feature[] featureValues = new Feature[features.length];

        for (int i = 0; i < features.length; i++) {
            String value = features[i].equals("1") ? "yes" : "no";
            featureValues[i] = new Feature(this.features[i], value, yesNo);
        }

        return new Item(name, featureValues);
    }

    /**
     * Creates the features.
     *
     * @param featuresCount The features count.
     * @return The created features.
     */
    private Map<String, FeatureType> createFeatures(Integer featuresCount) {
        Map<String, FeatureType> features = new HashMap<String, FeatureType>();

        for (int i = 0; i < featuresCount; i++) {
            features.put(this.features[i], yesNo);
        }

        return features;
    }

    /**
     * Generates the category.
     *
     * @return The generated category.
     */
    public String generateCategory() {
        return decisionTree.assignCategory(item);
    }

    /**
     * Generates the price.
     *
     * @return The generated price.
     */
    public Float generatePrice() {
        for (Map.Entry<String, Float> entry : categories.entrySet()) {
            if (getCategory().equals(entry.getKey())) {
                return entry.getValue();
            }
        }

        return 0.0f;
    }

    /**
     * Adds the item.
     *
     * @param buttonModel The button model.
     * @param i           The i.
     */
    public void addItem(ButtonModel buttonModel, Integer i) {
        if (buttonModel != null) {
            item.setFeatureValue(features[i - 1], buttonModel.getActionCommand());
        }
    }

    /**
     * Resize the array.
     *
     * @param oldArray The old array.
     * @param remove   The remove.
     * @return The resize array.
     */
    private String[] resizeArray(String[] oldArray, int remove) {
        int i = oldArray.length - remove;
        String[] newArray = new String[i];
        System.arraycopy(oldArray, 1, newArray, 0, i);

        return newArray;
    }

    /**
     * Gets the decision tree.
     *
     * @return The decision tree.
     */
    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    /**
     * Gets the features.
     *
     * @return The features.
     */
    public String[] getFeatures() {
        return features;
    }

    /**
     * Gets the price.
     *
     * @return The price.
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price The price.
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Gets the category.
     *
     * @return The category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category.
     *
     * @param category The category.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the color.
     *
     * @return The color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color The color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the weight.
     *
     * @return The weight.
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Sets the weight.
     *
     * @param weight The weight.
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
