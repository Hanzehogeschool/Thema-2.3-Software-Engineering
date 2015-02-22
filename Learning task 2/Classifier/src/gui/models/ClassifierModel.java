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

public class ClassifierModel {

    private DecisionTree decisionTree;
    private Map<Item, String> trainingsSet;
    private FeatureType yesNo;
    private String[] features;
    private Map<String, Float> categories;
    private Item item;
    private String category;
    private Float price;
    private String color;
    private Integer weight;

    public ClassifierModel(File categoriesFile, File featuresFile, File trainingSet) throws Exception {
        categories = buildCategoriesFromFile(categoriesFile);
        features = buildFeaturesFromFile(featuresFile);
        yesNo = new FeatureType("YesNo", new String[]{"yes", "no"});
        trainingsSet = new HashMap<Item, String>();
        decisionTree = buildDecisionTreeFromFile(trainingSet);
        item = createItem("car", getFeatures());
    }

    private Map<String, Float> buildCategoriesFromFile(File file) throws Exception {
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

    private String[] buildFeaturesFromFile(File file) throws Exception {
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

    private DecisionTree buildDecisionTreeFromFile(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        DecisionTree decisionTree;

        try {
            Integer featuresCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            Integer itemsCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            String line;
            Integer lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null && lineNumber <= itemsCount) {
                String[] currentLine = line.split(";");
                trainingsSet.put(createItem(currentLine[0], resizeArray(currentLine, 2)), currentLine[currentLine.length - 1]);
                lineNumber++;
            }

            decisionTree = new DecisionTree(trainingsSet, createFeatures(featuresCount));
        } finally {
            bufferedReader.close();
        }

        return decisionTree;
    }

    public Item createItem(String name, String[] features) {
        Feature[] featureValues = new Feature[features.length];

        for (int i = 0; i < features.length; i++) {
            String value = features[i].equals("1") ? "yes" : "no";
            featureValues[i] = new Feature(this.features[i], value, yesNo);
        }

        return new Item(name, featureValues);
    }

    private Map<String, FeatureType> createFeatures(Integer featuresCount) {
        Map<String, FeatureType> features = new HashMap<String, FeatureType>();

        for (int i = 0; i < featuresCount; i++) {
            features.put(this.features[i], yesNo);
        }

        return features;
    }

    private String[] resizeArray(String[] oldArray, int remove) {
        int i = oldArray.length - remove;
        String[] newArray = new String[i];
        System.arraycopy(oldArray, 1, newArray, 0, i);

        return newArray;
    }

    public void addItem(ButtonModel buttonModel, Integer i) {
        if (buttonModel != null) {
            item.setFeatureValue(features[i - 1], buttonModel.getActionCommand());
        }
    }

    public String buildCategory() {
        return decisionTree.assignCategory(item);
    }

    public Float buildPrice() {
        for (Map.Entry<String, Float> entry : categories.entrySet()) {
            if (getCategory().equals(entry.getKey())) {
                return entry.getValue();
            }
        }

        return 0.0f;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public String[] getFeatures() {
        return features;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
