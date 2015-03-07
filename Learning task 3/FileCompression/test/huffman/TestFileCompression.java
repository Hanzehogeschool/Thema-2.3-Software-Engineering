package huffman;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * JUnit test to test file compression.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TestFileCompression {

    /**
     * The support.
     */
    Support support;

    /**
     * Sets up the tests.
     */
    @Before
    public void setUp() {
        support = new Support();
    }

    /**
     * Tests the compress file a.
     */
    @Test
    public void testCompressFileA() {
        try {
            Hzip.compress("./data/TestA.dat");
        } catch (Exception e) {
            System.err.println("Compression failed.");
        }
    }

    /**
     * Tests the decompress file a.
     */
    @Test
    public void testDecompressFileA() {
        testCompressFileA();
        int[] file = support.readFile("./data/TestA.dat");

        try {
            Hzip.uncompress("./data/TestA.dat.huf");
        } catch (Exception exception) {
            System.err.println("Decompression failed.");
        }

        int[] decompressedFile = support.readFile("./data/TestA.dat.uc");
        assertTrue(Arrays.equals(file, decompressedFile));
    }

    /**
     * Tests the file compression sizes.
     */
    @Test
    public void testFileCompressionSizes() {
        String[] fileNames = {"TestA", "TestB", "TestC", "TestD"};
        File original;
        File compressed;
        int[] percentages = new int[fileNames.length];

        try {
            Hzip.compress("./data/" + fileNames[0] + ".dat");
        } catch (Exception exception) {
            System.err.println("Compression failed.");
        }

        original = new File("./data/" + fileNames[0] + ".dat");
        compressed = new File("./data/" + fileNames[0] + ".dat.huf");
        assertTrue(original.length() < compressed.length());
        percentages[0] = (int) ((compressed.length() * 100) / original.length());

        try {
            Hzip.compress("./data/" + fileNames[1] + ".dat");
        } catch (Exception exception) {
            System.err.println("Compression failed.");
        }

        original = new File("./data/" + fileNames[1] + ".dat");
        compressed = new File("./data/" + fileNames[1] + ".dat.huf");
        assertTrue(original.length() < compressed.length());
        percentages[1] = (int) ((compressed.length() * 100) / original.length());

        try {
            Hzip.compress("./data/" + fileNames[2] + ".dat");
        } catch (Exception exception) {
            System.err.println("Compression failed.");
        }

        original = new File("./data/" + fileNames[2] + ".dat");
        compressed = new File("./data/" + fileNames[2] + ".dat.huf");
        assertTrue(original.length() > compressed.length());
        percentages[2] = (int) ((compressed.length() * 100) / original.length());

        try {
            Hzip.compress("./data/" + fileNames[3] + ".dat");
        } catch (Exception exception) {
            System.err.println("Compression failed.");
        }

        original = new File("./data/" + fileNames[3] + ".dat");
        compressed = new File("./data/" + fileNames[3] + ".dat.huf");
        assertTrue(original.length() > compressed.length());
        percentages[3] = (int) ((compressed.length() * 100) / original.length());

        System.out.println("Compress % gain:");

        for (int i = 0; i < percentages.length; i++) {
            System.out.println("Compressed file '" + fileNames[i] + "' has gain " + percentages[i] + "% over the original file.");
        }
    }

}
