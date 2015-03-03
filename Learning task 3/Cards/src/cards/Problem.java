package cards;

import java.util.Scanner;

public class Problem {

    private Candidates candidates = new Candidates();
    private Solution solution = new Solution();
    private Scanner reader = new Scanner(System.in);

    public void solve() {
        int index = 0;

        while (index < candidates.size()) {
            if (solution.fits(candidates.get(index))) {
                solution.record(candidates.remove(index));

                if (solution.complete()) {
                    solution.show();
                } else {
                    solve();
                }

                candidates.add(index, solution.eraseRecording());
            }

            index++;
        }
    }

}
