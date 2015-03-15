package utils;

/**
 * Class representing a debugger.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class Debugger {

    /**
     * The debug.
     */
    public static boolean debug = false;

    /**
     * Prints.
     *
     * @param className   The class name.
     * @param methodName  The method name.
     * @param description The description.
     */
    public static void print(String className, String methodName, String description) {
        if (debug) {
            System.out.println(ANSI.ANSI_CYAN + className + " > " + ANSI.ANSI_BLUE + methodName + " > " + ANSI.ANSI_WHITE + description);
        }
    }

}
