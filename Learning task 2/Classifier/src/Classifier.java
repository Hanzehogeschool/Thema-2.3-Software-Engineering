import classifier.*;
import config.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Classifier {

    DecisionTree decisionTree;
    private Map<Item, String> trainingsSet = new HashMap<Item, String>();
    private FeatureType yn = new FeatureType("YesNo", new String[] {
            "yes",
            "no"
    });
    private String[] featureOptions = new String[] {
            "Turbo",
            "EnginePower",
            "SportBumper",
            "SportRing",
            "CruiseControl",
            "ABS",
            "AC",
            "Metallic"
    };

    public Classifier() throws Exception {
        File file = new File(Config.TRAININGSSETS_DIR + Config.TRAININGSET);
        decisionTree = buildTreeFromFile(file);
        System.out.println(decisionTree.toString());
    }

    public static void main(String[] args) throws Exception {
        new Classifier();
    }

    private DecisionTree buildTreeFromFile(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        try {
            Integer featuresCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            Integer itemsCount = Integer.parseInt(bufferedReader.readLine().split(";")[1]);
            Integer lineNumber = 0;

            while (bufferedReader.readLine() != null && lineNumber < itemsCount) {
                String[] currentLine = bufferedReader.readLine().split(";");
                trainingsSet.put(createItem(currentLine[0], currentLine), currentLine[currentLine.length - 1]);
                lineNumber++;
            }

            decisionTree = new DecisionTree(trainingsSet, createFeatures(featuresCount));
        } finally {
            bufferedReader.close();
        }

        return decisionTree;
    }

    private Item createItem(String name, String[] features) {
        Feature[] featureValues = new Feature[features.length - 2];

        for (int i = 0; i < features.length - 2; i++) {
            String value = features[i + 1].equals("1") ? "yes" : "no";
            featureValues[i] = new Feature(featureOptions[i], value, yn);
        }

        return new Item(name, featureValues);
    }

    private Map<String, FeatureType> createFeatures(Integer featuresCount) {
        Map<String, FeatureType> features = new HashMap<String, FeatureType>();

        for (int i = 0; i < featuresCount; i++) {
            features.put(featureOptions[i], yn);
        }

        return features;
    }

}
