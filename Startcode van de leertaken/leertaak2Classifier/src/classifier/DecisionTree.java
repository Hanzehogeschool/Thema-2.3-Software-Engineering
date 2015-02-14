/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package classifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a decision tree. As this class implements the Classifier
 * interface, it can be used to classify items. 
 */
public class DecisionTree implements Classifier {

	private Node root;

	public DecisionTree(Node tree){
		root = tree;
	}

	/**
	 * Assign a category to an item. This is the implementation of algorithm
	 * 4.1 on page 53 of the book.
	 * @param item The Item which has to be classified by the algorithm.
	 * @return A String describing the name of category to which the item belongs.
	 */
	public String assignCategory(Item item){
		Node current;

		//1)	start at the root node
		current= root;
		//2)	repeat while the current node is not a leaf
		while(! current.isLeaf() ){
			//    a)	follow the arc corresponding to the item's
			//              value of the current node split attribute
			//    b)	the node reached becomes the current node
			current = current.follow(item.getFeatureValue(current.getLabel()));
		}
		//3)	the label of the leaf node is the class of the item
		return current.getLabel();
	}

	public String toString(){
		return "Decision tree:\n" + root.toString();
	}


	/**
	 * Create a new DecisionTree based upon a given trainingsset and featurelist.
	 * @param trainingsSet
	 * @param features
	 */
	public DecisionTree(Map<Item, String> trainingsSet, Map<String, FeatureType> features) {
		root = buildDecisionTree(trainingsSet,features);
	}


	/**
	 * This method implements algorithm 4.2 on page 54 of the book.
	 * Input : a training set of items T, a set of features F
	 * Output: a decision tree which can be used to classify other items.
	 * @param trainingsSet
	 * @param features
	 * @return The root Node of the decisiontree created.
	 */
	private static Node buildDecisionTree(Map<Item, String> trainingsSet, Map<String, FeatureType> features){
		//1) 2ab) if the attribute set is empty or the items belong to a single class
		if(features.size()==0 || information(trainingsSet)==0.0){
			//  create a leaf node labeled according to the class of the items
			return new Node(findCategory(trainingsSet));
		}
		
		//3)  select the "best" split feature s in F
		//    (in the car example this best split feature might be "airco")
		String splitFeature = selectSplit(trainingsSet,features);
		//4)  create a node with label s.name
		Node n = new Node(splitFeature);
		//5)  for each possible value vi of s
		//    (in the car example the possible values of the airco feature are yes/no.
		FeatureType splitType = (FeatureType)features.get(splitFeature);
		//    Split the trainingsset into subsets. 
		//    (in the car example you'd get a subset of all cars with airco=yes and
		//     a subset of cars with airco=no)
		Map<String, HashMap<Item, String>> partitions = performSplit(trainingsSet,splitFeature,splitType.allowedValues());
		for (Iterator<String> iter = partitions.keySet().iterator(); iter.hasNext();) {
			String value = iter.next();		
			//    a)  be ni the result of a recursive execution of this algorithm where
			//        the fist input is: Ti = { item in T | item.s == vi }
			//        the second input is: A - { s }
			//        (repeat the algorithm with the feature airco excluded)
			Map<Item, String> partition=partitions.get(value);
			Map<String, FeatureType> remainingFeatures = new HashMap<String, FeatureType>(features);
			remainingFeatures.remove(splitFeature);
			Node child = buildDecisionTree(partition,remainingFeatures);
			//    b)  set ni as child node of n and label the connecting arc vi
			n.addChild(value,child);
		}
		//4)  n is the resulting root node of the decision tree
		return n;
	}

