package huffman;

/**
 * Een aantal constanten voor het programma.
 * (Merk op dat een interface constanten kan bevatten en niet 
 * noodzakelijk methoden hoeft te eisen.)
 */
public interface BitUtils
{
    public static final int BITS_PER_BYTES = 8;
    // Aantal verschillende characters
    public static final int DIFF_BYTES = 256; //binair: 100000000 (9 bytes).
    // End Of File character 
    public static final int EOF = 256;   
}