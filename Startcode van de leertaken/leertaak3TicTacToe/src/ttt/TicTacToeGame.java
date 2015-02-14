package ttt;

import java.util.Random;
class TicTacToe
{
	private static final int HUMAN        = 0; 
	private static final int COMPUTER     = 1; 
	public  static final int EMPTY        = 2;

	public  static final int HUMAN_WIN    = 0;
	public  static final int DRAW         = 1;
	public  static final int UNCLEAR      = 2;
	public  static final int COMPUTER_WIN = 3;

	private int [ ] [ ] board = new int[ 3 ][ 3 ];
    private Random random=new Random();  
	private int side=random.nextInt(2);  
	private int position=UNCLEAR;
	private char computerChar,humanChar;

	// Constructor
	public TicTacToe( )
	{
		clearBoard( );
		initSide();
	}
	
	private void initSide()
	{
	    if (this.side==COMPUTER) { computerChar='X'; humanChar='O'; }
		else                     { computerChar='O'; humanChar='X'; }
    }
    
    public void setComputerPlays()
    {
        this.side=COMPUTER;
        initSide();
    }
    
    public void setHumanPlays()
    {
        this.side=HUMAN;
        initSide();
    }

	public boolean computerPlays()
	{
	    return side==COMPUTER;
	}

	public int chooseMove()
	{
	    //Best best=chooseMove(COMPUTER);
	    //return best.row*3+best.column;
	    return 0;
    }
    
    // Find optimal move
	private Best chooseMove( int side )
	{
		int opp;              // The other side
		Best reply;           // Opponent's best reply
		int simpleEval;       // Result of an immediate evaluation
		int bestRow = 0;
		int bestColumn = 0;
		int value;

		if( ( simpleEval = positionValue( ) ) != UNCLEAR )
			return new Best( simpleEval );

		// TODO: implementeren m.b.v. recursie/backtracking
	    return null;
    }

   
    //check if move ok
    public boolean moveOk(int move)
    {
 	//return ( move>=0 && move <=8 && board[move/3 ][ move%3 ] == EMPTY );
 	return true;
    }
    
    // play move
    public void playMove(int move)
    {
		board[move/3][ move%3] = this.side;
		if (side==COMPUTER) this.side=HUMAN;  else this.side=COMPUTER;
	}


	// Simple supporting routines
	private void clearBoard( )
	{
		//TODO:
	}


	private boolean boardIsFull( )
	{
		//TODO:
		return true;
	}

	// Returns whether 'side' has won in this position
	private boolean isAWin( int side )
	{
	    //TODO:
	    return true;
    }

	// Play a move, possibly clearing a square
	private void place( int row, int column, int piece )
	{
		board[ row ][ column ] = piece;
	}

	private boolean squareIsEmpty( int row, int column )
	{
		return board[ row ][ column ] == EMPTY;
	}

	// Compute static value of current position (win, draw, etc.)
	private int positionValue( )
	{
		// TODO:
		return UNCLEAR;
	}
	
	
	public String toString()
	{
	    //TODO:
		return "...\n...\n...\n";   
	}  
	
	public boolean gameOver()
	{
	    this.position=positionValue();
	    return this.position!=UNCLEAR;
    }
    
    public String winner()
    {
        if      (this.position==COMPUTER_WIN) return "computer";
        else if (this.position==HUMAN_WIN   ) return "human";
        else                                  return "nobody";
    }
    
	
	private class Best
    {
       int row;
       int column;
       int val;

       public Best( int v )
         { this( v, 0, 0 ); }
      
       public Best( int v, int r, int c )
        { val = v; row = r; column = c; }
    } 
	
	
}

