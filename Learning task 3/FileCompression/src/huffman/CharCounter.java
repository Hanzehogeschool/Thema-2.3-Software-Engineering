package huffman;

import java.io.IOException;
import java.io.InputStream;

public class CharCounter {

    private int[] theCounts = new int[BitUtils.DIFF_BYTES + 1];

    public CharCounter() {

    }

    public CharCounter(InputStream input) throws IOException {
        int ch;
        while ((ch = input.read()) != -1) {
            theCounts[ch]++;
        }

        theCounts[HuffmanTree.END & 0xff] = 1;
    }

    public int getCount(int ch) {
        return theCounts[ch & 0xff];
    }

    public void setCount(int ch, int count) {
        theCounts[ch & 0xff] = count;
    }

}
