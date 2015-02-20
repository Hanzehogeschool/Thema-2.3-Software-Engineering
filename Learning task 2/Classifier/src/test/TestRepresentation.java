package test;
import junit.framework.TestCase;

import classifier.*;

public class TestRepresentation extends TestCase {

	public TestRepresentation(String arg0) {
		super(arg0);
	}

    public void testSingleNode(){
    	String label="the label";
    	Node node = new Node(label);
    	
    	String repres = node.toString();
    	
    	//assertEquals("["+label+"]",repres);
    	assertTrue(repres + " doesn't include " + label,
    				repres.indexOf(label)>-1);	
    }
    
	public void testSimpleTree(){
		Node leftLeaf = new Node("left");
		Node rightLeaf = new Node("right");
		
		String toLeft="to the left";
		String toRight="to the right";
		Node root = new Node("root");
		root.addChild(toLeft,leftLeaf);
		root.addChild(toRight,rightLeaf);
		
		String repres = root.toString();
		
		int index;
		index = repres.indexOf("root");
		assertTrue("root not found",index>0);
	}
}
