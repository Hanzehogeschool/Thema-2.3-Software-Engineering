package classifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DecisionTree implements Classifier {

    private Node root;

    public DecisionTree(Node tree) {
        root = tree;
    }

    public DecisionTree(Map<Item, String> trainingsSet, Map<String, FeatureType> features) {
        root = buildDecisionTree(trainingsSet, features);
    }

    private static Node buildDecisionTree(Map<Item, String> trainingsSet, Map<String, FeatureType> features) {
        if (features.size() == 0 || information(trainingsSet) == 0.0) {
            return new Node(findCategory(trainingsSet));
        }

        String splitFeature = selectSplit(trainingsSet, features);
        Node n = new Node(splitFeature);
        FeatureType splitType = (FeatureType) features.get(splitFeature);
        Map<String, HashMap<Item, String>> partitions = performSplit(trainingsSet, splitFeature, splitType.allowedValues());

        for (Iterator<String> iter = partitions.keySet().iterator(); iter.hasNext(); ) {
            String value = iter.next();
            Map<Item, String> partition = partitions.get(value);
            Map<String, FeatureType> remainingFeatures = new HashMap<String, FeatureType>(features);
            remainingFeatures.remove(splitFeature);
            Node child = buildDecisionTree(partition, remainingFeatures);
            n.addChild(value, child);
        }

        return n;
    }

    private static String findCategory(Map<Item, String> trainingsSubset) {
        if (trainingsSubset.size() == 0)
            return "?";

        Map<String, Integer> catFreq = new HashMap<String, Integer>();
        Iterator<Item> it = trainingsSubset.keySet().iterator();
        String category = "";

        while (it.hasNext()) {
            Item item = it.next();
            category = trainingsSubset.get(item);
            Integer count = catFreq.get(category);

            if (count == null) {
                catFreq.put(category, new Integer(1));
            } else {
                catFreq.put(category, new Integer(1 + count.intValue()));
            }
        }

        if (catFreq.keySet().size() == 1) {
            return category;
        }

        return "?";
    }

    private static String selectSplit(Map<Item, String> trainingsSet, Map<String, FeatureType> features) {
        Iterator<String> attr = features.keySet().iterator();
        String split = null;
        double maxGain = 0.0;

        while (attr.hasNext()) {
            String candidate = attr.next();
            FeatureType type = features.get(candidate);
            double gain = evaluateSplitGain(trainingsSet, candidate, type.allowedValues());
            if (gain > maxGain) {
                maxGain = gain;
                split = candidate;
            }
        }

        return split;
    }

    private static Map<String, HashMap<Item, String>> performSplit(Map<Item, String> trainingsSet, String split, Collection<String> possibleValues) {
        Map<String, HashMap<Item, String>> partitions = new HashMap<String, HashMap<Item, String>>();

        for (Iterator<String> iter = possibleValues.iterator(); iter.hasNext(); ) {
            String value = iter.next();
            partitions.put(value, new HashMap<Item, String>());
        }

        Iterator<Item> it = trainingsSet.keySet().iterator();

        while (it.hasNext()) {
            Item item = it.next();
            String splitValue = item.getFeatureValue(split);
            Map<Item, String> partition = partitions.get(splitValue);
            partition.put(item, trainingsSet.get(item));
        }

        return partitions;
    }

    private static double evaluateSplitGain(Map<Item, String> items, String split, Collection<String> possibleValues) {
        double origInfo = information(items);
        double splitInfo = 0;
        Map<String, HashMap<Item, String>> partitions = performSplit(items, split, possibleValues);
        double size = items.size();

        for (Iterator<String> iter = possibleValues.iterator(); iter.hasNext(); ) {
            String value = iter.next();
            Map<Item, String> partition = partitions.get(value);
            double partitionSize = partition.size();
            double partitionInfo = information(partition);
            splitInfo += partitionSize / size * partitionInfo;
        }

        return origInfo - splitInfo;
    }

    private static double information(Map<Item, String> trainingsSubset) {
        Map<String, Long> frequencies = new HashMap<String, Long>();
        Iterator<Item> it = trainingsSubset.keySet().iterator();

        while (it.hasNext()) {
            Item item = it.next();
            String category = trainingsSubset.get(item);
            Long num_occur = frequencies.get(category);

            if (num_occur == null) {
                frequencies.put(category, new Long(1));
            } else {
                frequencies.put(category, new Long(num_occur + 1));
            }
        }

        double info = 0;
        double numItems = trainingsSubset.size();
        Iterator<Long> iter = frequencies.values().iterator();

        while (iter.hasNext()) {
            Long num_occurr = iter.next();
            double freq = num_occurr.doubleValue() / numItems;
            info += freq * Math.log(freq) / Math.log(2.0);
        }

        return -info;
    }

    public String assignCategory(Item item) {
        Node current;
        current = root;

        while (!current.isLeaf()) {
            current = current.follow(item.getFeatureValue(current.getLabel()));
        }

        return current.getLabel();
    }

    public String toString() {
        return "Decision tree:\n" + root.toString();
    }

}
