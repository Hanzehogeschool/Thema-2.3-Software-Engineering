package test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestTraining.class);
		suite.addTestSuite(TestTree.class);
		suite.addTestSuite(TestItem.class);
		suite.addTestSuite(TestClassifier.class);
		suite.addTestSuite(TestRepresentation.class);
		//$JUnit-END$
		return suite;
	}

}
