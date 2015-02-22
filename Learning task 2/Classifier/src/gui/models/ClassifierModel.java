package gui.models;

import classifier.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ClassifierModel {

    private DecisionTree decisionTree;
    private Map<Item, String> trainingsSet;
    private FeatureType yesNo;
    private String[] featureValues;

    public ClassifierModel(File featureValuesFile, File trainingSet) throws Exception {
        featureValues = buildFeatureValuesFromFile(featureValuesFile);
        yesNo = new FeatureType("YesNo", new String[] { "yes", "no" });
        trainingsSet = new HashMap<Item, String>();
        decisionTree = buildDecisionTreeFromFile(trainingSet);
    }

    private String[] buildFeatureValuesFromFile(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String[] featureValues = new String[]{};

        try {
            Integer featuresCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            featureValues = new String[featuresCount];
            String line;
            Integer lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null && lineNumber < featuresCount) {
                featureValues[lineNumber] = line;
                lineNumber++;
            }
        } finally {
            bufferedReader.close();
        }

        return featureValues;
    }

    private DecisionTree buildDecisionTreeFromFile(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        DecisionTree decisionTree;

        try {
            Integer featuresCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            Integer itemsCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            Integer lineNumber = 0;

            while (bufferedReader.readLine() != null && lineNumber < itemsCount) {
                String[] currentLine = bufferedReader.readLine().split(";");
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
            featureValues[i] = new Feature(this.featureValues[i], value, yesNo);
        }

        return new Item(name, featureValues);
    }

    private Map<String, FeatureType> createFeatures(Integer featuresCount) {
        Map<String, FeatureType> features = new HashMap<String, FeatureType>();

        for (int i = 0; i < featuresCount; i++) {
            features.put(featureValues[i], yesNo);
        }

        return features;
    }

    private String[] resizeArray(String[] oldArray, int remove) {
        int i = oldArray.length - remove;
        String[] newArray = new String[i];
        System.arraycopy(oldArray, 0, newArray, 0, i);

        return newArray;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public String[] getFeatureValues() {
        return featureValues;
    }

}
