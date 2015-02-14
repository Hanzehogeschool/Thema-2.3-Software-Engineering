package test;
import classifier.*;
import junit.framework.TestCase;



public class TestClassifier extends TestCase {

	public TestClassifier(String arg0) {
		super(arg0);
	}

    private DecisionTree buildTree(){
		Node root = new Node("AC");
		
		Node n1=new Node("ABS");
		Node n2=new Node("ABS");
		root.addChild("yes",n1);
		root.addChild("no",n2);
		
		// leaves
		Node l1 = new Node("high");
		Node l2 = new Node("medium");
		Node l3 = new Node("medium");
		Node l4 = new Node("low");

		n1.addChild("yes",l1);
		n1.addChild("no",l2);

		n2.addChild("yes",l3);
		n2.addChild("no",l4);
		
		return new DecisionTree(root);
    }
	public void testCategory(){
		
		DecisionTree dt = buildTree();

		FeatureType yn = new FeatureType("YesNo"
						,new String[]{"yes","no"});

		Feature[] features = new Feature[]
		{ new Feature("AC","yes",yn),
		  new Feature("ABS","yes",yn)
		};
		
		Item item = new Item("car",features);
		
		String category = dt.assignCategory(item);
		assertEquals("high",category);
		
		
		item.setFeatureValue("AC","no");
		category = dt.assignCategory(item);
		assertEquals("medium",category);

		item.setFeatureValue("ABS","no");
		category = dt.assignCategory(item);
		assertEquals("low",category);
	}
}
