import gui.ClassifierGUI;

/**
 * Class representing a classifier.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class Classifier {

    /**
     * The main method.
     * Creates a new classifier without arguments.
     *
     * @param args The arguments for the classifier.
     */
    public static void main(String[] args) throws Exception {
        new ClassifierGUI().initializeGUI();
    }

}
