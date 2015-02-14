package huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;

/**
 * HZIPInputStream wraps an input stream.
 * read() returns an uncompressed byte from the
 * wrapped input stream.
 */
public class HZIPInputStream extends InputStream
{
    private BitInputStream bin;
    private HuffmanTree codeTree;

    public HZIPInputStream( InputStream in ) throws IOException
    {
        codeTree = new HuffmanTree( );
        codeTree.readEncodingTable( new DataInputStream( in ) );
        
        bin = new BitInputStream( in );
    }
    
    /*
     * This method reads the encoded part of the file using the (earlier) extracted HuffmanTree.
     * 
     */
    public int read( ) throws IOException
    { 
        String bits = "";
        int bit;
        int decode;

        while( true )
        {
            bit = bin.readBit( );        
            if( bit == -1 )
                return -1;
                
            bits += bit;
        
            decode = codeTree.getChar( bits );
            if( decode == HuffmanTree.INCOMPLETE_CODE )
                continue;
            else if( decode == HuffmanTree.ERROR )
                throw new IOException( "Decoding error" );
            else if (decode == (HuffmanTree.END & 0xff)) // this is our "end of file" marker
            	return -1;
            else 
                return decode;
        }
    }
    
    public void close( ) throws IOException
    {
        bin.close( );
    }
    
}
