package huffman;

import java.util.Arrays;
import junit.framework.TestCase;

/*
 * Here we will test the compress and uncompress functions of Hzip.
 */
public class TestHzip extends TestCase {

	Support support = new Support();
	
	public void testCompress() {
		// text1
		support.prepareTest(support.text1, support.testFilePlain);
		try {
			Hzip.compress(support.testFilePlain);
		}
		catch (Exception e) {
			System.err.println("Fatal error: could not Compress.");
		}
		int[] result = support.readFile(support.testFileEnc);
		assertTrue(Arrays.equals(support.rawEncoding1, result));
		
		// text 2 
		support.prepareTest(support.text2, support.testFilePlain);
		try {
			Hzip.compress(support.testFilePlain);
		}
		catch (Exception e) {
			System.err.println("Fatal error: could not Compress.");
		}
		result = support.readFile(support.testFileEnc);
		assertTrue(Arrays.equals(support.rawEncoding2, result));
	}
	
	public void testUncompress() {
		// text 1
		support.prepareTest(support.rawEncoding1, support.testFileEnc);
		try {
			Hzip.uncompress(support.testFileEnc);
		}
		catch (Exception e) {
			System.err.println("Fatal error: could not Uncompress.");
		}
		int[] result = support.readFile(support.testFileDec);
		assertTrue(Arrays.equals(support.text1, result));
		
		// text 2
		support.prepareTest(support.rawEncoding2, support.testFileEnc);
		try {
			Hzip.uncompress(support.testFileEnc);
		}
		catch (Exception e) {
			System.err.println("Fatal error: could not Uncompress.");
		}
		result = support.readFile(support.testFileDec);
		assertTrue(Arrays.equals(support.text2, result));
	} 
	
	public void testChain() {
		support.prepareTest(support.text1, support.testFilePlain);
		try {
			Hzip.compress(support.testFilePlain);
			Hzip.uncompress(support.testFileEnc);
		}
		catch (Exception e) {
			System.err.println("Fatal error: could not make the comress/decompress chain.");
		}
		int[] result = support.readFile(support.testFileDec);
		assertTrue(Arrays.equals(support.text1, result));
		support.prepareTest(support.text2, support.testFilePlain);
		try {
			Hzip.compress(support.testFilePlain);
			Hzip.uncompress(support.testFileEnc);
		}
		catch (Exception e) {
			System.err.println("Fatal error: could not make the comress/decompress chain.");
		}
		result = support.readFile(support.testFileDec);
		assertTrue(Arrays.equals(support.text2, result));
	}
}
