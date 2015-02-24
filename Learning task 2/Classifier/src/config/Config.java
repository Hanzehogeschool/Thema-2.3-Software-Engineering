package config;

/**
 * Class representing a config.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class Config {

    /**
     * The root directory.
     */
    public static final String ROOT_DIR = "/home/nils/Code/Hanze/Theme-2.3-Software-Engineering/Learning task 2/";

    /**
     * The project directory.
     */
    public static final String PROJECT_DIR = ROOT_DIR + "Classifier/";

    /**
     * The source directory.
     */
    public static final String SOURCE_DIR = PROJECT_DIR + "src/";

    /**
     * The sets directory.
     */
    public static final String SETS_DIR = SOURCE_DIR + "sets/";

    /**
     * Te categories file.
     */
    public static final String CATEGORIES_FILE = SETS_DIR + "Categories.txt";

    /**
     * The features file.
     */
    public static final String FEATURES_FILE = SETS_DIR + "Features.txt";

    /**
     * The training set file.
     */
    public static final String TRAINING_SET_FILE = SETS_DIR + "TrainingSet.txt";

    /**
     * The training set incomplete file.
     */
    public static final String TRAINING_SET_INCOMPLETE_FILE = SETS_DIR + "TrainingSetIncomplete.txt";

    /**
     * The training set inconsistent file.
     */
    public static final String TRAINING_SET_INCONSISTENT_FILE = SETS_DIR + "TrainingSetInconsistent.txt";

}
