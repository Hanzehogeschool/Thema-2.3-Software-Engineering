package huffman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HuffmanTree {

    public static final int ERROR = -3;
    public static final int INCOMPLETE_CODE = -2;
    public static final int END = BitUtils.DIFF_BYTES;
    private CharCounter theCounts;
    private HuffNode[] theNodes = new HuffNode[BitUtils.DIFF_BYTES + 1];
    private HuffNode root;

    public HuffmanTree() {
        theCounts = new CharCounter();
        root = null;
    }

    public HuffmanTree(CharCounter cc) {
        theCounts = cc;
        root = null;
        createTree();
    }

    public int[] getCode(int ch) {
        HuffNode current = theNodes[ch & 0xff];

        if (current == null) {
            return null;
        }

        String v = "";
        HuffNode par = current.parent;

        while (par != null) {
            if (par.left == current) {
                v = "0" + v;
            } else {
                v = "1" + v;
            }

            current = current.parent;
            par = current.parent;
        }

        int[] result = new int[v.length()];

        for (int i = 0; i < result.length; i++) {
            result[i] = v.charAt(i) == '0' ? 0 : 1;
        }

        return result;
    }

    public int getChar(String code) {
        char[] numbers = code.toCharArray();
        HuffNode current = root;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return current.value;
    }

    public void writeEncodingTable(DataOutputStream out) throws IOException {
        for (int i = 0; i <= BitUtils.DIFF_BYTES; i++) {
            if (theCounts.getCount(i) > 0) {
                out.writeByte(i);
                out.writeInt(theCounts.getCount(i));
            }
        }

        out.writeByte(0);
        out.writeInt(0);
    }

    public void readEncodingTable(DataInputStream in) throws IOException {
        for (int i = 0; i <= BitUtils.DIFF_BYTES; i++) {
            theCounts.setCount(i, 0);
        }

        int ch;
        int num;

        while (true) {
            ch = in.readByte() & 0xff;
            num = in.readInt();

            if (num == 0) {
                break;
            }

            theCounts.setCount(ch, num);
        }

        createTree();
    }

    private void createTree() {
        ArrayList<HuffNode> ar = new ArrayList<HuffNode>();

        for (int i = 0; i <= BitUtils.DIFF_BYTES; i++) {
            if (theCounts.getCount(i) > 0) {
                HuffNode newNode = new HuffNode(i, theCounts.getCount(i), null, null, null);
                theNodes[i] = newNode;
                ar.add(newNode);
            }
        }

        while (ar.size() > 1) {
            HuffNode left = ar.remove(getLowestWeight(ar));
            HuffNode right = ar.remove(getLowestWeight(ar));
            HuffNode par = new HuffNode(INCOMPLETE_CODE, (right.weight + left.weight), left, right, null);
            right.parent = par;
            left.parent = par;
            ar.add(par);
        }

        root = ar.remove(0);
    }

    private int getLowestWeight(ArrayList<HuffNode> array) {
        int lowest = 0;

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).weight < array.get(lowest).weight) {
                lowest = i;
            }
        }

        return lowest;
    }

}
