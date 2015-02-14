package huffman;

import java.io.IOException;
import java.io.OutputStream;

//BitOutputStream class: Bit-output stream wrapper class.
//
//CONSTRUCTION: with an open OutputStream.
//
//******************PUBLIC OPERATIONS***********************
//void writeBit( val )        --> Write one bit (0 or 1)
//void writeBits( vald )      --> Write array of bits
//void flush( )               --> Flush buffered bits
//void close( )               --> Close underlying stream

public class BitOutputStream
{
	private OutputStream out;
	private int buffer;
	private int bufferPos;

	public BitOutputStream( OutputStream os )
	{
		bufferPos = 0;
		buffer = 0;
		out = os;
	}

	/*
	 * This method writes bits into the output stream (os).
	 */
	public void writeBit( int val ) throws IOException
	{
		buffer = setBit( buffer, bufferPos++, val );
		if( bufferPos == BitUtils.BITS_PER_BYTES )
			flush( );
	}

	public void writeBits( int [ ] val ) throws IOException
	{
		for( int i = 0; i < val.length; i++ ) {
			writeBit( val[ i ] );
		}
	}    

	public void flush( ) throws IOException
	{
		if( bufferPos == 0 )
			return;

		out.write( buffer );
		bufferPos = 0;
		buffer = 0;    
	}

	public void close( ) throws IOException
	{
		flush( );
		out.close( );
	}

	private int setBit( int pack, int pos, int val )
	{
		if( val == 1 )
			pack |= ( val << pos );
		return pack;
	}

}
