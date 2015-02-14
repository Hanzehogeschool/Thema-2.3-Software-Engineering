package huffman;

import java.io.IOException;
import java.io.InputStream;

//CharCounter class: A character counting class.
//
// CONSTRUCTION: with no parameters or an open InputStream.
//
// ******************PUBLIC OPERATIONS***********************
// int getCount( ch )           --> Return # occurrences of ch
// void setCount( ch, count )   --> Set # occurrences of ch
// ******************ERRORS**********************************
// No error checks.

public class CharCounter
{
    public CharCounter( )
    {
    }
    
    public CharCounter( InputStream input ) throws IOException
    {
        int ch;
        while( ( ch = input.read( ) ) != -1 )
            theCounts[ ch ]++;
        theCounts[HuffmanTree.END & 0xff] = 1; // define an "end of file" value in our tree.
    }
    
    public int getCount( int ch )
    {
        // Waarom '& 0xff' ?  -- om te doen alsof de byte (rest van het programma werkt met ints, 
    	// maar ze stellen bytes voor) "unsigned" is (dit maakt een implicite cast naar int, en zorgt 
    	// er voor dat de waarde altijd positief is).
    	return theCounts[ ch & 0xff ];
    }
    
    public void setCount( int ch, int count )
    {
        theCounts[ ch & 0xff ] = count;
    }
    
    private int [ ] theCounts = new int[ BitUtils.DIFF_BYTES + 1 ];
}