	/**
	 * Determines the category of a set of items. Returns "?" when
	 * the items have 
	 */
	private static String findCategory(Map<Item, String> trainingsSubset){
		// No category if the set is empty
		if(trainingsSubset.size()==0) 
			return "?";

		// Computes the frequency of each category
		// J.Balje: This could be done much simpler... left as an exercise ;-)
		Map<String, Integer> catFreq=new HashMap<String, Integer>();
		Iterator<Item> it=trainingsSubset.keySet().iterator();
		String category="";
		while(it.hasNext()){
			Item item = it.next();
			category = trainingsSubset.get(item);
			Integer count = catFreq.get(category);
			if(count==null){
				catFreq.put(category,new Integer(1));
			}else{
				catFreq.put(category,new Integer(1+count.intValue()));
			}
		}
		// if only one category is present, it is the category of the set
		if(catFreq.keySet().size()==1){
			return category;
		}
		// otherwise it is not possible to assign a category
		return "?";
	}

	/**
	 * Determines the "best" split attribute (feature) for a set of items,
	 * it is based on the information gain criterion. Thus to determine which feature is "best" (which feature
	 * contains the most information)the formulas on page 54 and 55 are used.
	 */
	private static String selectSplit(Map<Item, String> trainingsSet,Map<String, FeatureType> features){
		Iterator<String> attr=features.keySet().iterator();
		String split=null;
		double maxGain=0.0;
		while(attr.hasNext()){
			String candidate = attr.next();
			FeatureType type = features.get(candidate);
			double gain = evaluateSplitGain(trainingsSet,candidate,type.allowedValues());
			if(gain>maxGain){
				maxGain = gain;
				split = candidate;
			}
		}
		return split;
	}

	/**
	 * Executes the split of a trainingsset according to a split feature.
	 * It returns the resulting sets of items, each of them being labeled
	 * with the relative feature value.
	 * (in our example: feature=airco, the result is {all cars with airco=yes}, {all cars with airco=no})
	 * By the way, these generics look great, don't they? ;-)
	 */
	private static Map<String, HashMap<Item, String>> performSplit(Map<Item, String> trainingsSet, String split, Collection<String> possibleValues){
		Map<String, HashMap<Item, String>> partitions = new HashMap<String, HashMap<Item, String>>();
		for (Iterator<String> iter = possibleValues.iterator(); iter.hasNext();) {
			String value = iter.next();
			partitions.put(value,new HashMap<Item, String>());
		}
		Iterator<Item> it=trainingsSet.keySet().iterator();
		while(it.hasNext()){
			Item item = it.next();
			String splitValue = item.getFeatureValue(split);
			Map<Item, String> partition = partitions.get(splitValue);
			partition.put(item,trainingsSet.get(item));
		}
		return partitions;
	}

	/**
	 * Evaluates the information gain that would derive from splitting
	 * a set of items according to a given split attribute.
	 */
	private static double evaluateSplitGain(Map<Item, String> items, String split, Collection<String> possibleValues){
		double origInfo = information(items);
		double splitInfo = 0;
		Map<String, HashMap<Item, String>> partitions = performSplit(items,split,possibleValues);
		double size=items.size();
		for (Iterator<String> iter = possibleValues.iterator(); iter.hasNext();) {
			String value = iter.next();
			Map<Item, String> partition = partitions.get(value);
			double partitionSize=partition.size();
			double partitionInfo = information(partition);
			splitInfo += partitionSize/size*partitionInfo;
		}
		return origInfo - splitInfo;
	}

	/**
	 * Computes the information content of a set of items.
	 * The classes represent the symbols of the alphabet
	 */
	private static double information(Map<Item, String> trainingsSubset){
		Map<String, Long> frequencies = new HashMap<String, Long>();

		// compute number of occurrencies of classes
		Iterator<Item> it=trainingsSubset.keySet().iterator();
		while(it.hasNext()){
			Item item = it.next();
			String category = trainingsSubset.get(item);
			Long num_occur = frequencies.get(category);
			if(num_occur == null){
				frequencies.put(category,new Long(1));
			}else{
				frequencies.put(category,new Long(num_occur + 1));
			}
		}

		// compute information
		double info=0;
		double numItems = trainingsSubset.size();
		Iterator<Long> iter = frequencies.values().iterator();
		while(iter.hasNext()){
			Long num_occurr = iter.next();
			double freq = num_occurr.doubleValue() / numItems;
			info += freq * Math.log(freq) / Math.log(2.0);
		}
		return -info;
	}

}
