package huffman;

import java.io.*;

/**
 * Main file. 
 * Opstarten met twee parameters (Eclipse->Run->Run Configurations->Arguments tabblad)
 * 
 * Bijvoorbeeld:
 * -c H:\\download\\eclipse_workspace\\Huffmann\\data\\TestA.dat
 * -u H:\\download\\eclipse_workspace\\Huffmann\\data\\TestA.dat.huf
 */

/*
 * Veranderd: aantal comments toe gevoegd. Alle referenties naar EOF uit de tree geschopt.
 * 
 */
public class Hzip
{

	public static void compress( String inFile ) throws IOException
	{
		String compressedFile = inFile + ".huf";
		InputStream in = new BufferedInputStream(
				new FileInputStream( inFile ) );
		OutputStream fout = new BufferedOutputStream(
				new FileOutputStream( compressedFile ) );
		HZIPOutputStream hzout = new HZIPOutputStream( fout );
		int ch;
		while( ( ch = in.read( ) ) != -1 )
			hzout.write( ch );
		in.close( );
		hzout.close( );     
	}

	public static void uncompress( String compressedFile ) throws IOException
	{
		// Check extension inputfile
		String extension;
		extension = compressedFile.substring( compressedFile.length( ) - 4 );
		if( !extension.equals( ".huf" ) )
		{
			System.out.println( "Not a compressed file!" );
			return;
		}

		// Setup inputstream (the compressed file)
		InputStream fin = new BufferedInputStream(
				new FileInputStream( compressedFile ) );
		HZIPInputStream hzin = new HZIPInputStream( fin );

		// Setup outputstream (the uncompressed file)
		String uncompressedFile;
		uncompressedFile = compressedFile.substring( 0, compressedFile.length( ) - 4 );
		uncompressedFile += ".uc";    // for debugging, so as to not clobber original
		OutputStream fout = new BufferedOutputStream(
				new FileOutputStream( uncompressedFile ) );
		
		// Process file
		int ch;
		while( ( ch = hzin.read( ) ) != -1 )
			fout.write( ch );

		hzin.close( );
		fout.close( );
	}

	/**
	 * Main methode van het Huffmann programma. Merk op dat er twee commandline
	 * parameters nodig zijn. Deze kun je in Eclipse standaard meegeven door 
	 * het maken van run configurations onder Run -> Run Configurations en dan 
	 * 2e tab: arguments.
	 *  
	 * @param -c of -u voor compress of uncompress
	 * @param filenaam van de file die gecomprimeerd of 'uitgepakt' moet worden
	 * @throws IOException
	 */
	public static void main( String [ ] args ) throws IOException
	{
		if( args.length < 2 )
		{
			System.out.println( "Usage: java Hzip -[cu] files" );
			return;
		}

		String option = args[ 0 ];
		for( int i = 1; i < args.length; i++ )
		{
			String nextFile = args[ i ];
			if( option.equals( "-c" ) )
				compress( nextFile );
			else if( option.equals( "-u" ) )
				uncompress( nextFile );
			else
			{
				System.out.println( "Usage: java Hzip -[cu] files" );
				return;
			}
		}
	}
}
