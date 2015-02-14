package huffman;

import java.io.*;

/**
 * Writes to HZIPOutputStream are compressed and
 * sent to the output stream being wrapped.
 * No writing is actually done until close.
 */
public class HZIPOutputStream extends OutputStream
{
    private ByteArrayOutputStream byteOut = new ByteArrayOutputStream( );
    private DataOutputStream dout;

    public HZIPOutputStream( OutputStream out ) throws IOException
    {
        dout = new DataOutputStream( out );
    }
    
    /**
     * HZIPOutputStream extends OutputStream. However, we don't want to write
     * the characters immediately. We want to collect all the
     * characters so that we can analyze the frequencies and 
     * determine the best encoding. That's why this write()-method just collects
     * the characters in an outputstream.
     */
    public void write( int ch ) throws IOException
    { 
        byteOut.write( ch );
    }
    
    /*
     * This method overwrites "close", it ensures that once the output stream is closed, the entire contents
     * of the HuffmanTree (HuffmanTree.writeEncodingTable()) and the byteout stream are written to the file 
     * (in that order). Before writing them, this method also creates the tree (based on the input characters)
     * and also encodes the bits to be written (before writing them).
     */
    public void close( ) throws IOException
    {
        // Transform the outputstream to an inputstream so that
    	// CharCounter can analyze it.
    	byte [ ] theInput = byteOut.toByteArray( );
        InputStream byteIn = new ByteArrayInputStream( theInput );
                
        CharCounter countObj = new CharCounter( byteIn );
        byteIn.close( );
        
        HuffmanTree codeTree = new HuffmanTree( countObj );
        codeTree.writeEncodingTable( dout );
        
        BitOutputStream bout = new BitOutputStream( dout );
        
        for( int i = 0; i < theInput.length; i++ ) {
            bout.writeBits( codeTree.getCode( theInput[ i ] ) );
        }
        // And add an "end of file" value to our file, so we can tell the reading program when we are done.
        // The reason we need to do this, is because we use bits, but Java file operation requires us to write complete bytes,
        // so if we do not say it is the end, it will read the filling 0 bits that Java inserts as a new character to read.
        bout.writeBits(codeTree.getCode(HuffmanTree.END & 0xff));
        
        bout.close( );
        dout.close();
        byteOut.close( );
    }
    
}
