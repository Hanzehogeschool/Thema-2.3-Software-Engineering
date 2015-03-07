package huffman;

import java.io.*;

public class HZIPOutputStream extends OutputStream {

    private ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    private DataOutputStream dout;

    public HZIPOutputStream(OutputStream out) throws IOException {
        dout = new DataOutputStream(out);
    }

    public void write(int ch) throws IOException {
        byteOut.write(ch);
    }

    public void close() throws IOException {
        byte[] theInput = byteOut.toByteArray();
        InputStream byteIn = new ByteArrayInputStream(theInput);
        CharCounter countObj = new CharCounter(byteIn);
        byteIn.close();
        HuffmanTree codeTree = new HuffmanTree(countObj);
        codeTree.writeEncodingTable(dout);
        BitOutputStream bout = new BitOutputStream(dout);

        for (int i = 0; i < theInput.length; i++) {
            bout.writeBits(codeTree.getCode(theInput[i]));
        }

        bout.writeBits(codeTree.getCode(HuffmanTree.END & 0xff));
        bout.close();
        dout.close();
        byteOut.close();
    }

}
