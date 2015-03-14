package utils;

public class Debugger {

    public static boolean debug = false;

    public static void print(String className, String methodName, String description) {
        if (debug) {
            System.out.println(ANSI.ANSI_CYAN + className + " > " + ANSI.ANSI_BLUE + methodName + " > " + ANSI.ANSI_WHITE + description);
        }
    }

}
