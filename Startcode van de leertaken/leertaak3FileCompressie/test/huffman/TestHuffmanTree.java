package huffman;

//import huffman.HuffmanTree;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.*;

/*
 * This class tests the HuffmanTree.
 */
public class TestHuffmanTree extends TestCase {

	Support support = new Support();
	
	/*
	 * create a HuffmanTree and an encoded test file. Load the test file into the HuffmanTree and
	 * then iterate over each entry and compare. Same for the next method.
	 */
	public void testGetCode() {	
		HuffmanTree ht = new HuffmanTree();
		support.prepareTest(support.rawEncoding1, support.testFileEnc);
		try {
			InputStream fin = new BufferedInputStream(new FileInputStream(support.testFileEnc));
			ht.readEncodingTable(new DataInputStream(fin));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < support.values.length; i++) {
			int[] result = ht.getCode(support.values[i]);
			String temp = "";
			for (int j = 0; j < result.length; j++) {
				temp += result[j];
			}
			assertEquals(temp, support.codes[i]);
		}
	}
	
	public void testGetChar() {
		HuffmanTree ht = new HuffmanTree();
		support.prepareTest(support.rawEncoding1, support.testFileEnc);
		try {
			InputStream fin = new BufferedInputStream(new FileInputStream(support.testFileEnc));
			ht.readEncodingTable(new DataInputStream(fin));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < support.codes.length; i++) {
			assertEquals(support.values[i], ht.getChar(support.codes[i]));
		}
	}
	
	/*
	 * testCreateTree()
	 * 
	 * This one is tested implicitly by the above 2 tests. It is a bit tricky to combine methods in
	 * unit tests, but in this case the getCode and getChar methods are so closely linked to the tree
	 * that it is an acceptable situation.
	 * 
	 * 
	 * testWriteEncodingTable()
	 * testReadEncodingTable()
	 * 
	 * This is implicitly tested by testHzip
	 * Since we tested all the other functions, if testCompress or testUncompress fails, and 
	 * the ones in this class do not, then we know which of these 2 methods is not working properly
	 * Adding tests here might be done in some cases (using prepared streams), but in this case
	 * it can be done like this without too much risk of ambiguity. 
	 */
}
