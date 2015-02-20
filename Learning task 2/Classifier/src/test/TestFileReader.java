package test;
import junit.framework.TestCase;
import classifier.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class TestFileReader extends TestCase {
	private static final String RESULTS_FILE = "OptieText.txt";
	private static final String CAT_FILE = "CatText.txt";
	private ArrayList<Node> optionList = new ArrayList<Node>();

	public TestFileReader(String arg0) {
		super(arg0);
	}

	private DecisionTree buildTree(){
		Node iroot = null;
		File resultsFile = makeAbsoluteFilename(RESULTS_FILE);
        BufferedReader reader = null;
        //
        File catFile = makeAbsoluteFilename(CAT_FILE);
        BufferedReader catReader = null;
        //
        try {
            reader = new BufferedReader(
                        new FileReader(resultsFile));
            catReader = new BufferedReader(
                    new FileReader(catFile));
        }
        catch(FileNotFoundException e) {
            System.out.println("Unable to find the file: " +
                               resultsFile);
        }
       
        try{
        String line = reader.readLine();
        int lineNumber = 1;
        Node root = new Node(line);
        optionList.add(root);
        line = reader.readLine();
        while(line != null){
        	for(int i=0;i<(Math.pow(2,lineNumber));i++){
            	Node node =new Node(line);
            	optionList.add(node);
            }
            line = reader.readLine();
            lineNumber ++;
        }
            
        int group = 0;
        int[] catGroup = new int[]{30,24,30,24,20,10,68,30,20};
        String catLine = catReader.readLine();
        while(catLine != null){
            makeCat(catLine, catGroup[group]);
            group ++;
            catLine = catReader.readLine();
        }
            //(2*n)+1
        for (int i=0; i<Math.pow(2,lineNumber-1);i++){
            Node node = (Node)optionList.get(i);
            node.addChild("yes",(Node)optionList.get((2*i)+1));
            node.addChild("no",(Node)optionList.get((2*i)+2));
        }
        iroot = root;
        }
        catch(IOException e) {
            System.out.println("Error encountered reading the file: " +
                               resultsFile);
        }
        return new DecisionTree(iroot);
    }
        
	private void makeCat(String cat, int aantal)
	{
		for (int i=0; i<aantal;i++){
			Node node =new Node(cat);
    		optionList.add(node);
		}
	}
	private File makeAbsoluteFilename(String filename)
    {
        File file = new File(filename);
        if(!file.isAbsolute()) {
            file = new File(getProjectFolder(), filename);
        }
        return file;
    }
	private File getProjectFolder()
    {
         String myClassFile = getClass().getName() + ".class";
         URL url = getClass().getResource(myClassFile);
         return new File(url.getPath()).getParentFile();
    }

	public void testCategory(){
		DecisionTree dt = buildTree();

		FeatureType yn = new FeatureType("YesNo"
						,new String[]{"yes","no"});

		Feature[] features = new Feature[]
		{ new Feature("Turbo","yes",yn),
		  new Feature("EnginPower","yes",yn),
		  new Feature("SportBumper","yes",yn),
		  new Feature("SportRing","yes",yn),
		  new Feature("CruisControll","yes",yn),
		  new Feature("ABS","yes",yn),
		  new Feature("AC","yes",yn),
		  new Feature("Metalic","yes",yn)
		};
		
		Item item = new Item("car",features);
		
		String category = dt.assignCategory(item);
		assertEquals("High",category);
		
		item.setFeatureValue("Turbo","no");
		category = dt.assignCategory(item);
		assertEquals("High",category);
		
		item.setFeatureValue("EnginPower","no");
		category = dt.assignCategory(item);
		assertEquals("Medium",category);
		
	}
	
}
