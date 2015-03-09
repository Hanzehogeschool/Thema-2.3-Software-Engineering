package huffman;

import java.io.*;

public class Hzip {

    public static void compress(String inFile) throws IOException {
        String compressedFile = inFile + ".huf";
        InputStream in = new BufferedInputStream(new FileInputStream(inFile));
        OutputStream fout = new BufferedOutputStream(new FileOutputStream(compressedFile));
        HZIPOutputStream hzout = new HZIPOutputStream(fout);
        int ch;

        while ((ch = in.read()) != -1) {
            hzout.write(ch);
        }

        in.close();
        hzout.close();
    }

    public static void uncompress(String compressedFile) throws IOException {
        String extension;
        extension = compressedFile.substring(compressedFile.length() - 4);

        if (!extension.equals(".huf")) {
            System.out.println("Not a compressed file!");

            return;
        }

        InputStream fin = new BufferedInputStream(new FileInputStream(compressedFile));
        HZIPInputStream hzin = new HZIPInputStream(fin);

        String uncompressedFile;
        uncompressedFile = compressedFile.substring(0, compressedFile.length() - 4);
        uncompressedFile += ".uc";
        OutputStream fout = new BufferedOutputStream(new FileOutputStream(uncompressedFile));

        int ch;

        while ((ch = hzin.read()) != -1) {
            fout.write(ch);
        }

        hzin.close();
        fout.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java Hzip -[cu] files");

            return;
        }

        String option = args[0];

        for (int i = 1; i < args.length; i++) {
            String nextFile = args[i];

            if (option.equals("-c")) {
                compress(nextFile);
            } else if (option.equals("-u")) {
                uncompress(nextFile);
            } else {
                System.out.println("Usage: java Hzip -[cu] files");

                return;
            }
        }
    }

}
