package huffman;

import java.io.*;


// BitInputStream class: Bit-input stream wrapper class.
//
// CONSTRUCTION: with an open InputStream.
//
// ******************PUBLIC OPERATIONS***********************
// int readBit( )              --> Read one bit as a 0 or 1
// void close( )               --> Close underlying stream

 public class BitInputStream
{
    public BitInputStream( InputStream is )
    {
        in = is;
        bufferPos = BitUtils.BITS_PER_BYTES;
    }
    
    public int readBit( ) throws IOException
    {
        if ( bufferPos == BitUtils.BITS_PER_BYTES )
        {
            buffer = in.read( );
            if( buffer == -1 )
                return -1;
            bufferPos = 0;
        }
        
        return getBit( buffer, bufferPos++ );    
    }
    
    public void close( ) throws IOException
    {
        in.close( );
    }
    
    private static int getBit( int pack, int pos )
    {
        // Stel pack= 01101010 en je wilt byte op pos=3 
    	// << is de 'shift'-operator, zie internet
    	// 
    	// 1) 1 << pos wordt dan 00000001 << 3
    	// en dat is 00001000
    	//
    	// 2) 01101010 & 00001000 is 000010000 (& is bitwise AND)
    	// 
    	// 3) als de waarde van het antwoord niet 0 is (binaire waarde weer
    	// terugvertaald naar decimaal), dan was de gevraagde bit 1, anders 0. 
    	return ( pack & ( 1 << pos ) ) != 0 ? 1 : 0;
    }
    
    private InputStream in;
    private int buffer;
    private int bufferPos;
}
