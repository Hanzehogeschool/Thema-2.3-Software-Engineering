package cards;
import java.util.LinkedList;
import java.util.Iterator;
public class Candidates extends LinkedList<Candidate>
{

public Candidates()
{
    super.add(new Candidate('A',2)); //Ace
    super.add(new Candidate('K',2)); //King
    super.add(new Candidate('Q',2)); //Queen
    super.add(new Candidate('J',2)); //Jack
}
public Candidate remove(int index) //overwrites super.remove
{
    Candidate candidate=get(index);
    candidate.takeOne();
    //System.out.println("remove index "+index +" "+ candidate);
    if (candidate.getAvailable()==0)
     {
         candidate=super.remove(index);
     }
     return candidate;
}

public void add (int index, Candidate candidate) //overwrites super.add
{
      candidate.addOne();
      //System.out.println("add index "+ index + " "+ candidate);
      if (candidate.getAvailable()==1)
      {
          super.add(index,candidate);
      }
}  

public String toString()
{
    Iterator it=iterator(); 
    String rS="";
    while (it.hasNext())
    {
        rS+=it.next()+" ";
    }
    return rS;
}

}

    
    
