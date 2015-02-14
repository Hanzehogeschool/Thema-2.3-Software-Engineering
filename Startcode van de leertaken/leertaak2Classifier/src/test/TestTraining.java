package test;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import classifier.*;

public class TestTraining extends TestCase {

	// The yes/no featuretype is the only featuretype used in this example.
	private FeatureType yn = new FeatureType("YesNo",new String[]{"yes","no"});

	public TestTraining(String arg0) {
		super(arg0);
	}

	/**
	 * A helper method to create an item with the specified values
	 * for the airco and abs features.
	 * @param ac A String describing whether or not this item has airco.
	 * @param abs Idem ABS.
	 * @return An Item with the specified values.
	 */
	private Item createItem(String ac, String abs){
		Feature[] featureValues = new Feature[]{ 
													new Feature("AC",ac,yn),
													new Feature("ABS",abs,yn)
		                                       };

		return new Item("car", featureValues);
	}


	public void testBuildDecisionTree(){
		Map<Item, String> trainingsSet = new HashMap<Item, String>();
		Map<String, FeatureType> features = new HashMap<String, FeatureType>();

		features.put("AC",yn);
		features.put("ABS",yn);

		// Create an item that does have airco and abs.
		Item item1 = createItem("yes","yes");
		// Put this item in the trainingsset with category "high".
		trainingsSet.put(item1,"high");

		Item item2 = createItem("yes","no");
		trainingsSet.put(item2,"medium");

		Item item3 = createItem("no","yes");
		trainingsSet.put(item3,"medium");

		Item item4 = createItem("no","no");
		trainingsSet.put(item4,"low");

		// Build a new DecisionTree based upon this trainingsset.
		DecisionTree dc = new DecisionTree(trainingsSet,features);

		// Test whether the decisiontree works as expected.
		// It should classify the items in the same category 
		// as assigned to them before.
		assertEquals("high",dc.assignCategory(item1));
		assertEquals("medium",dc.assignCategory(item2));
		assertEquals("medium",dc.assignCategory(item3));
		assertEquals("low",dc.assignCategory(item4));
	}

}
