package ttt;

import java.util.*;
/*
 * Textual User Interface
 */
class Tui
{
    private Scanner reader      = new Scanner(System.in);
    private TicTacToe t; 
 
    public Tui()
    {
        do
        {
            System.out.println("*** new Game ***\n");
            t=new TicTacToe();
            if (t.computerPlays()) System.out.println("I start:\n");
            else                   System.out.println("You start:\n");
            while (!t.gameOver())
            {
               t.playMove(move());
               System.out.println(t);
            }
            System.out.println("Game over " + t.winner() + " wins");
        } while (nextGame());
    }
    
    public static void main( String [ ] args ) { new Tui();}
    
    private int move()
    {
        if (t.computerPlays())
        {
            int compMove=t.chooseMove();
            System.out.println("Computer Move = " + compMove);
            return compMove;
        }
        else
        {
            int humanMove;
            do
            {
                System.out.print("Human move    = ");
                // enter integer for the position on the tictactoe board
                // 012
                // 345
                // 678
                humanMove = reader.nextInt();
            }
            while (!t.moveOk(humanMove));
            return humanMove;
        }
    }
    
    private boolean nextGame()
    {
        Character yn;
        do 
        {
            System.out.print("next Game? enter Y/N: ");
            yn=(reader.next()).charAt(0);
            System.out.println(""+yn);
        } 
        while  (!(yn=='Y' || yn=='y' || yn=='N' || yn=='n'));
        return yn=='Y'|| yn=='y';
    }
}


