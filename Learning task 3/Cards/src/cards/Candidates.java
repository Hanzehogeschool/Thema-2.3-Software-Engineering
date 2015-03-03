package cards;

import java.util.Iterator;
import java.util.LinkedList;

public class Candidates extends LinkedList<Candidate> {

    public Candidates() {
        super.add(new Candidate('A', 2));
        super.add(new Candidate('K', 2));
        super.add(new Candidate('Q', 2));
        super.add(new Candidate('J', 2));
    }

    public Candidate remove(int index) {
        Candidate candidate = get(index);
        candidate.takeOne();

        if (candidate.getAvailable() == 0) {
            candidate = super.remove(index);
        }

        return candidate;
    }

    public void add(int index, Candidate candidate) {
        candidate.addOne();

        if (candidate.getAvailable() == 1) {
            super.add(index, candidate);
        }
    }

    public String toString() {
        Iterator it = iterator();
        String rS = "";

        while (it.hasNext()) {
            rS += it.next() + " ";
        }

        return rS;
    }

}
