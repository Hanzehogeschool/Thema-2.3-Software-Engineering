package huffman;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream {

    private InputStream in;
    private int buffer;
    private int bufferPos;

    public BitInputStream(InputStream is) {
        in = is;
        bufferPos = BitUtils.BITS_PER_BYTES;
    }

    private static int getBit(int pack, int pos) {
        return (pack & (1 << pos)) != 0 ? 1 : 0;
    }

    public int readBit() throws IOException {
        if (bufferPos == BitUtils.BITS_PER_BYTES) {
            buffer = in.read();

            if (buffer == -1) {
                return -1;
            }

            bufferPos = 0;
        }

        return getBit(buffer, bufferPos++);
    }

    public void close() throws IOException {
        in.close();
    }

}
