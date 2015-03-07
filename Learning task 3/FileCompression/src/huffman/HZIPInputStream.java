package huffman;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HZIPInputStream extends InputStream {

    private BitInputStream bin;
    private HuffmanTree codeTree;

    public HZIPInputStream(InputStream in) throws IOException {
        codeTree = new HuffmanTree();
        codeTree.readEncodingTable(new DataInputStream(in));
        bin = new BitInputStream(in);
    }

    public int read() throws IOException {
        String bits = "";
        int bit;
        int decode;

        while (true) {
            bit = bin.readBit();

            if (bit == -1) {
                return -1;
            }

            bits += bit;

            decode = codeTree.getChar(bits);

            if (decode == HuffmanTree.INCOMPLETE_CODE) {
                continue;
            } else if (decode == HuffmanTree.ERROR) {
                throw new IOException("Decoding error");
            } else if (decode == (HuffmanTree.END & 0xff)) {
                return -1;
            } else {
                return decode;
            }
        }
    }

    public void close() throws IOException {
        bin.close();
    }

}
