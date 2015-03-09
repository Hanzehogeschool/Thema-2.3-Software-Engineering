package huffman;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class TestHuffmanTree extends TestCase {

    Support support = new Support();

    public void testGetCode() {
        HuffmanTree ht = new HuffmanTree();
        support.prepareTest(support.rawEncoding1, support.testFileEnc);

        try {
            InputStream fin = new BufferedInputStream(new FileInputStream(support.testFileEnc));
            ht.readEncodingTable(new DataInputStream(fin));
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < support.codes.length; i++) {
            assertEquals(support.values[i], ht.getChar(support.codes[i]));
        }
    }

}
