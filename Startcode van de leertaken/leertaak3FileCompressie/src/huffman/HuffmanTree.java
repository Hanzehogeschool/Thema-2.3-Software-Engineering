package huffman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;



//Huffman tree class interface: manipulate huffman coding tree.
//
//CONSTRUCTION: with no parameters or a CharCounter object.
//
//******************PUBLIC OPERATIONS***********************
//int [ ] getCode( ch )        --> Return code given character
//int getChar( code )          --> Return character given code
//void writeEncodingTable( out ) --> Write coding table to out
//void readEncodingTable( in ) --> Read encoding table from in
//******************ERRORS**********************************
//Error check for illegal code.

public class HuffmanTree
{
	 
	 public static final int ERROR = -3;
	 public static final int INCOMPLETE_CODE = -2;
	 public static final int END = BitUtils.DIFF_BYTES;
	 private CharCounter theCounts;
	 private HuffNode [ ] theNodes = new HuffNode[ BitUtils.DIFF_BYTES + 1 ];
	 private HuffNode root;

	 public HuffmanTree( )
	 {
		 theCounts = new CharCounter( );
		 root = null;
	 }
 
	public HuffmanTree( CharCounter cc )
	{
		theCounts = cc;
		root = null;
		createTree( );
	}
 
	 /**
	  * Return the code corresponding to character ch.
	  * (The parameter is an int to accommodate EOF).
	  * If code is not found, return an array of length 0.
	  */
	 public int [ ] getCode( int ch )
	 {
	     HuffNode current = theNodes[ ch & 0xff];
	     
	     if( current == null )
	         return null;
	         
	     String v = "";
	     HuffNode par = current.parent;
	     
	     while ( par != null ) // go from our code back to the root, prepending 0's (left) and 1's (right) while we trace back our path.
	     {
	         if( par.left == current )
	             v = "0" + v;
	         else
	             v = "1" + v;
	         current = current.parent;
	         par = current.parent;
	     }
	     
	     // parse the string back to an int array.
	     int [ ] result = new int[ v.length( ) ];
	     for( int i = 0; i < result.length; i++ )
	    	 result[ i ] = v.charAt( i ) == '0' ? 0 : 1;
	         
	    return result;
	}  
	 
	 /**
	  * Get the character corresponding to code.
	  */
	 public int getChar( String code )
	 {
	     // TODO = opdracht           
	 }
	 
	 /**
	  * Writes an encoding table to an output stream.
	  * Format is character (1 byte), count (4 bytes).
	  * A zero count terminates the encoding table.
	  */
	 public void writeEncodingTable( DataOutputStream out ) throws IOException
	 {
		 for( int i = 0; i <= BitUtils.DIFF_BYTES; i++ )
	     {
	         if( theCounts.getCount( i ) > 0 )
	         {
	             // Voor files met alleen a t/m e worden hier de ASCII waarden
	         	// 97 t/m 101 geschreven. Als je de file opent dan kun je de
	         	// characters lezen.
	         	out.writeByte( i );
	         	// De tellingen worden als bits geschreven (zie API DataOutputStream) dus niet
	         	// 'leesbaar' als je de file opent.
	             out.writeInt( theCounts.getCount( i ) );
	         }
	     }
	     out.writeByte( 0 ); // write a 0 byte followed by a 0 int to signify the end of the encoding table in the file.
	     out.writeInt( 0 );
	 }
	 
	 /**
	  * Read the encoding table (the char counts) from an input stream in format
	  * given above and then construct the Huffman tree.
	  * Stream will then be positioned to read compressed data.
	  */
	 public void readEncodingTable( DataInputStream in ) throws IOException
	 {
	     for( int i = 0; i <= BitUtils.DIFF_BYTES; i++ ) // set all counts to 0
	         theCounts.setCount( i, 0 );
	     
	     int ch;
	     int num;
	     
	     while (true) // loop forever, stops if at any points we read a 0 (which signifies the end of the encoding table).
	     {
	         ch = in.readByte( ) & 0xff; // to properly encode characters with a byte value of more than 128.
	         num = in.readInt( );
	         if( num == 0 )
	             break;
	         theCounts.setCount( ch, num );
	     }
	     createTree( );
	 }
	           
	 /*
	  * It is worth noticing that theCounts already exists, and is initialized when this method gets called.
	  * Reading/writing the encoding table will also be invoked by other methods already. All that needs to 
	  * be done is implementing them using theCounts.
	  */
	 
	 /**
	  * Construct the Huffman coding tree.
	  */
	 private void createTree( )
	 {
	     ArrayList<HuffNode> ar = new ArrayList<HuffNode>();
		 
	     for( int i = 0; i <= BitUtils.DIFF_BYTES; i++ )
	         if ( theCounts.getCount( i ) > 0 )
	         {
	             HuffNode newNode = new HuffNode( i,
	                    theCounts.getCount( i ), null, null, null );
	             theNodes[ i ] =  newNode;
	             ar.add( newNode );
	         }
	              
	     // TODO = opdracht      
	     
	     root = ar.remove(0);
	 }
}